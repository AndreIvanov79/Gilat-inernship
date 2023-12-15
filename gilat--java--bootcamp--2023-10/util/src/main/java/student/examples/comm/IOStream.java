package student.examples.comm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class IOStream {

	private final Logger LOGGER = LogManager.getLogger(SecureIOStream.class);
    final BufferedInputStream bIn;
    final BufferedOutputStream bOut;

    public IOStream(BufferedInputStream bIn, BufferedOutputStream bOut) {
        super();
        this.bIn = bIn;
        this.bOut = bOut;
    }

    public void send(int value) throws IOException {
        bOut.write(value);
        bOut.flush();
    }

    public int receive() throws IOException {
        if (bIn.available() > 0) {
            return bIn.read();
        }
        return -1;
    }
    
    public void sendBytes(byte[] value) {
        byte[] length = ByteBuffer.allocate(4).putInt(value.length).array();
        try {
            bOut.write(length);
            bOut.write(value);
            bOut.flush();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
    
    public byte[] receiveBytes() {
        try {
            DataInputStream dataInputStream = new DataInputStream(bIn);
            int length = dataInputStream.readInt();
            byte[] buffer = dataInputStream.readNBytes(length);
            return buffer;
        } catch (IOException e) {
            return new byte[0];
        }
    }

}
