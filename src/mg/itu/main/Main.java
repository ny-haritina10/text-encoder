package mg.itu.main;

import java.io.FileWriter;
import java.io.IOException;

import mg.itu.compressor.FileCompressor;

public class Main {
    
    // Partie - 1
    // public static void main(String[] args) {
    //     HuffmanCoder coder = new HuffmanCoder();
    //     String text = "hello";
        
    //     Map<Character, String> codes = coder.encode(text);
        
    //     System.out.println("Huffman Codes:");
    //     for (Map.Entry<Character, String> entry : codes.entrySet()) {
    //         System.out.println("'" + entry.getKey() + "': " + entry.getValue());
    //     }
        
    //     String compressed = coder.compress(text);
    //     System.out.println("\nOriginal text: " + text);
    //     System.out.println("Compressed: " + compressed);

    //     String decompressed = coder.decompress(compressed);
    //     System.out.println("Decompressed: " + decompressed);
    //     System.out.println("Decompression successful: " + text.equals(decompressed));
        
    //     // SwingUtilities.invokeLater(coder::visualizeTree);
    // }

    public static void main(String[] args) {
        try {
            FileCompressor compressor = new FileCompressor();

            String inputFile = "D:\\Studies\\ITU\\S6\\INF-310_Codage\\text-encoder\\__input_text.txt";
            String outputFile = "D:\\Studies\\ITU\\S6\\INF-310_Codage\\text-encoder\\__output_text.huff";
            
            // Create a sample text file if it doesn't exist
            try (FileWriter writer = new FileWriter(inputFile)) {
                writer.write("huffman coding example");
            }
            
            compressor.compressFile(inputFile, outputFile);
            System.out.println("File compressed successfully to " + outputFile);
        } catch (IOException e) {
            System.err.println("Error during compression: " + e.getMessage());
            e.printStackTrace();
        }
    }
}