/*
 * @(#)MyToolbox.java
 * Time-stamp: "2008-11-16 23:52:40 anton"
 */

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 * MyToolbox is responsible for drawing and controlling the GUI of this
 * application. MyToolbox draws an interface for interacting with a Java
 * class. Though the interface it is possible for a user to specify parameters
 * and invoke methods of the loaded class.
 *
 * @author Anton Johansson, dit06ajn@cs.umu.se
 * @version 1.0
 */
public class MyToolbox extends JFrame {
    private ClassHandler classHandler;
    private JList methodList;
    private JTextField paramField;
    private String splitPattern = " +|, *";
    private static Logger logger = Logger.getLogger("mytoolbox");

    /**
     * Initializes the GUI, creates a ClassHandler instance, redirects
     * System.out to a JTextArea in the GUI.
     *
     * @param className Should point to a java class existing in classpath. The
     *                  class should implement the interface Plugable, and at
     *                  least one constructor which take no parameters is
     *                  required.
     * @exception ClassNotFoundException If the specified className is not a
     *                                   Class in classpath.
     * @exception IllegalAccessException If the specified Class constructor
     *                                   doesn't have access to the definition
     *                                   of this Constructor.
     * @exception InstantiationException If the specified Class doesn't have a
     *                                   constructor without parameters.
     */
    public MyToolbox(String className) throws ClassNotFoundException,
                                              IllegalAccessException,
                                              InstantiationException {
        super("MyToolbox: "+ className);

        // could throw Exceptions
        this.classHandler = new ClassHandler(className);

        // Upper panel
        JPanel upperPanel = new JPanel(new BorderLayout());
        JTextField descField = new JTextField(classHandler.getDescription());
        descField.setEditable(false);

        this.paramField
            = new JTextField("Type parameters here, separated by ' ' or ','");
        paramField.addKeyListener(new KeyAdapter() {
                /**
                 * Run invokeSelectedMethod if ENTER is pressed in
                 * this JTextField.
                 */
                @Override
                public void keyReleased(KeyEvent ev) {
                    // Releasing ENTER key
                    if (ev.getKeyCode() == KeyEvent.VK_ENTER) {
                        invokeSelectedMethod();
                    }
                }
            });
        upperPanel.add(descField, BorderLayout.NORTH);
        upperPanel.add(paramField, BorderLayout.SOUTH);

        // Lower panel
        JPanel lowerPanel = new JPanel(new BorderLayout());
        this.methodList = new JList(classHandler.getMethods());
        methodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        try {
            methodList.setSelectedIndex(0);
        } catch (NullPointerException e) {
            // TODO - move this to main
            System.out.println("Class doesn't contain any methods");
            System.exit(-1);
        }
        methodList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent ev) {
                    if (ev.getClickCount() == 2) {
                        invokeSelectedMethod();
                    }
                }
            });
        lowerPanel.add(methodList, BorderLayout.NORTH);

        // Textarea for output
        JTextArea textArea = new JTextArea();
        // Settings for textArea
        textArea.setRows(20);
        textArea.setColumns(40);
        textArea.setEditable(false);
        JScrollPane scrollPaneForTextArea = new JScrollPane(textArea);
        lowerPanel.add(scrollPaneForTextArea, BorderLayout.CENTER);

        // Redirect System.out to textArea
        PrintStream guiOutPrintStream =
            new PrintStream(
                new WriterOutputStream(new TextAreaWriter(textArea)));
        System.setOut(guiOutPrintStream);

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
     * Invokes the selected method in the field JList methodList. This
     * method handles all exceptions that could be thrown from this
     * invocation.
     */
    public void invokeSelectedMethod() {
        if (methodList.isSelectionEmpty()) {
            System.out.println("No method is selected");
            return;
        }

        Method method = (Method) methodList.getSelectedValue();

        // Read and parse paramField
        String paramFieldText = paramField.getText().trim();
        String[] args = paramFieldText.split(splitPattern);

        // If method the method requires 0 formal parameters, the
        // supplied array must be of length 0. An empty JTextField
        // always contains the empty string "".
        if (args[0].equals("")) {
            args = new String[] {};
        }

        try {
            Object ret  = classHandler.invoke(method, args);
            logger.info("Method returned: " + ret);
        } catch (IllegalArgumentException e) {
            // The method has been passed illegal or inappropriate
            // arguments.
            System.out.println(e.getMessage());
        } catch (InvocationTargetException e) {
            System.out.println("Parameters can't be converted from a String to"
                               + " the required class");
        } catch (NoSuchMethodException e) {
            // The class stored in classHandler doesn't have the specified
            // method. This should never happen.
            e.printStackTrace();
            System.exit(-1);
        } catch (InstantiationException e) {
            // Should never happen, NoSuchMethodException should be thrown
            // first.
            e.printStackTrace();
            System.exit(-1);
        } catch (IllegalAccessException e) {
            // The specified method doesn't have access to the definition of the
            // instantiated class in classHandler. This should never
            // happen.
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Starts the program.
     *
     * @param args should contain the name of a Class existing in
     * current classpath. This class should implement the interface
     * Plugable and contain at a constructor which doesn't require
     * parameters. If supplied argument doesn't match these requirements,
     */
    public static void main(String[] args) {
        Logger.getLogger("mytoolbox").setLevel(Level.OFF);

        if (args.length == 1) {
            try {
                new MyToolbox(args[0]);
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found");
                //e.printStackTrace();
                System.exit(-1);
            } catch (InstantiationException e) {
                System.out.println("Could not create an instance of this class."
                                   + " One empty constructor required");
                //e.printStackTrace();
                System.exit(-1);
            } catch (ClassCastException e) {
                System.out.println("Class must implement interface Plugable");
                System.exit(-1);
            } catch (IllegalAccessException e) {
                // TODO
                e.printStackTrace();
                System.exit(-1);
            }
        } else {
            System.err.println("You must supply one (and only one) Class name");
            System.exit(-1);
        }
    }
}