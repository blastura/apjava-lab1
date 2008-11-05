import java.io.OutputStream;
import java.io.IOException;
import java.io.Writer;

/**
 * Adapter for a Writer to behave like an OutputStream.
 * <p>
 * Bytes are converted to chars using the platform default encoding.
 * If this encoding is not a single-byte encoding, some data may be lost.
 */
public class WriterOutputStream extends OutputStream {
   private final Writer writer;

   /**
    * Creates a WriterOutputStream
    *
    * @param writer a writer
    */
   public WriterOutputStream(Writer writer) {
      this.writer = writer;
   }

   /**
    * Writes a single character. The character to be written is contained
    * in the 16 low-order bits of the given integer value; the 16 high-order
    * bits are ignored.
    *
    * @param b int specifying a character to be written
    * @throws IOException if an I/O error occurs
    */
   @Override
   public void write(int b) throws IOException {
      // It's tempting to use writer.write((char) b), but that may get the encoding wrong
      // This is inefficient, but it works
      write(new byte[] {(byte) b}, 0, 1);
   }

   /**
    * Writes a portion of an array of characters.
    *
    * @param b array of characters
    * @param off offset from which to start writing characters
    * @param len - number of characters to write
    * @throws IOException if an I/O error occurs
    */
   @Override
   public void write(byte[] b, int off, int len) throws IOException {
      writer.write(new String(b, off, len));
   }

   /**
    * Flushes the stream. If the stream has saved any characters from the
    * various write() methods in a buffer, write them immediately to their
    * intended destination. Then, if that destination is another character
    * or byte stream, flush it. Thus one flush() invocation will flush all
    * the buffers in a chain of Writers and OutputStreams.
    */
   
   @Override
   public void flush() throws IOException {
      writer.flush();
   }

   /**
    * Closes the stream, flushing it first. Once the stream has been closed,
    * further write() or flush() invocations will cause an IOException to be thrown.
    * Closing a previously closed stream has no effect.
    */
   @Override
   public void close() throws IOException {
      writer.close();
   }
}
