package mg.itu.compressor;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import mg.itu.bits.BitOutputStream;
import mg.itu.huffman.HuffmanCoder;

public class FileCompressor {
    private HuffmanCoder huffmanCoder;

    public FileCompressor() {
        this.huffmanCoder = new HuffmanCoder();
    }

    public void compressFile(String inputFilePath, String outputFilePath) throws IOException {
        String text = readTextFile(inputFilePath);

        Map<Character, String> huffmanCodes = huffmanCoder.encode(text);
        String compressedBits = huffmanCoder.compress(text);

        try (FileOutputStream fos = new FileOutputStream(outputFilePath);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             DataOutputStream dos = new DataOutputStream(bos)) {
            
            writeHeader(dos, huffmanCodes, compressedBits);

            BitOutputStream bitOutput = new BitOutputStream(dos);
            for (char bit : compressedBits.toCharArray()) {
                bitOutput.writeBit(bit == '0' ? 0 : 1);
            }
            
            int paddingBits = bitOutput.getPaddingBits();

            dos.writeByte(paddingBits);  
            bitOutput.close();      
            
            dos.flush();
        }
    }

    private String readTextFile(String filePath) throws IOException {
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append("\n");
            }
        }
        if (text.length() > 0 && text.charAt(text.length() - 1) == '\n') {
            text.setLength(text.length() - 1);
        }
        return text.toString();
    }

    private void writeHeader(DataOutputStream dos, Map<Character, String> huffmanCodes, String compressedBits) 
        throws IOException 
    {
        dos.writeBytes("HUFF");         // magic number     
        dos.writeInt(huffmanCodes.size());
        
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            char character = entry.getKey();
            String code = entry.getValue();
            
            dos.writeChar(character);         
            dos.writeByte(code.length());

            for (char bit : code.toCharArray()) 
            { dos.writeByte(bit == '0' ? 0 : 1); }

            int padding = (8 - (code.length() % 8)) % 8;
            for (int i = 0; i < padding; i++) {
                dos.writeByte(0);
            }
        }
        
        dos.writeInt(compressedBits.length());
    }
}