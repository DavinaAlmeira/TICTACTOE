package TicTacToeAI2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Tic-Tac-Toe: Two-player Graphic version with better OO design.
 * The Board and Cell classes are separated in their own classes.
 */
public class GameMainAI extends JPanel {
    private static final long serialVersionUID = 1L; // to prevent serializable warning

    // Define named constants for the drawing graphics
    public static final String TITLE = "Tic-Tac-toe";
    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_BG_STATUS = new Color(220, 193, 190);
    // public static final Color COLOR_CROSS = new Color(255, 182, 193);  // light pink tidak terpakai
    // public static final Color COLOR_NOUGHT = new Color(173, 216, 230); // light blue tidak terpakai
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    // Define game objects
    private Board board;         // the game board
    private State currentState;  // the current state of the game
    private Seed currentPlayer;  // the current player
    private JLabel statusBar;    // for displaying status message

    String player1Name;
    AIPlayer aiPlayer;

    /** Constructor to setup the UI and game components */
    public GameMainAI() {

        // Inisialisasi game board dan status
        board = new Board();
        currentState = State.PLAYING;
        currentPlayer = Seed.CROSS;

        // Popup untuk nama Player 1
        player1Name = JOptionPane.showInputDialog(this, "Enter your name:");
        if (player1Name == null || player1Name.trim().isEmpty()) {
            player1Name = "Cat"; // Nama default jika tidak diisi
        }

        aiPlayer = new AIPlayerMinimax(board); // AI dengan Minimax
        aiPlayer.setSeed(Seed.NOUGHT); //
        aiPlayer.setSeed(Seed.NOUGHT); // AI menggunakan Seed NOUGHT (lingkaran)

        // This JPanel fires MouseEvent
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
                int mouseX = e.getX();
                int mouseY = e.getY();
                // Get the row and column clicked
                int row = mouseY / Cell.SIZE;
                int col = mouseX / Cell.SIZE;

                // Check if the clicked position is within the bounds of the board
                if (row < 0 || row >= Board.ROWS || col < 0 || col >= Board.COLS) return; // Ignore clicks outside the board

                if (currentState == State.PLAYING) {
                    if (board.cells[row][col].content == Seed.NO_SEED) {
                        // Update cells[][] and return the new game state after the move
                        currentState = board.stepGame(currentPlayer, row, col);
                        if (currentState == State.PLAYING) {
                            // Switch player
                            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;

                            // AI turn
                            if (currentPlayer == Seed.NOUGHT) {
                                new Thread(() -> {
                                    try {
                                        Thread.sleep(1000); // Delay 1 detik
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }

                                    SwingUtilities.invokeLater(() -> {
                                        int[] aiMove = aiPlayer.move();
                                        // Check if AI move is valid
                                        if (aiMove[0] >= 0 && aiMove[0] < Board.ROWS && aiMove[1] >= 0 && aiMove[1] < Board.COLS) {
                                            currentState = board.stepGame(currentPlayer, aiMove[0], aiMove[1]);
                                        } else {
                                            // AI move is invalid or null
                                            System.out.println("AI move is invalid. Skipping AI turn.");
                                        }

                                        if (currentState == State.PLAYING && currentPlayer == Seed.NOUGHT) {
                                            int[] bestMove = board.getBestMove(Seed.NOUGHT, Seed.CROSS);
                                            if (bestMove[0] != -1) {
                                                currentState = board.stepGame(Seed.NOUGHT, bestMove[0], bestMove[1]);
                                            }
                                        }

                                        // Switch back to player 1
                                        currentPlayer = Seed.CROSS;
                                        repaint(); // Refresh tampilan
                                    });
                                }).start();
                            }
                        }
                    }
                } else {        // game over
                    newGame();  // restart the game
                }
                // Refresh the drawing canvas
                repaint();  // Callback paintComponent().
            }
        });

        // Setup the status bar (JLabel) to display status message
        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(300, 30));
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        super.setLayout(new BorderLayout());
        super.add(statusBar, BorderLayout.PAGE_END); // same as SOUTH
        super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));
        // account for statusBar in height
        super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

        // Set up Game
        initGame();
        newGame();
    }

    /** Initialize the game (run once) */
    public void initGame() {
        board = new Board();  // allocate the game-board
    }

    /** Reset the game-board contents and the current-state, ready for new game */
    public void newGame() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED; // all cells empty
            }
        }
        currentPlayer = Seed.CROSS;    // cross plays first
        currentState = State.PLAYING;  // ready to play
    }

    /** Custom painting codes on this JPanel */
    @Override
    public void paintComponent(Graphics g) {  // Callback via repaint()
        super.paintComponent(g);
        setBackground(COLOR_BG); // set its background color

        board.paint(g);  // ask the game board to paint itself include background

        // Print status-bar message
        if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText((currentPlayer == Seed.CROSS) ? player1Name + "'s Turn" : "AI's Turn");
        } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == State.CROSS_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText(player1Name + "'s Won! Click to play again.");
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("AI's Won! Click to play again.");
        }
    }

    /** The entry "main" method */
    public static void main(String[] args) {
        // Run GUI construction codes in Event-Dispatching thread for thread safety
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(TITLE);
                // Set the content-pane of the JFrame to an instance of main JPanel
                frame.setContentPane(new GameMainAI());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null); // center the application window
                frame.setVisible(true);            // show it
            }
        });
    }
}