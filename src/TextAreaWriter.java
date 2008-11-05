import java.io.IOException;
import java.io.Writer;
import javax.swing.text.JTextComponent;

/**
 * Writes text to an JTextComponent.
 *
 * @author johane
 * @version 2008-10-25
 *
 */
public class TextAreaWriter extends Writer {
   private JTextComponent textComp;

   /**
    * Creates a new TextAreaWriter that writes to a JTextComponent.
    *
    * @param tc the JTextComponent that the stream should write to
    */
   public TextAreaWriter(JTextComponent tc) {
      textComp=tc;
   }

   /**
    * Closes the stream. Once the stream has been
    * closed, further write() or flush() invocations will cause an
    * IOException to be thrown. Closing a previously closed stream has
    * no effect.
    *
    * @throws IOException if an I/O error occurs
    */
   @Override
   public void close() throws IOException {
      textComp=null;
   }

   /**
    * Flushes the stream (does nothing in this case).
    *
    * @throws IOException if an I/O error occur (in this case the stream is closed)
    */
   @Override
   public void flush() throws IOException {
      if(textComp==null) throw new IOException();
   }

   /**
    * Writes a portion of an array of characters.
    *
    * @param cbuf array of characters
    * @param off offset from which to start writing characters
    * @param len - number of characters to write
    * @throws IOException if an I/O error occurs
    */
   @Override
   public void write(char[] cbuf, int off, int len) throws IOException {
      if(textComp==null) throw new IOException();
      String s=new String(cbuf,off,len);
      textComp.setText(textComp.getText()+s);
   }
}
