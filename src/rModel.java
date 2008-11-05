/*
 * @(#)rModel.java
 * Time-stamp: "2008-11-06 00:13:00 anton"
 */

import java.lang.reflect.Method;

/**
 * Describe class <code>rModel</code> here.
 *
 * @author dit06ajn@cs.umu.se
 * @version 1.0
 */
public class rModel { 
   /** Creates a new rModel instance. */
   public rModel() throws Exception {
      Class<?> c = Class.forName("Calc");
      System.out.println(c.getName());
      Method[] methods = c.getDeclaredMethods();
      for (int i = 0, length = methods.length; i < length; i++) {
         System.out.println(methods[i].getName());
         //methods.invoke(args);
         Class<?>[] params = methods[i].getParameterTypes();
         for (int j = 0; j < params.length; j++) {
            System.out.println("   \\-" + params[j].getName());
         }
         Object iC = c.newInstance();
         if (params.length == 2) {
         }
         else if (params.length == 1){
            methods[i].invoke(iC, -10);
         }
      }
   }
}
