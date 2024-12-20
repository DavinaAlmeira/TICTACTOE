

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Import game packages
import connectFour.GameMain;
import TicTacToe.GameMainTicTac;
import TicTacToeAI.GameMainAI;

public class GameSelector {
    public static void main(String[] args) {
        // Membuat frame utama
        JFrame frame = new JFrame("Game Selector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Mengatur agar frame muncul di tengah dan ukuran layar penuh
        frame.setSize(400, 200); // Ukuran sementara yang lebih kecil
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Membuat frame fullscreen
        frame.setLayout(new BorderLayout());

        // Membuat panel dengan background gambar
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Membaca gambar untuk dijadikan latar belakang
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("background.jpg")); // Ganti dengan path gambar Anda
                Image image = backgroundImage.getImage();

                // Menggambar gambar pada panel
                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this); // Menyesuaikan gambar dengan ukuran panel
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Menggunakan BoxLayout untuk menumpuk elemen secara vertikal

        // Menambahkan label judul
        JLabel title = new JLabel("Pilih Game yang Ingin Dimainkan", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE); // Menentukan warna teks judul menjadi putih
        title.setAlignmentX(Component.CENTER_ALIGNMENT); // Menyesuaikan posisi tengah
        title.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0)); // Memberikan padding atas dan bawah
        panel.add(title);

        // Membuat panel untuk tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Mengatur tombol dengan jarak antar tombol
        buttonPanel.setOpaque(false); // Membuat panel tombol transparan

        // Tombol untuk Connect Four
        JButton connectFourButton = new JButton("Connect Four");
        connectFourButton.setPreferredSize(new Dimension(150, 50)); // Ukuran tombol
        connectFourButton.setBackground(new Color(75, 0, 130)); // Warna ungu untuk tombol
        connectFourButton.setForeground(new Color(220, 193, 190)); // Warna teks tombol
        connectFourButton.setFont(new Font("Arial", Font.BOLD, 16)); // Ukuran font
        connectFourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openConnectFour();
            }
        });
        buttonPanel.add(connectFourButton);

        // Tombol untuk Tic Tac Toe 
        JButton ticTacToeButton = new JButton("Tic Tac Toe");
        ticTacToeButton.setPreferredSize(new Dimension(150, 50)); // Ukuran tombol
        ticTacToeButton.setBackground(new Color(75, 0, 130, 215)); // Warna ungu untuk tombol
        ticTacToeButton.setForeground(new Color(220, 193, 190)); // Warna teks tombol
        ticTacToeButton.setFont(new Font("Arial", Font.BOLD, 16)); // Ukuran font
        ticTacToeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTicTacToe();
            }
        });
        buttonPanel.add(ticTacToeButton);

        // Tombol untuk TicTacToe AI
        JButton TTaiButton = new JButton("TicTacToe vs AI");
        TTaiButton.setPreferredSize(new Dimension(150, 50)); // Ukuran tombol
        TTaiButton.setBackground(new Color(75, 0, 130)); // Warna ungu untuk tombol
        TTaiButton.setForeground(new Color(220, 193, 190)); // Warna teks tombol
        TTaiButton.setFont(new Font("Arial", Font.BOLD, 16)); // Ukuran font
        TTaiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTicTacToeAI();
            }
        });
        buttonPanel.add(TTaiButton);

        // Menambahkan panel tombol ke panel utama
        panel.add(buttonPanel);

        // Menampilkan frame dan mengatur posisi di tengah
        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null); // Membuat frame muncul di tengah
        frame.setVisible(true);
    }

    // Membuka game Connect Four
    private static void openConnectFour() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Connect4");  // Judul Game
            GameMain game = new GameMain();
            frame.setContentPane(game);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); // Membuat frame muncul di tengah
            frame.setVisible(true);
        });
    }

    // Membuka game Tic Tac Toe
    private static void openTicTacToe() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameMainTicTac ticTacToeGame = new GameMainTicTac();
                JFrame frame = new JFrame("Tic Tac Toe");
                frame.setContentPane(ticTacToeGame);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null); // Membuat frame muncul di tengah
                frame.setVisible(true);
            }
        });
    }

    // Membuka game Tic Tac Toe AI
    private static void openTicTacToeAI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameMainAI ticTacToeGame = new GameMainAI();
                JFrame frame = new JFrame("Tic Tac Toe AI");
                frame.setContentPane(ticTacToeGame);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null); // Membuat frame muncul di tengah
                frame.setVisible(true);
            }
        });
    }
}