package mg.itu.main;

import java.io.IOException;

import mg.itu.compressor.FileCompressor;
import mg.itu.compressor.FileDecompressor;

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
    

    // Partie - 2
    public static void main(String[] args) {
        try {
            FileCompressor compressor = new FileCompressor();
            FileDecompressor decompressor = new FileDecompressor();

            String inputFile = "D:\\Studies\\ITU\\S6\\INF-310_Codage\\text-encoder\\__input_text.txt";
            String outputFile = "D:\\Studies\\ITU\\S6\\INF-310_Codage\\text-encoder\\__output_text.huff";
            String decompressedFile = "D:\\Studies\\ITU\\S6\\INF-310_Codage\\\\text-encoder\\__decompressed_text.txt";
            
            compressor.compressFile(inputFile, outputFile);
            decompressor.decompressFile(outputFile, decompressedFile);

            System.out.println("File compressed successfully to " + outputFile);
            System.out.println("File decompressed successfully to " + decompressedFile);
        } catch (IOException e) {
            System.err.println("Error during compression: " + e.getMessage());
            e.printStackTrace();
        }
    }
}