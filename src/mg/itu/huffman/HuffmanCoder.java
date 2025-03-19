package mg.itu.huffman;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

public class HuffmanCoder {
    private HuffmanTree huffmanTree;

    public HuffmanCoder() {
        this.huffmanTree = new HuffmanTree();
    }

    public Map<Character, Integer> calculateFrequencies(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty");
        }

        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap;
    }

    public Map<Character, String> encode(String text) {
        Map<Character, Integer> frequencyMap = calculateFrequencies(text);
        huffmanTree.buildTree(frequencyMap);
        return huffmanTree.getHuffmanCodes();
    }

    public String compress(String text) {
        Map<Character, String> codes = encode(text);
        StringBuilder compressed = new StringBuilder();
        
        for (char c : text.toCharArray()) {
            compressed.append(codes.get(c));
        }
        
        return compressed.toString();
    }

    public String decompress(String compressedText) {
        return huffmanTree.decode(compressedText);
    }

    public void visualizeTree() {
        JFrame frame = new JFrame("Huffman Tree Visualization");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new HuffmanTreePanel(huffmanTree.getRoot()));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}