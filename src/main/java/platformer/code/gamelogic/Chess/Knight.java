package platformer.code.gamelogic.Chess;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

// Joe Jiao
// Knight
/* The knight moves in the manner of an "L" shape, moving 
one square on one axis and two squares on the other axis.
The knight can jump over pieces of either color. However,
the knight is still restricted to capturing pieces of only 
the opposing king's color. */
public class Knight extends Piece {
    public Knight(boolean isWhite, String img_file) {
        super(isWhite, img_file);
    }

    // Pre-condition: "board" is a 2D Square array representing the board and
    // "start" is a Square representing the square the user's selected piece is on
    // Post-condition: An ArrayList of Squares is returned composed of squares that
    // the piece can legally moved to (REGARDLESS OF IF THE SQUARES ARE OCCUPIED BY
    // PIECES OF THE SAME COLOR).
    @Override public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        int x = start.getCol();
        int y = start.getRow();

        // List of controlled squares that shall be returned
        ArrayList<Square> controlledSquares = new ArrayList<Square>();

        int[] signs = { 1, -1 }; // This is used later in for each loops to make it so the knight moves in
                                 // positive and negative directions both horizontally and vertically
        int[][] pairs = { { 1, 2 }, { 2, 1 } }; // Knights move in an "L-shape" patter with either moves that are 2 up
                                                // and 1 to the side or 2 to the side and 1 up. This is used in the for
                                                // each loop to make my code more unnecessarily compact

        // Copious amounts of for each loops to loop through all possible moves of the
        // knight
        for (int sign1 : signs) {
            for (int sign2 : signs) {
                for (int[] pair : pairs) {
                    // Records the row and column of the move
                    int newX = x + sign2 * pair[1];
                    int newY = y + sign1 * pair[0];

                    if (newX >= 0 && newX <= 7 && newY >= 0 && newY <= 7) { // Makes sure the location is within the
                                                                            // bounds of the board
                        controlledSquares.add(board[newY][newX]); // Adds valid square to the return array
                    }
                }
            }
        }

        return controlledSquares;
    }

    // Pre-condition: "b" is a board representing the board and "start" is a Square
    // representing the square the user's selected piece is on
    // Post-condition: An ArrayList of Squares is returned composed of squares that
    // the piece can legally moved to (standard chess rules; the move is within the
    // board and the piece is not capturing those of its own color)
    @Override public ArrayList<Square> getLegalMoves(Board b, Square start) {
        // Steals list of squares that are on the board that the knight can move to from
        // the previous method
        ArrayList<Square> controlled = getControlledSquares(b.getSquareArray(), start);

        // Return list of valid moves the knight can make
        ArrayList<Square> valid = new ArrayList<Square>();

        // Iterates through array of the squares
        for (int i = 0; i < controlled.size(); i++) {
            Square s = controlled.get(i);

            if ((!s.isOccupied()) || (s.getOccupyingPiece().getColor() != this.getColor())) { // Makes sure that the square
                                                                                              // does not contain a piece of
                                                                                              // the same color as the knight
                // Adds the square to the return list if valid
                valid.add(s);
            }
        }

        return valid;
    }

    @Override public String toString() {
        String placeholder = "This is a ";
        if (this.getColor()) placeholder += "white knight";
        else placeholder += "black knight";

        return placeholder;
    }
}