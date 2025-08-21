package com.joe.asciirenderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SwingWindow extends JPanel {

    private String[] asciiLines = new String[0];
    private Font baseFont = new Font("Monospaced", Font.PLAIN, 12);
    private JFrame frame;

    public SwingWindow(int width, int height) {
        frame = new JFrame("Ascii Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.add(this);
        frame.setVisible(true);
    }

    public void updateFrame(String ascii) {
        SwingUtilities.invokeLater(() -> {
            this.asciiLines = ascii.split("\n");
            repaint(); // trigger redraw
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (asciiLines.length == 0) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.setBackground(Color.BLACK);
        g2d.clearRect(0, 0, getWidth(), getHeight());

        int rows = asciiLines.length;
        int cols = asciiLines[0].length();

        // Calculate font size so ASCII fits
        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;
        int fontSize = Math.max(4, Math.min(cellWidth, cellHeight));

        Font scaledFont = baseFont.deriveFont((float) fontSize);
        g2d.setFont(scaledFont);
        FontMetrics fm = g2d.getFontMetrics();

        int y = fm.getAscent();
        for (String line : asciiLines) {
            g2d.drawString(line, 0, y);
            y += fm.getHeight();
        }
    }
}
