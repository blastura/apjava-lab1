/*
 * @(# )MyToolbox.java
 * Time-stamp: "2008-11-13 03:23:30 anton"
 */

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ListSelectionModel;

/**
 * Describe class MyToolbox here.
 *
 * @author Anton Johansson, dit06ajn@cs.umu.se
 * @version 1.0
 */
public class MyToolbox extends JFrame {
    private ClassHandler classHandler;
    private JTextField paramField;
    private JList methodList;
    
    /** TODO. Creates a new MyToolbox instance. */
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
        methodList.setSelectedIndex(0);
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
        // Default number of rows TODO hardcoded.
        textArea.setRows(20);
        textArea.setColumns(40);
        textArea.setEditable(false);
        JScrollPane scrollPaneForTextArea = new JScrollPane(textArea);
        // TODO size
        // scrollPaneForTextArea.setPreferredSize(new Dimension(200, 200));
        lowerPanel.add(scrollPaneForTextArea, BorderLayout.CENTER);

        // Redirect System.out to textArea
        PrintStream guiOutPrintStream =
            new PrintStream(new WriterOutputStream(new TextAreaWriter(textArea)));
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
    
    public void invokeSelectedMethod() {
        if (methodList.isSelectionEmpty()) {
            System.out.println("No method is selected");
            return;
        }
        Method method = (Method) methodList.getSelectedValue();
        
        // Read and parse paramField TODO
        String[] params =
            paramField.getText().split(" +|, *");
        
        try {
            Object ret = classHandler.invoke(method,
                                             params);
            System.out.println("Method returned : " + ret);
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
            // TODO linelength?
            System.out.println("Parameters can't be converted"
                               + "from a String to required class");
            e.printStackTrace();
        }
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
                // TODO
                e.printStackTrace();
                System.exit(0);
            }
        } else {
            System.err.println("You must supply one (and only one) Class name");
            System.exit(0);
        }
    }
}