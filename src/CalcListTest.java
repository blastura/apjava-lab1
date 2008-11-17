import java.util.List;

public class CalcListTest implements Plugable {
   public CalcListTest () {
   }
   
   public String getDescription () {
      return "A simple calculator";
   }

    public void add (List a, List b) {
        System.out.println(a + " " + b);
   }

   public void sub (Integer a, Integer b) {
      int res = a - b;
      System.out.println (res);
   }

   public void neg (Integer a) {
      System.out.println (0 - a);
   }
}
