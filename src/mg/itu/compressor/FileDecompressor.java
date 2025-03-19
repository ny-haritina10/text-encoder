package mg.itu.compressor;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mg.itu.bits.BitInputStream;

public class FileDecompressor {

    public void decompressFile(String inputFilePath, String outputFilePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputFilePath);
             BufferedInputStream bis = new BufferedInputStream(fis);
             DataInputStream dis = new DataInputStream(bis);
             FileWriter writer = new FileWriter(outputFilePath)) {
            
            Map<String, Character> codeToCharMap = parseHeader(dis);

            int compressedLength = dis.readInt();
            BitInputStream bitInput = new BitInputStream(dis);
            int paddingBits = dis.readByte();

            StringBuilder currentCode = new StringBuilder();
            int bitsProcessed = 0;

            while (bitsProcessed < compressedLength - paddingBits) {
                int bit = bitInput.readBit();
                if (bit == -1) {
                    throw new IOException("Unexpected end of file");
                }
                
                currentCode.append(bit);
                bitsProcessed++;

                Character decodedChar = codeToCharMap.get(currentCode.toString());
                if (decodedChar != null) {
                    writer.write(decodedChar);
                    currentCode.setLength(0); 
                }
            }

            bitInput.close();
        }
    }

    private Map<String, Character> parseHeader(DataInputStream dis) 
        throws IOException 
    {
        byte[] magic = new byte[4];
        dis.readFully(magic);
        if (!new String(magic).equals("HUFF")) {
            throw new IOException("Invalid file format: incorrect magic number");
        }

        int numChars = dis.readInt();
        Map<String, Character> codeToCharMap = new HashMap<>();

        for (int i = 0; i < numChars; i++) {
            char character = dis.readChar();    
            int codeLength = dis.readByte() & 0xFF;  
            
            StringBuilder code = new StringBuilder();
            for (int j = 0; j < codeLength; j++) {
                int bit = dis.readByte();
                code.append(bit);
            }
            
            int padding = (8 - (codeLength % 8)) % 8;
            for (int j = 0; j < padding; j++) {
                dis.readByte();
            }
            
            codeToCharMap.put(code.toString(), character);
        }

        return codeToCharMap;
    }
}