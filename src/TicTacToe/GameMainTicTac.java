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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Tic-Tac-Toe: Two-player Graphic version with better OO design.
 * The Board and Cell classes are separated in their own classes.
 */
public class GameMainTicTac extends JPanel {
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
   String player2Name;

   /** Constructor to setup the UI and game components */
   public GameMainTicTac() {

      player1Name = JOptionPane.showInputDialog(this, "Enter Player 1's Name:");
        player2Name = JOptionPane.showInputDialog(this, "Enter Player 2's Name:");
        if (player1Name == null || player1Name.trim().isEmpty()) {
            player1Name = "Cat";
        }
        if (player2Name == null || player2Name.trim().isEmpty()) {
            player2Name = "Dog";
        }

      // This JPanel fires MouseEvent
      super.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
            int mouseX = e.getX();
            int mouseY = e.getY();
            // Get the row and column clicked
            int row = mouseY / Cell.SIZE;
            int col = mouseX / Cell.SIZE;

            if (currentState == State.PLAYING) {
               if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                     && board.cells[row][col].content == Seed.NO_SEED) {
                  // Update cells[][] and return the new game state after the move
                  currentState = board.stepGame(currentPlayer, row, col);
                  // Play appropriate sound clip
                  if (currentState == State.CROSS_WON || currentState == State.NOUGHT_WON) {
                     SoundEffect.WIN.play(); // Play win sound
                     } else if (currentState == State.DRAW) {
                           SoundEffect.DRAW.play();
                     } else if (currentState == State.PLAYING) {
                              if (currentPlayer == Seed.CROSS) {
                                 SoundEffect.player1Name.play(); 
                              } else {
                                 SoundEffect.player2Name.play();
                              }
                           }
                  // Switch player
                  currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
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
         statusBar.setText((currentPlayer == Seed.CROSS) ? player1Name + "'s Turn" : player2Name + "'s Turn");
      } else if (currentState == State.DRAW) {
         statusBar.setForeground(Color.RED);
         statusBar.setText("It's a Draw! Click to play again.");
      } else if (currentState == State.CROSS_WON) {
         statusBar.setForeground(Color.RED);
         statusBar.setText(player1Name + "'s Won! Click to play again.");
      } else if (currentState == State.NOUGHT_WON) {
         statusBar.setForeground(Color.RED);
         statusBar.setText(player2Name + "'s Won! Click to play again.");
      }
   }

   /** The entry "main" method */
   public static void main(String[] args) {
      // Run GUI construction codes in Event-Dispatching thread for thread safety
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            JFrame frame = new JFrame(TITLE);
            // Set the content-pane of the JFrame to an instance of main JPanel
            frame.setContentPane(new GameMainTicTac());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); // center the application window
            frame.setVisible(true);            // show it
         }
      });
   }
}