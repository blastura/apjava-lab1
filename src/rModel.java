/*
 * @(#)rModel.java
 * Time-stamp: "2008-11-06 23:01:52 anton"
 */

import java.lang.reflect.Method;

/**
 * Describe class <code>rModel</code> here.
 *
 * @author dit06ajn@cs.umu.se
 * @version 1.0
 */
public class rModel {
   private Class<?> c;
   private Method[] methods;
   
   /** Creates a new rModel instance. */
   public rModel(String className) throws Exception {
      this.c = Class.forName(className);
      //System.out.println(c.getName());

      // Get all methods
      this.methods = c.getDeclaredMethods();
      for (int i = 0, length = methods.length; i < length; i++) {
         System.out.println(methods[i]);
         
         // Get all parameters for current method
         Class<?>[] params = methods[i].getParameterTypes();
         for (int j = 0; j < params.length; j++) {
            System.out.println("   \\-" + params[j]);
         }
         // Contructor without parameters is a precondition.
         Object iC = c.newInstance();
         // if (params.length == 2) {
         //          }
         //          else if (params.length == 1){
         //             methods[i].invoke(iC, -10);
         //          }
      }
   }

   public String getDescription() {
      try {
         return ((Plugable) c.newInstance()).getDescription();
      }
      catch (InstantiationException e) {
         e.printStackTrace();
         return "err";
      }
      catch (IllegalAccessException e) {
         e.printStackTrace();
         return "err";
      }
   }
   
   public Method[] getMethods() {
      return this.methods;
   }

   public void invoke(Method method) {
   }
}