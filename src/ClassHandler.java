/*
 * @(#)ClassHandler.java
 * Time-stamp: "2008-11-16 23:07:03 anton"
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * ClassHandler is responsible for instantiating and interacting with a supplied
 * Class.
 *
 * @author dit06ajn@cs.umu.se
 * @version 1.0
 */
public class ClassHandler {
    private Method[] methods;
    private Plugable classInstance;
    private static Logger logger = Logger.getLogger("mytoolbox");

    /**
     * Creates a new ClassHandler instance which creates an instance
     * of the specified class in the parameter className and stores
     * it's methods in an array.
     *
     * @param className Should point to a java class existing in classpath. The
     *                  class should implement the interface Plugable, and at
     *                  least one constructor which take no parameters is
     *                  required.
     * @exception ClassNotFoundException If the specified className is not a
     *                                   Class in classpath.
     * @exception InstantiationException If the specified Class doesn't have a
     *                                   constructor without parameters.
     * @exception IllegalAccessException If the specified Class constructor
     *                                   doesn't have access to the definition
     *                                   of this Constructor.
     */
    public ClassHandler(String className) throws ClassNotFoundException,
                                                 InstantiationException,
                                                 IllegalAccessException {
        // Get Class of className, and it's implemented class
        // Throws: ClassNotFoundException, IllegalAccessException
        Class<?> c = Class.forName(className);

        // Create a new instance.
        // Require: A constructor without parameters
        // Throws: InstantiationException and
        //         (RuntimeException) ClassCastException
        this.classInstance  = (Plugable) c.newInstance();

        Class<?> implInterface = Class.forName("Plugable");

        // Get all declared methods
        Method[] allMethods = c.getDeclaredMethods();

        // Methods in interface
        Method[] implMethods = implInterface.getDeclaredMethods();

        // To store (allMethods - implMethods)
        this.methods = new Method[allMethods.length - implMethods.length];

        // Add all methods to array methods that exists in allMethods but not in
        // implMethods
        int count = 0;
        for (Method method : allMethods) {
            for (Method implMethod : implMethods) {
                if (!isSameMethod(method, implMethod)) {
                    methods[count] = method;
                    count++;
                    logger.info("Method will be in list: "
                                + method.getName());
                } else {
                    logger.info("Method will be removed from list: "
                                + implMethod.getName());
                }
            }
        }
    }

    /* Returns true if supplied methods has equal name, parameters and
     * return type, false otherwise */
    private static boolean isSameMethod(Method m1, Method m2) {
        return (m1.getName().equals(m2.getName()))
            && m1.getReturnType().equals(m2.getReturnType())
            && java.util.Arrays.equals(m1.getParameterTypes(),
                                       m2.getParameterTypes());
    }

    /**
     * Creates new instances of the arguments in <code>args</code> with regard
     * to the required parameter types in the supplied Method
     * <code>method</code>.
     *
     * @param method The Method to invoke.
     * @param args A String representation of the parameters required to invoke
     *             <code>method</code>.
     * @return The value returned by the invoked Method, if the Method doesn't
     *         return a value, this will be null.
     * @exception IllegalAccessException If the specified method doesn't have
     *                                   access to the instantiated Class in
     *                                   this ClassHandler.
     * @exception InvocationTargetException If an exception is thrown in the
     *                                      invoked Method, the Exception is
     *                                      wrapped in this
     *                                      InvocationTargetException.
     * @exception NoSuchMethodException If the Class stored in this ClassHandler
     *                                  doesn't have the supplied Method.
     * @exception InstantiationException If one of the arguments supplied in
     *                                   <code>args</code> can't be converted
     *                                   from a String to the required Class to
     *                                   be used as a parameter in
     *                                   <code>method</code>.
     */
    public Object invoke(Method method, String[] args)
        throws IllegalAccessException,
               InvocationTargetException,
               NoSuchMethodException,
               InstantiationException {
        // Store the Class for each parameter in method.
        Class<?>[] typeParams = method.getParameterTypes();

        // For storing type converted parameters.
        Object[] params = new Object[typeParams.length];

        if (args.length != typeParams.length) {
            // Too few or to many parameters
            throw new IllegalArgumentException(typeParams.length
                                               + " parameter(s) is required to"
                                               + " to execute this method");
        }

        // Loop through all typeParams and args to create an array containing
        // arguments converted to the required class.
        for (int i = 0; i < typeParams.length; i++) {
            Class<?> paramClass = typeParams[i];

            // Required: Constructor that takes a String as its only parameter,
            // NoSuchMethodException thrown otherwise.
            Constructor<?> con =
                paramClass.getConstructor(java.lang.String.class);

            // Could (but shouldn't) throw InstantiationException since
            // NoSuchMethodException should be thrown above
            // first. InvocationTargetException is thrown if the supplied value
            // can't be used to create this instance, ex new Integer("foo").
            params[i] = con.newInstance(args[i]);
        }
        // Could throw IllegalAccessException, InvocationTargetException
        return method.invoke(classInstance, params);
    }

    /**
     * Returns the description of the loaded Class. This requires the Class to
     * implement the interface Plugable.
     *
     * @return The description of the supplied class.
     */
    public String getDescription() {
        return classInstance.getDescription();
    }

    /**
     * Returns all the methods of the class stored in this ClassHandler.
     *
     * @return An array of all the methods of the classed stored in this
     *         ClassHandler.
     */
    public Method[] getMethods() {
        return methods;
    }
}