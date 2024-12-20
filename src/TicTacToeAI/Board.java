/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #8
 * 1 - 5026231023 - Nadya Luthfiyah Rahma
 * 2 - 5026231094 - Davina Almeira
 * 3 - 5026231148 - Tiara Aulia Azadirachta Indica
 */

package TicTacToeAI;

import java.awt.*;

import javax.swing.ImageIcon;

public class Board {
// Define named constants
public static final int ROWS = 3;  // ROWS x COLS cells
public static final int COLS = 3;
public static final int CANVAS_WIDTH = Cell.SIZE * COLS; // the drawing canvas
public static final int CANVAS_HEIGHT = Cell.SIZE * ROWS;
public static final int GRID_WIDTH = 8; // Grid-line's width
public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2; // Grid-line's half-width
public static final Color COLOR_GRID = new Color(220, 193, 190);  // grid lines
private Image backgroundImage;

    Cell[][] cells; // Composes of 2D array of ROWS-by-COLS Cell instances

    public Board() {
        initGame();
        loadImage(); //load background pada board
    }

    public void initGame() {
        cells = new Cell[ROWS][COLS];
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    public void newGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].content = Seed.NO_SEED;
            }
        }
    }

    public State stepGame(Seed player, int selectedRow, int selectedCol) {
        cells[selectedRow][selectedCol].content = player;

        if (hasWon(player, selectedRow, selectedCol)) {
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        }

        // Check for DRAW
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (cells[row][col].content == Seed.NO_SEED) {
                    return State.PLAYING;
                }
            }
        }
        return State.DRAW;
    }

    public boolean hasWon(Seed theSeed, int rowSelected, int colSelected) {
        // Check the row
        int count = 0;
        for (int col = 0; col < COLS; ++col) {
            if (cells[rowSelected][col].content == theSeed) {
                ++count;
                if (count == 3) return true; //kalau menghitung 4 berturut2 berarti menang
            } else {
                count = 0;
            }
        }

        // Check the column
        count = 0;
        for (int row = 0; row < ROWS; ++row) {
            if (cells[row][colSelected].content == theSeed) {
                ++count;
                if (count == 3) return true;
            } else {
                count = 0;
            }
        }

        //check 4 line diagonal 
        count = 0;
        for (int row = 0; row < ROWS; ++row) {
            if (cells[row][row].content == theSeed) {
                ++count;
                if (count == 3) return true;
            } else {
                count = 0;
            }
        }

        // Check the main diagonal (↘)
        count = 0;
        for (int offset = -3; offset <= 3; ++offset) {
            int row = rowSelected + offset;
            int col = colSelected + offset;
            if (row >= 0 && row < ROWS && col >= 0 && col < COLS &&
                cells[row][col].content == theSeed) {
                ++count;
                if (count == 3) return true;
            } else {
                count = 0;
            }
        }

        // Check the secondary diagonal (↙)
        count = 0;
        for (int offset = -3; offset <= 3; ++offset) {
            int row = rowSelected + offset;
            int col = colSelected - offset;
            if (row >= 0 && row < ROWS && col >= 0 && col < COLS &&
                cells[row][col].content == theSeed) {
                ++count;
                if (count == 3) return true;
            } else {
                count = 0;
            }
        }

        return false;  // No 3-in-a-line found
    }
    public void loadImage(){
        ImageIcon icon = new ImageIcon("src/background.jpg");
        backgroundImage = icon.getImage();
    }

    public void paint(Graphics g) {
        // Background
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, CANVAS_WIDTH, CANVAS_HEIGHT, null);
        }

        g.setColor(COLOR_GRID);
        for (int row = 1; row < ROWS; ++row) {
            g.fillRoundRect(0, Cell.SIZE * row - GRID_WIDTH_HALF,
                    CANVAS_WIDTH - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
        }
        for (int col = 1; col < COLS; ++col) {
            g.fillRoundRect(Cell.SIZE * col - GRID_WIDTH_HALF, 0,
                    GRID_WIDTH, CANVAS_HEIGHT - 1, GRID_WIDTH, GRID_WIDTH);
        }

        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].paint(g);
            }
        }
    }
    //AI Turn
    public int[] getBestMove(Seed aiSeed, Seed opponentSeed) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (cells[row][col].content == Seed.NO_SEED) {
                    // Simulasi langkah AI
                    cells[row][col].content = aiSeed;
                    int score = minimax(aiSeed, opponentSeed, false);
                    cells[row][col].content = Seed.NO_SEED; // Undo langkah

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{row, col};
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(Seed aiSeed, Seed opponentSeed, boolean isMaximizing) {
        State currentState = evaluateGame(aiSeed, opponentSeed);
        if (currentState == State.CROSS_WON) return (aiSeed == Seed.CROSS) ? 10 : -10;
        if (currentState == State.NOUGHT_WON) return (aiSeed == Seed.NOUGHT) ? 10 : -10;
        if (currentState == State.DRAW) return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (cells[row][col].content == Seed.NO_SEED) {
                    cells[row][col].content = isMaximizing ? aiSeed : opponentSeed;
                    int score = minimax(aiSeed, opponentSeed, !isMaximizing);
                    cells[row][col].content = Seed.NO_SEED; // Undo langkah
                    bestScore = isMaximizing
                            ? Math.max(score, bestScore)
                            : Math.min(score, bestScore);
                }
            }
        }
        return bestScore;
    }

    public Cell[][] getCells() {
        return cells;
    }


    private State evaluateGame(Seed aiSeed, Seed opponentSeed) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (cells[row][col].content != Seed.NO_SEED &&
                        hasWon(cells[row][col].content, row, col)) {
                    return (cells[row][col].content == aiSeed) ? State.CROSS_WON : State.NOUGHT_WON;
                }
            }
        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (cells[row][col].content == Seed.NO_SEED) {
                    return State.PLAYING;
                }
            }
        }
        return State.DRAW;
    }
}