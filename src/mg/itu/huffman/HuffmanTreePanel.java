package mg.itu.huffman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class HuffmanTreePanel extends JPanel {
    private Node root;
    private static final int NODE_RADIUS = 20;
    private static final int VERTICAL_GAP = 80;
    private static final int HORIZONTAL_GAP = 40;
    private Map<Node, Point> nodePositions;

    public HuffmanTreePanel(Node root) {
        this.root = root;
        this.nodePositions = new HashMap<>();
        setPreferredSize(new Dimension(800, 600));
        calculatePositions();
    }

    private void calculatePositions() {
        if (root != null) {
            calculateNodePositions(root, 400, 50, 200);
        }
    }

    private void calculateNodePositions(Node node, int x, int y, int width) {
        if (node == null) return;

        nodePositions.put(node, new Point(x, y));

        int childWidth = width / 2;
        if (node.getLeftChild() != null) {
            calculateNodePositions(node.getLeftChild(), x - childWidth, y + VERTICAL_GAP, childWidth);
        }
        if (node.getRightChild() != null) {
            calculateNodePositions(node.getRightChild(), x + childWidth, y + VERTICAL_GAP, childWidth);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (root != null) {
            drawTree(g2d, root);
        }
    }

    private void drawTree(Graphics2D g2d, Node node) {
        Point currentPos = nodePositions.get(node);
        if (currentPos == null) return;

        // Draw edges first
        if (node.getLeftChild() != null) {
            Point leftPos = nodePositions.get(node.getLeftChild());
            g2d.drawLine(currentPos.x, currentPos.y, leftPos.x, leftPos.y);
            g2d.drawString("0", (currentPos.x + leftPos.x) / 2, (currentPos.y + leftPos.y) / 2);
        }
        if (node.getRightChild() != null) {
            Point rightPos = nodePositions.get(node.getRightChild());
            g2d.drawLine(currentPos.x, currentPos.y, rightPos.x, rightPos.y);
            g2d.drawString("1", (currentPos.x + rightPos.x) / 2, (currentPos.y + rightPos.y) / 2);
        }

        // Draw node
        g2d.setColor(Color.WHITE);
        g2d.fillOval(currentPos.x - NODE_RADIUS, currentPos.y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(currentPos.x - NODE_RADIUS, currentPos.y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        // Draw node content
        String text = node.isLeaf() 
            ? String.format("'%s'(%d)", node.getCharacter(), node.getFrequency())
            : String.valueOf(node.getFrequency());
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        g2d.drawString(text, currentPos.x - textWidth / 2, currentPos.y + fm.getHeight() / 4);
        
        // Recursively draw children
        drawTree(g2d, node.getLeftChild());
        drawTree(g2d, node.getRightChild());
    }
}