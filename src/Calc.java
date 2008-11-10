public class Calc implements Plugable {
   public Calc () {
   }
   
   public String getDescription () {
      return "A simple calculator";
   }

    public void add (Integer a, Integer b) {
      int res = a + b;
      System.out.println (res);
   }

   public void sub (Integer a, Integer b) {
      int res = a - b;
      System.out.println (res);
   }

   public void neg (Integer a) {
      System.out.println (0 - a);
   }
}
