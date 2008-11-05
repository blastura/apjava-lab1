/*
 * @(#)MyToolbox.java
 * Time-stamp: "2008-11-05 19:34:20 anton"
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;

/**
 * Describe class MyToolbox here.
 *
 * @author Anton Johansson, dit06ajn@cs.umu.se
 * @version 1.0
 */
public class MyToolbox {
   /**
    * Creates a new MyToolbox instance.
    *
    */
   public MyToolbox() {
      JPanel panel = new JPanel(new GridLayout(4, 1));
      JTextField descField = new JTextField("descField");
      JTextField descFieldt = new JTextField("descField");
      panel.add(descField);
      panel.add(descFieldt);

      JFrame frame = new JFrame("MyToolbox");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(panel);
      frame.setSize(500, 500);
      frame.setVisible(true);
   }

   /**
    * Starts the program
    *
    * @param args should contain a Classname
    */
   public static void main(String[] args) {
      new MyToolbox();
   }
}