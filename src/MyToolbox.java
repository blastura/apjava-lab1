/*
 * @(# )MyToolbox.java
 * Time-stamp: "2008-11-06 22:59:24 anton"
 */

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Describe class MyToolbox here.
 *
 * @author Anton Johansson, dit06ajn@cs.umu.se
 * @version 1.0
 */
public class MyToolbox extends JFrame {
   /** Creates a new MyToolbox instance. */
   public MyToolbox(String className) throws Exception {
      super("MyToolbox: "+ className);
      rModel model = new rModel(className);
      
      // Upper panel
      JPanel upperPanel = new JPanel(new BorderLayout());
      JTextField descField = new JTextField(model.getDescription());
      JTextField paramField = new JTextField("paramField");
      upperPanel.add(descField, BorderLayout.NORTH);
      upperPanel.add(paramField, BorderLayout.SOUTH);
      
      // Lower panel
      JPanel lowerPanel = new JPanel(new BorderLayout());
      final JList methodList = new JList(model.getMethods());
      methodList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               if (e.getClickCount() == 2) {
                  int index = methodList.locationToIndex(e.getPoint());
                  System.out.println("Double clicked on Item "
                                     + methodList.getModel().getElementAt(index));
               }
            }
         });
      lowerPanel.add(methodList, BorderLayout.NORTH);
      JTextArea consoleOut = new JTextArea("hej och hå \n detta är text");
      lowerPanel.add(consoleOut, BorderLayout.CENTER);

      // Final panel
      JPanel panel = new JPanel(new BorderLayout());
      panel.add(upperPanel, BorderLayout.NORTH);
      panel.add(lowerPanel, BorderLayout.CENTER);
      
      // Frame      
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.getContentPane().add(panel);
      this.pack();
      this.setVisible(true);
   }

   /**
    * Starts the program
    *
    * @param args should contain the name of a Class existing in
    * current classpath.
    */
   public static void main(String[] args) throws Exception {
      if (args.length == 1) {
         new MyToolbox(args[0]);
      }
      else {
         System.err.println("You must supply one (and only one) Class name");
         System.exit(0);
      }
   }
}