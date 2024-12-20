/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #8
 * 1 - 5026231023 - Nadya Luthfiyah Rahma
 * 2 - 5026231094 - Davina Almeira
 * 3 - 5026231148 - Tiara Aulia Azadirachta Indica
 */

import javax.swing.*;

import TicTacToe.GameMainTicTac;
import TicTacToeAI.GameMainAI;
import connectFour.GameMain;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSelector {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Membuat frame utama
            JFrame frame = new JFrame("Main Menu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null); // Menempatkan frame di tengah layar

            // Panel utama dengan latar belakang gambar
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
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Menggunakan BoxLayout untuk tata letak vertikal

            // Menambahkan menu bar
            JMenuBar menuBar = new JMenuBar();

            // Menu utama
            JMenu fileMenu = new JMenu("File");
            JMenuItem exitMenuItem = new JMenuItem("Exit");
            exitMenuItem.addActionListener(e -> System.exit(0));
            fileMenu.add(exitMenuItem);
            menuBar.add(fileMenu);

            JMenu helpMenu = new JMenu("Help");
            JMenuItem aboutMenuItem = new JMenuItem("About");
            aboutMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Game Develop by Group 8 :\n * 5026231023 - Nadya Luthfiyah Rahma\n" + //
                                " * 5026231094 - Davina Almeira\n" + //
                                " * 5026231148 - Tiara Aulia Azadirachta Indica"));
            helpMenu.add(aboutMenuItem);
            menuBar.add(helpMenu);

            frame.setJMenuBar(menuBar);

            // Tambahkan spacer di atas
            panel.add(Box.createVerticalGlue());

            // Menambahkan label judul
            JLabel title = new JLabel("Pilih Game yang Ingin Dimainkan", SwingConstants.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setForeground(Color.WHITE); // Warna teks putih
            title.setAlignmentX(Component.CENTER_ALIGNMENT); // Pusatkan secara horizontal
            title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Padding atas dan bawah
            panel.add(title);

            // Membuat panel untuk tombol
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Mengatur tata letak tombol
            buttonPanel.setOpaque(false); // Membuat panel tombol transparan

            // Tombol untuk Connect Four
            JButton connectFourButton = new JButton("Connect Four");
            connectFourButton.setPreferredSize(new Dimension(150, 50));
            connectFourButton.setBackground(new Color(75, 0, 130)); // Warna ungu
            connectFourButton.setForeground(new Color(220, 193, 190)); // Warna teks
            connectFourButton.setFont(new Font("Arial", Font.BOLD, 16));
            connectFourButton.addActionListener(new ActionListener() {
                @Override
            public void actionPerformed(ActionEvent e) {
                openConnectFour();
            }
            });
            buttonPanel.add(connectFourButton);

            // Tombol untuk Tic Tac Toe
            JButton ticTacToeButton = new JButton("Tic Tac Toe");
            ticTacToeButton.setPreferredSize(new Dimension(150, 50));
            ticTacToeButton.setBackground(new Color(75, 0, 130, 215)); // Warna ungu
            ticTacToeButton.setForeground(new Color(220, 193, 190)); // Warna teks
            ticTacToeButton.setFont(new Font("Arial", Font.BOLD, 16));
            ticTacToeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openTicTacToe();
                }
            });
            buttonPanel.add(ticTacToeButton);

            // Tombol untuk TicTacToe AI
            JButton TTaiButton = new JButton("TicTacToe vs AI");
            TTaiButton.setPreferredSize(new Dimension(150, 50));
            TTaiButton.setBackground(new Color(75, 0, 130)); // Warna ungu
            TTaiButton.setForeground(new Color(220, 193, 190)); // Warna teks
            TTaiButton.setFont(new Font("Arial", Font.BOLD, 16));
            TTaiButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openTicTacToeAI();
                }
            });
            buttonPanel.add(TTaiButton);

            // Menambahkan panel tombol ke panel utama
            buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(buttonPanel);

            // Tambahkan spacer di bawah
            panel.add(Box.createVerticalGlue());

            // Menambahkan panel utama ke frame
            frame.add(panel);

            // Tampilkan frame
            frame.setVisible(true);
        });
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

            // Tombol Exit untuk kembali ke Main Menu
            JButton exitButton = new JButton("Exit to Main Menu");
            exitButton.addActionListener(e -> {
                frame.dispose();
                main(new String[0]); // Menampilkan kembali Main Menu
            });
            // Menambahkan exitButton ke panel game dengan BorderLayout.SOUTH
            JPanel gamePanel = new JPanel(new BorderLayout());
            gamePanel.add(game, BorderLayout.CENTER); // Menambahkan game ke panel
            gamePanel.add(exitButton, BorderLayout.SOUTH); // Menambahkan tombol exit di bawah
            frame.setContentPane(gamePanel); // Mengatur ulang content pane
            frame.revalidate(); // Revalidate frame setelah perubahan
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

                // Tombol Exit untuk kembali ke Main Menu
                JButton exitButton = new JButton("Exit to Main Menu");
                exitButton.addActionListener(e -> {
                    frame.dispose();
                    main(new String[0]); // Menampilkan kembali Main Menu
                });
                // Menambahkan exitButton ke panel game dengan BorderLayout.SOUTH
                JPanel gamePanel = new JPanel(new BorderLayout());
                gamePanel.add(ticTacToeGame, BorderLayout.CENTER); // Menambahkan game ke panel
                gamePanel.add(exitButton, BorderLayout.SOUTH); // Menambahkan tombol exit di bawah
                frame.setContentPane(gamePanel); // Mengatur ulang content pane
                frame.revalidate(); // Revalidate frame setelah perubahan
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

                // Tombol Exit untuk kembali ke Main Menu
                JButton exitButton = new JButton("Exit to Main Menu");
                exitButton.addActionListener(e -> {
                    frame.dispose();
                    main(new String[0]); // Menampilkan kembali Main Menu
                });
                // Menambahkan exitButton ke panel game dengan BorderLayout.SOUTH
                JPanel gamePanel = new JPanel(new BorderLayout());
                gamePanel.add(ticTacToeGame, BorderLayout.CENTER); // Menambahkan game ke panel
                gamePanel.add(exitButton, BorderLayout.SOUTH); // Menambahkan tombol exit di bawah
                frame.setContentPane(gamePanel); // Mengatur ulang content pane
                frame.revalidate(); // Revalidate frame setelah perubahan
            }
        });
    }
}
