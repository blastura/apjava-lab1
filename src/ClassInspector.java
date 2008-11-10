/*
 * @(#)ClassInspector.java
 * Time-stamp: "2008-11-10 00:36:46 anton"
 */

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Describe class <code>ClassInspector</code> here.
 *
 * @author dit06ajn@cs.umu.se
 * @version 1.0
 */
public class ClassInspector {
    private Class<?> c;
    private Object cI;
    private Method[] methods;

    /**
     * Creates a new <code>ClassInspector</code> instance.
     *
     * @param className a <code>String</code> value which should link
     * to a java class existing in classpath. The class should
     * implement the interface Plugable, and at least one constructor
     * with no parameters is required.
     * @exception Exception if an error occurs
     */
    public ClassInspector(String className) throws ClassNotFoundException,
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
               InvocationTargetException {
        // Get all parameters for current method
        Class<?>[] typeParams = method.getParameterTypes();
        if (args.length != typeParams.length) {
            // TODO params are missing
            return "You should provide "
                + typeParams.length
                + " parameter(s) to excecute this method";
        }
        Object[] params = new Object[typeParams.length];
        for (int i = 0; i < typeParams.length; i++) {
            // If cast fails values will be null
            Object parameter = typeParams[i];
            
            if (parameter.equals(java.lang.String.class)) {
                params[i] = args[i];
            }
            else if (parameter.equals(java.lang.Integer.class)) {
                params[i] = Integer.valueOf(args[i]);
            }
             else if (parameter.equals(java.lang.Double.class)) {
                params[i] = Double.valueOf(args[i]);
             }
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