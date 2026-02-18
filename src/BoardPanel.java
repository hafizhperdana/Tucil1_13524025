import java.awt.*;
import java.awt.geom.Path2D;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class BoardPanel extends JPanel {
    private Board board;
    private Map<Character, Color> colorMap; 

    private final Color[] PALETTE = {
        new Color(179, 157, 219), // Deep Purple
        new Color(255, 204, 128), // Deep Orange
        new Color(144, 202, 249), // Blue
        new Color(239, 154, 154), // Red
        new Color(165, 214, 167), // Green
        new Color(255, 245, 157), // Yellow
        new Color(176, 190, 197), // Blue Grey
        new Color(206, 147, 216), // Magenta
        new Color(188, 170, 164), // Brown
        new Color(128, 203, 196)  // Teal
    };

    public BoardPanel() {
        this.colorMap = new HashMap<>();
        this.setBackground(new Color(220, 220, 220)); 
    }

    public void setBoard(Board board) {
        this.board = board;
        generateColors();
        repaint();
    }

    private void generateColors() {
        colorMap.clear();
        if (board == null) return;
        int N = board.getSize();
        int paletteIndex = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                char code = board.getColor(i, j);
                if (!colorMap.containsKey(code)) {
                    colorMap.put(code, PALETTE[paletteIndex % PALETTE.length]);
                    paletteIndex++;
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (board == null) {
            drawEmptyState(g);
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

        int N = board.getSize();
        int w = getWidth();
        int h = getHeight();

        int margin = 30; 
        int availableSize = Math.min(w, h) - (2 * margin);
        int cellSize = availableSize / N;
        
        int startX = (w - (cellSize * N)) / 2;
        int startY = (h - (cellSize * N)) / 2;

        // Shadow
        g2d.setColor(new Color(0, 0, 0, 0));
        g2d.fillRect(startX + 6, startY + 6, cellSize * N, cellSize * N);

        // Grid
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int x = startX + j * cellSize;
                int y = startY + i * cellSize;

                char colorCode = board.getColor(i, j);
                g2d.setColor(colorMap.getOrDefault(colorCode, Color.WHITE));
                g2d.fillRect(x, y, cellSize, cellSize);

                g2d.setColor(new Color(0, 0, 0, 70)); 
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRect(x, y, cellSize, cellSize);
            }
        }

        // Border
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int x = startX + j * cellSize;
                int y = startY + i * cellSize;
                char currentColor = board.getColor(i, j);

                if (i == 0 || board.getColor(i - 1, j) != currentColor) {
                    g2d.drawLine(x, y, x + cellSize, y);
                }

                if (j == 0 || board.getColor(i, j - 1) != currentColor) {
                    g2d.drawLine(x, y, x, y + cellSize);
                }
                
                if (i == N - 1) {
                     g2d.drawLine(x, y + cellSize, x + cellSize, y + cellSize);
                }
                
                if (j == N - 1) {
                    g2d.drawLine(x + cellSize, y, x + cellSize, y + cellSize);
                }
            }
        }

        // Queen
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board.getQueenCol(i) == j) {
                    int x = startX + j * cellSize;
                    int y = startY + i * cellSize;
                    drawCrown(g2d, x, y, cellSize);
                }
            }
        }
        
        // Border
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(startX, startY, cellSize * N, cellSize * N);
    }

    private void drawCrown(Graphics2D g2d, int x, int y, int size) {
        int pad = size / 5;
        int w = size - 2 * pad;
        int h = size - 2 * pad;
        int cx = x + pad;
        int cy = y + pad;

        Path2D crown = new Path2D.Double();
        crown.moveTo(cx, cy + h * 0.3); 
        crown.lineTo(cx + w * 0.25, cy + h * 0.6); 
        crown.lineTo(cx + w * 0.5, cy + h * 0.1); 
        crown.lineTo(cx + w * 0.75, cy + h * 0.6); 
        crown.lineTo(cx + w, cy + h * 0.3); 
        crown.lineTo(cx + w * 0.9, cy + h * 0.9); 
        crown.lineTo(cx + w * 0.1, cy + h * 0.9); 
        crown.closePath();

        g2d.setColor(Color.BLACK);
        g2d.fill(crown);
    }

    private void drawEmptyState(Graphics g) {
        g.setColor(Color.GRAY);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        String msg = "Silakan Pilih File (.txt)";
        FontMetrics fm = g.getFontMetrics();
        g.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, getHeight() / 2);
    }
}