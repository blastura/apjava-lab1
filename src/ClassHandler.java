/*
 * @(#)ClassHandler.java
 * Time-stamp: "2008-11-13 03:21:38 anton"
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Describe class <code>ClassHandler</code> here.
 *
 * @author dit06ajn@cs.umu.se
 * @version 1.0
 */
public class ClassHandler {
    private Object cI;
    private Method[] methods;

    /**
     * Creates a new ClassHandler instance which creates an instance
     * of the specified class in the parameter className and stores
     * it's methods in an array.
     *
     * @param className should point to a java class existing in
     * classpath. The class should implement the interface Plugable,
     * and at least one constructor which take no parameters is
     * required.
     * @exception ClassNotFoundException if the specified className is
     * not a Class in classpath.
     * @exception InstantiationException if the specified Class
     * doesn't have a constructor without parameters.
     * @exception IllegalAccessException if TODO
     */
    public ClassHandler(String className) throws ClassNotFoundException,
                                                 InstantiationException,
                                                 IllegalAccessException {
        // Get Class of className
        // Throws: ClassNotFoundException, IllegalAccessException
        Class<?> c = Class.forName(className);

        // Get all declared methods
        this.methods = c.getDeclaredMethods();
        
        // Create a new instance.
        // Require: A constructor without parameters,
        // Throws: InstantiationException
        this.cI  = c.newInstance();
    }

    /**
     * Describe <code>invoke</code> method here.
     *
     * @param method a <code>Method</code> value
     * @param args a <code>String</code> value
     * @return an <code>Object</code> value
     * @exception IllegalAccessException if an error occurs
     * @exception InvocationTargetException if an error occurs
     * @exception NoSuchMethodException if an error occurs
     * @exception InstantiationException if an error occurs
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
            // TODO params are missing
            throw new IllegalArgumentException("You should provide "
                                               + typeParams.length
                                               + " parameter(s) to excecute this method");
        }
        
        // Loop through all typeParams and args to create an array
        // containing arguments converted to the required class.
        for (int i = 0; i < typeParams.length; i++) {
            Class<?> paramClass = typeParams[i];
            
            // Required: Constructor that takes a String as its only
            // parameter, NoSuchMethodException thrown otherwise.
            Constructor<?> con =
                paramClass.getConstructor(java.lang.String.class);
            
            // Could (but shouldn't) throw InstantiationException
            // since NoSuchMethodException should be thrown above
            // first.
            params[i] = con.newInstance(args[i]);
        }
        return method.invoke(cI, params);
    }

    public String getDescription() {
        if (cI instanceof Plugable) {
            return ((Plugable) cI).getDescription();
        }
        else {
            return "Class does not implement Plugable, no describtion found";
        }
    }
    
    /**
     * Returns all the methods of the class stored in this
     * ClassHandler.
     *
     * @return an array of all the methods of the classed stored in
     * this ClassHandler.
     */
    public Method[] getMethods() {
        return this.methods;
    }

    // TODO
    public void printMethods() {
        for (int i = 0, length = methods.length; i < length; i++) {
            System.out.println(methods[i]);
            
            // Get all parameters for current method
            Class<?>[] params = methods[i].getParameterTypes();
            for (int j = 0; j < params.length; j++) {
                System.out.println("   \\-" + params[j]);
            }
        }
    }
}