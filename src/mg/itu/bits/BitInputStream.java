package mg.itu.bits;

import java.io.IOException;
import java.io.InputStream;

public class BitInputStream {
    
    private InputStream input;
    private int currentByte;
    private int bitsRemaining;

    public BitInputStream(InputStream input) {
        this.input = input;
        this.currentByte = 0;
        this.bitsRemaining = 0;
    }

    public int readBit() throws IOException {
        if (bitsRemaining == 0) {
            currentByte = input.read();
            if (currentByte == -1) return -1;
            bitsRemaining = 8;
        }
        bitsRemaining--;
        return (currentByte >>> bitsRemaining) & 1;
    }

    public void close() throws IOException {
        input.close();
    }
}