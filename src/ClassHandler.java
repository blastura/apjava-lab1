/*
 * @(#)ClassHandler.java
 * Time-stamp: "2008-11-10 20:22:47 anton"
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
    private Class<?> c;
    private Object cI;
    private Method[] methods;

    /**
     * Creates a new <code>ClassHandler</code> instance.
     *
     * @param className a <code>String</code> value which should link
     * to a java class existing in classpath. The class should
     * implement the interface Plugable, and at least one constructor
     * with no parameters is required.
     * @exception Exception if an error occurs
     */
    public ClassHandler(String className) throws ClassNotFoundException,
                                                 InstantiationException,
                                                 IllegalAccessException {
        this.c = Class.forName(className);
        this.cI  = c.newInstance();
        
        // Get all methods
        this.methods = c.getDeclaredMethods();
        for (int i = 0, length = methods.length; i < length; i++) {
            System.out.println(methods[i]);

            // Get all parameters for current method
            Class<?>[] params = methods[i].getParameterTypes();
            for (int j = 0; j < params.length; j++) {
                System.out.println("   \\-" + params[j]);
            }
        }
    }

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
    
    public Method[] getMethods() {
        return this.methods;
    }
}