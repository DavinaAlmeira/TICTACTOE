/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #8
 * 1 - 5026231023 - Nadya Luthfiyah Rahma
 * 2 - 5026231094 - Davina Almeira
 * 3 - 5026231148 - Tiara Aulia Azadirachta Indica
 */
package TicTacToe;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

public class Cell {
    public static final int SIZE = 100; // ukuran sel (grid)
    public static final int PADDING = 24;
    public static final int SEED_SIZE = 72;
    public static final int SEED_STROKE_WIDTH = 8;
    private static final ImageIcon CROSS_ICON = new ImageIcon("src/cat.gif");
    private static final ImageIcon NOUGHT_ICON = new ImageIcon("src/doggie.gif");
    public int row, col; // posisi baris dan kolom
    public Seed content; // isi sel

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.content = Seed.NO_SEED; // kosong secara default
    }

    public void newGame() {
        content = Seed.NO_SEED;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(8.0F, 1, 1));

        int cellSize = 100; // Ukuran kotak papan
        int padding = 24;   // Padding dari sisi kotak
        int x1 = this.col * cellSize + padding;
        int y1 = this.row * cellSize + padding;
        int imageSize = cellSize - 2 * padding; // Ukuran gambar pas

        if (this.content == Seed.CROSS) {
            g.drawImage(CROSS_ICON.getImage(), x1, y1, imageSize, imageSize, null);
        } else if (this.content == Seed.NOUGHT) {
            g.drawImage(NOUGHT_ICON.getImage(), x1, y1, imageSize, imageSize, null);
        }

        // Hitung posisi untuk menggambar
        /* int x1 = col * SIZE;
        int y1 = row * SIZE;

        // Gambar konten berdasarkan isi sel
        if (content == Seed.CROSS) {
            g.setColor(new Color(255, 182, 193)); // Warna pemain 1
            g.fillOval(x1 + 10, y1 + 10, SIZE - 20, SIZE - 20); // Lingkaran pink
        } else if (content == Seed.NOUGHT) {
            g.setColor(new Color(173, 216, 230)); // Warna pemain 2
            g.fillOval(x1 + 10, y1 + 10, SIZE - 20, SIZE - 20); // Lingkaran biru
        }

        // Gambar garis grid
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(x1, y1, SIZE, SIZE); */ 
    }
}