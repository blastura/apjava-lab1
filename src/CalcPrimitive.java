public class CalcPrimitive implements Plugable {
   public CalcPrimitive () {
   }
   
   public String getDescription () {
      return "A simple calculator";
   }

    public void add (int a, int b) {
      int res = a + b;
      System.out.println (res);
   }

   public void sub (int a, int b) {
      int res = a - b;
      System.out.println (res);
   }

   public void neg (int a) {
      System.out.println (0 - a);
   }
}
