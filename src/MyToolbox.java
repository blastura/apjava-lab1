/*
 * @(# )MyToolbox.java
 * Time-stamp: "2008-11-10 22:21:10 anton"
 */

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Describe class MyToolbox here.
 *
 * @author Anton Johansson, dit06ajn@cs.umu.se
 * @version 1.0
 */
public class MyToolbox extends JFrame {
    /** Creates a new MyToolbox instance. */
    public MyToolbox(String className) throws ClassNotFoundException,
                                              IllegalAccessException,
                                              InstantiationException {
        super("MyToolbox: "+ className);
        // could throw Exceptions
        final ClassHandler classHandler = new ClassHandler(className);

        // Upper panel
        JPanel upperPanel = new JPanel(new BorderLayout());
        JTextField descField = new JTextField(classHandler.getDescription());
        JTextField paramField = new JTextField("paramField");
        upperPanel.add(descField, BorderLayout.NORTH);
        upperPanel.add(paramField, BorderLayout.SOUTH);

        // Lower panel
        JPanel lowerPanel = new JPanel(new BorderLayout());
        final JList methodList = new JList(classHandler.getMethods());
        methodList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent ev) {
                    if (ev.getClickCount() == 2) {
                        int index = methodList.locationToIndex(ev.getPoint());
                        Method methodClicked =
                            (Method) methodList.getModel().getElementAt(index);
                        // System.out.println("Double clicked on Item "
                        // + methodClicked);
                        try {
                            System.out.println("Invoke "
                                + classHandler.invoke(methodClicked,
                                                        new String[] {"2", "2"}));
                        } catch (IllegalArgumentException e) {
                            // TODO
                            System.out.println(e.getMessage());
                        } catch (InstantiationException e) {
                            // TODO
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            // TODO
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            // TODO
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            // TODO
                            e.printStackTrace();
                        }
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
     * Starts the program.
     *
     * @param args should contain the name of a Class existing in
     * current classpath.
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                new MyToolbox(args[0]);
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found");
                e.printStackTrace();
                System.exit(0);
            } catch (InstantiationException e) {
                System.out.println("Could not create an instance of this class");
                e.printStackTrace();
                System.exit(0);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                System.exit(0);
            }
        } else {
            System.err.println("You must supply one (and only one) Class name");
            System.exit(0);
        }
    }
}