package connectFour;

import java.awt.*;

import javax.swing.ImageIcon;

public class Board {
    // Define named constants
    public static final int ROWS = 6;  // ROWS x COLS cells
    public static final int COLS = 7;
    public static final int CANVAS_WIDTH = Cell.SIZE * COLS; // the drawing canvas
    public static final int CANVAS_HEIGHT = Cell.SIZE * ROWS;
    public static final int GRID_WIDTH = 8; // Grid-line's width
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2; // Grid-line's half-width
    public static final Color COLOR_GRID = new Color(220, 193, 190);  // grid lines
    private Image backgroundImage;

    Cell[][] cells; // Composes of 2D array of ROWS-by-COLS Cell instances

    

}