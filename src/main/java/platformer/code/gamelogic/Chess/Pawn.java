//Miguel M
// Pawn, moves forward one square and captures diagonally forward. Can move forward 2 spaces if it's the pawn's first move.
package platformer.code.gamelogic.Chess;

import java.util.ArrayList;

//you will need to implement two functions in this file.

public class Pawn extends Piece{


    public Pawn(boolean isWhite, String img_file) {
            super(isWhite, img_file);
    }

    @Override
	public String toString() {

		return "A " + super.toString() + " pawn";
	}

    // precon: a valid double square array, a valid square on the board
    // poscon: all the squares that the pawn controls on the board, as of the square
    // given
    @Override
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlledSquares = new ArrayList<Square>();
        if (start == null || board == null) {
            return controlledSquares;
        }
        boolean iswhite = this.getColor();
        int row = start.getRow();
        int col = start.getCol();
        if (!iswhite) {
            if ((row - 1 >= 0) && (col - 1 >= 0)) {
                controlledSquares.add(board[row - 1][col - 1]);
                System.out.println("check wcl");
            }
            if ((row - 1 >= 0) && (col + 1 < 8)) {
                controlledSquares.add(board[row - 1][col + 1]);
                System.out.println("check wcr");
            }
        } else {
            if ((row + 1 < 8) && (col - 1 >= 0)) {
                controlledSquares.add(board[row + 1][col - 1]);
                System.out.println("check bcl");
            }
            if ((row + 1 < 8) && (col + 1 < 8)) {
                controlledSquares.add(board[row + 1][col + 1]);
                System.out.println("check bcr");
            }
        }

        
        return controlledSquares;
    }

    // RULES:
    // The pawn can move one piece forward at a time
    // If it is the pawns first move, it can move 2 spaces forward
    // If a piece is one space diagonally forward left or right to the pawn,
    // then it can move to that space and capture it if there is a pawn of the
    // opposite color there.

    // precon: a valid 8x8 chess board, and a valid square on that chess board
    // poscon: returns all the legal moves for the pawn on that square
    @Override
    public ArrayList<Square> getLegalMoves(Board b, Square start) {
        ArrayList<Square> moves = new ArrayList<Square>();
        boolean iswhite = this.getColor();
        int sr = start.getRow();
        int sc = start.getCol();
        System.out.println("checked square ["+sr+","+sc+"]" );
        // white
        if (!iswhite) {
            // 1 move forward
            {
                if (sr - 1 >= 0
                        &&
                        !(b.getSquareArray()[sr - 1][sc].isOccupied())) {
                    moves.add(b.getSquareArray()[sr - 1][sc]);
                    System.out.println("w1 check");
                }
            }
            // 2 move forward
            {
                if (sr == 6
                        &&
                        !(b.getSquareArray()[sr - 1][sc].isOccupied())
                        &&
                        !(b.getSquareArray()[sr - 2][sc].isOccupied())) {
                    moves.add(b.getSquareArray()[sr - 2][sc]);
                    System.out.println("w2 check");
                }
            }
            // captures
            {
                // left capture
                if ((sr - 1 >= 0) && (sc - 1 >= 0)
                        &&
                        b.getSquareArray()[sr - 1][sc - 1].isOccupied()
                        &&
                        b.getSquareArray()[sr - 1][sc - 1].getOccupyingPiece().getColor() != iswhite) {
                    moves.add(b.getSquareArray()[sr - 1][sc - 1]);
                    System.out.println("wcl check");
                }
                // right capture
                if ((sr - 1 >= 0) && (sc + 1 < 8)
                        &&
                        b.getSquareArray()[sr - 1][sc + 1].isOccupied()
                        &&
                        b.getSquareArray()[sr - 1][sc + 1].getOccupyingPiece().getColor() != iswhite) {
                    moves.add(b.getSquareArray()[sr - 1][sc + 1]);
                    System.out.println("wcr check");
                }
            }

        }
        // black
        else {
            // 1 move forward
            {
                if (sr + 1 < 8
                        &&
                        !(b.getSquareArray()[sr + 1][sc].isOccupied())) {
                    moves.add(b.getSquareArray()[sr + 1][sc]);
                    System.out.println("b1 check");
                }
            }
            // 2 move forward
            {
                if (sr == 1
                        &&
                        !(b.getSquareArray()[sr + 1][sc].isOccupied())
                        &&
                        !(b.getSquareArray()[sr + 2][sc].isOccupied())) {
                    moves.add(b.getSquareArray()[sr + 2][sc]);
                    System.out.println("b2 check");
                }
            }
            // captures
            {
                // left capture
                if ((sr + 1 < 8) && (sc - 1 >= 0)
                        &&
                        b.getSquareArray()[sr + 1][sc - 1].isOccupied()
                        &&
                        b.getSquareArray()[sr + 1][sc - 1].getOccupyingPiece().getColor() != iswhite) {
                    moves.add(b.getSquareArray()[sr + 1][sc - 1]);
                    System.out.println("bcl check");
                }
                // right capture
                if ((sr + 1 < 8) && (sc + 1 < 8)
                        &&
                        b.getSquareArray()[sr + 1][sc + 1].isOccupied()
                        &&
                        b.getSquareArray()[sr + 1][sc + 1].getOccupyingPiece().getColor() != iswhite) {
                    moves.add(b.getSquareArray()[sr + 1][sc + 1]);
                    System.out.println("bcr check");
                }
            }

        }
        return moves;
    }
}
