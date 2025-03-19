package mg.itu.main;

import java.util.Map;

import mg.itu.huffman.HuffmanCoder;

public class Main {
    
    public static void main(String[] args) {
        HuffmanCoder coder = new HuffmanCoder();
        String text = "hello";
        
        Map<Character, String> codes = coder.encode(text);
        
        System.out.println("Huffman Codes:");
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            System.out.println("'" + entry.getKey() + "': " + entry.getValue());
        }
        
        String compressed = coder.compress(text);
        System.out.println("\nOriginal text: " + text);
        System.out.println("Compressed: " + compressed);

        String decompressed = coder.decompress(compressed);
        System.out.println("Decompressed: " + decompressed);
        System.out.println("Decompression successful: " + text.equals(decompressed));
        
        // SwingUtilities.invokeLater(coder::visualizeTree);
    }
}