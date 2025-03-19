package mg.itu.bits;

import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream {

    private OutputStream output;
    private int currentByte;
    private int numBitsFilled;

    public BitOutputStream(OutputStream output) {
        this.output = output;
        this.currentByte = 0;
        this.numBitsFilled = 0;
    }

    public void writeBit(int bit) throws IOException {
        if (bit != 0 && bit != 1) {
            throw new IllegalArgumentException("Bit must be 0 or 1");
        }
        currentByte = (currentByte << 1) | bit;
        numBitsFilled++;
        
        if (numBitsFilled == 8) {
            output.write(currentByte);
            currentByte = 0;
            numBitsFilled = 0;
        }
    }

    public void close() throws IOException {
        while (numBitsFilled > 0) {
            writeBit(0);
        }
        output.close();
    }

    public int getPaddingBits() {
        return numBitsFilled == 0 ? 0 : 8 - numBitsFilled;
    }
}