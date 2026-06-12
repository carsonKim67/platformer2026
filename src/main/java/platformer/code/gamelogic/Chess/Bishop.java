//Carson Kim

//Bishop
//Moves diagonally in both directions.

package platformer.code.gamelogic.Chess;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Bishop extends Piece {

    public Bishop(boolean isWhite, String img_file) {
        super(isWhite, img_file);
    }

    //pre con - toString is a method already made
    //post con - tells that the piece is a bishop
    @Override
    public String toString() {
        //gets from piece class
        return super.toString() + " Bishop";
    }

    // TO BE IMPLEMENTED!
    // return a list of every square that is "controlled" by this piece. A square is
    // controlled
    // if the piece capture into it legally.
    // pre con - the piece is on the square start, and the board is in some
    // configuration
    // post con - returns an arraylist of squares which this piece could legally
    // move to
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlledSquares = new ArrayList<>();

        // Bishop controls all diagonals until blocked
        int startRow = start.getRow();
        int startCol = start.getCol();
        int[][] directions = { { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };

        for (int[] dir : directions) {
            int r = startRow + dir[0];
            int c = startCol + dir[1];

            // Continue in this direction until hit a boundary or piece
            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                Square target = board[r][c];

                if (target.getOccupyingPiece() == null) {
                    // Empty square, controls it
                    controlledSquares.add(target);
                } else if (target.getOccupyingPiece().getColor() != this.color) {
                    // Enemy piece, controls it but can't go further
                    controlledSquares.add(target);
                    break;
                } else {
                    // Friendly piece, blocked
                    break;
                }

                // moves diagonally in the direction of dir
                r += dir[0];
                c += dir[1];
            }
        }

        return controlledSquares;
    }

    // bishop is allowed to move diagonally in all four directions until it hits a
    // piece or the edge of the board. It can capture an enemy piece by moving onto
    // its square, but cannot move onto a square occupied by a friendly piece.
    // pre con - the piece is on the square start, and the board is in some
    // configuration
    // post con - returns an arraylist of squares which this piece could legally
    // move to
    public ArrayList<Square> getLegalMoves(Board b, Square start) {
        /// can accesss board b.getSquareArray()
        ArrayList<Square> legalMoves = new ArrayList<>();
        Square[][] boardArray = b.getSquareArray();

        // Bishop moves diagonally in all four directions using row/col
        int startRow = start.getRow();
        int startCol = start.getCol();
        int[][] directions = { { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };

        for (int[] dir : directions) {
            int r = startRow + dir[0];
            int c = startCol + dir[1];

            // Continue in this direction until hit a boundary or piece
            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                Square target = boardArray[r][c];

                if (target.getOccupyingPiece() == null) {
                    // Empty square, can move here
                    legalMoves.add(target);
                } else if (target.getOccupyingPiece().getColor() != this.color) {
                    // Enemy piece, can capture
                    legalMoves.add(target);
                    break;
                } else {
                    // Friendly piece, blocked
                    break;
                }

                r += dir[0];
                c += dir[1];
            }
        }

        return legalMoves;
    }
}