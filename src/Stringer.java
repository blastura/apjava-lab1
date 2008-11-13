public class Stringer implements Plugable {
   public Stringer () {
   }

   public String getDescription () {
      return "A simple string manipulator";
   }

   public void conc (String a, String b) {
      System.out.println (a + " " + b);
   }

   public void backwards (String a) {
      char c[] = a.toCharArray();
      for (int i=c.length - 1; i >= 0; i--) {
         System.out.print (c[i]);
      }
      System.out.println ("");
   }
}
