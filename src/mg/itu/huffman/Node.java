package mg.itu.huffman;

public class Node implements Comparable<Node> {
    
    private Character character;
    private int frequency;
    private Node leftChild;
    private Node rightChild;

    public Node(Character character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.leftChild = null;
        this.rightChild = null;
    }

    public Node(int frequency, Node leftChild, Node rightChild) {
        this.character = null;
        this.frequency = frequency;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.frequency, other.frequency);
    }

    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }

    public Character getCharacter() {
        return character;
    }

    public int getFrequency() {
        return frequency;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }
}