package mg.itu.huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
    
    private Node root;
    private Map<Character, String> huffmanCodes;

    public HuffmanTree() {
        this.root = null;
        this.huffmanCodes = new HashMap<>();
    }

    public void buildTree(Map<Character, Integer> occurencesMap) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        
        for (Map.Entry<Character, Integer> entry : occurencesMap.entrySet()) {
            priorityQueue.offer(new Node(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() > 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            Node parent = new Node(left.getFrequency() + right.getFrequency(), left, right);

            priorityQueue.offer(parent);
        }

        this.root = priorityQueue.poll();
        generateCodes(root, "");
    }

    private void generateCodes(Node node, String code) {
        if (node == null) {
            return;
        }

        if (node.isLeaf()) {
            huffmanCodes.put(node.getCharacter(), code);
            return;
        }

        generateCodes(node.getLeftChild(), code + "0");
        generateCodes(node.getRightChild(), code + "1");
    }

    public Map<Character, String> getHuffmanCodes() {
        return new HashMap<>(huffmanCodes);
    }

    public Node getRoot() {
        return root;
    }

    public String decode(String encodedString) {
        if (root == null) {
            throw new IllegalStateException("Tree not built yet");
        }

        StringBuilder decoded = new StringBuilder();
        Node current = root;

        for (char bit : encodedString.toCharArray()) {
            if (bit == '0') {
                current = current.getLeftChild();
            } else if (bit == '1') {
                current = current.getRightChild();
            } else {
                throw new IllegalArgumentException("Invalid bit in encoded string: " + bit);
            }

            if (current.isLeaf()) {
                decoded.append(current.getCharacter());
                current = root;
            }
        }

        return decoded.toString();
    }
}