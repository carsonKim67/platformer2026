//Verona M Gerolmini Bianchimano

//Queen piece
//The piece goes straight and diagonal in any direction.

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
public class Queen extends Piece {
    
    
    
    public Queen(boolean isWhite, String img_file) {
        super(isWhite, img_file);
    }
    
    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.


    // Returns a list of all squares that this piece controls (can capture into)

    // Precondition: start is a square on the board containing this piece.
    //               board is a valid 8x8 array representing the current board state.
    // Postcondition: Returns an ArrayList of Squares that this piece controls,
    //             including squares occupied by friendly pieces and stopping
    //             at the first piece encountered in each direction.
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        
        ArrayList<Square> controlled = new ArrayList<>();

        // up
        int row = start.getRow() - 1;
        int col = start.getCol();
        while(row >= 0){
            controlled.add(board[row][col]);
            if(board[row][col].isOccupied()) break;
            row--;
        }

        // down
        row = start.getRow() + 1;
        while(row < 8){
            controlled.add(board[row][col]);
            if(board[row][col].isOccupied()) break;
            row++;
        }

        // right
        row = start.getRow();
        col = start.getCol() + 1;
        while(col < 8){
            controlled.add(board[row][col]);
            if(board[row][col].isOccupied()) break;
            col++;
        }

        // left
        col = start.getCol() - 1;
        while(col >= 0){
            controlled.add(board[row][col]);
            if(board[row][col].isOccupied()) break;
            col--;
        }

        // up-left
        row = start.getRow() - 1;
        col = start.getCol() - 1;
        while(row >= 0 && col >= 0){
            controlled.add(board[row][col]);
            if(board[row][col].isOccupied()) break;
            row--; col--;
        }

        // up-right
        row = start.getRow() - 1;
        col = start.getCol() + 1;
        while(row >= 0 && col < 8){
            controlled.add(board[row][col]);
            if(board[row][col].isOccupied()) break;
            row--; col++;
        }

        // down-left
        row = start.getRow() + 1;
        col = start.getCol() - 1;
        while(row < 8 && col >= 0){
            controlled.add(board[row][col]);
            if(board[row][col].isOccupied()) break;
            row++; col--;
        }

        // down-right
        row = start.getRow() + 1;
        col = start.getCol() + 1;
        while(row < 8 && col < 8){
            controlled.add(board[row][col]);
            if(board[row][col].isOccupied()) break;
            row++; col++;
        }

        return controlled;
    }
    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.
    

    
    // Returns a list of all legal moves for this piece from the starting square.
 
    // Precondition: start is a square on the board containing this piece.
    // Postcondition: Returns an ArrayList of Squares that this piece can legally move to,
    //               according to chess rules and considering other pieces on the board.
    public ArrayList<Square> getLegalMoves(Board b, Square start){

        ArrayList<Square> moves = new ArrayList<>();

        //try to go up
        int row = start.getRow()-1;
        while(row >=0){
            if(!b.getSquareArray()[row][start.getCol()].isOccupied()){
                moves.add(b.getSquareArray()[row][start.getCol()]);
            } else {
                if(b.getSquareArray()[row][start.getCol()].getOccupyingPiece().getColor()!= color){
                    moves.add(b.getSquareArray()[row][start.getCol()]);
                }
                break;
            }
            row--;
        }

        // try to go down
        row = start.getRow() +1;
        while(row < 8){
            if(!b.getSquareArray()[row][start.getCol()].isOccupied()){
                moves.add(b.getSquareArray()[row][start.getCol()]);
            } else {
                if(b.getSquareArray()[row][start.getCol()].getOccupyingPiece().getColor()!= color){
                    moves.add(b.getSquareArray()[row][start.getCol()]);
                }
                break;
            }
            row++;
        }

        // try to go right
        int col = start.getCol() +1;
        while(col < 8){
            if(!b.getSquareArray()[start.getRow()][col].isOccupied()){
                moves.add(b.getSquareArray()[start.getRow()][col]);
            } else {
                if(b.getSquareArray()[start.getRow()][col].getOccupyingPiece().getColor()!= color){
                    moves.add(b.getSquareArray()[start.getRow()][col]);
                }
                break;
            }
            col++;
        }

        // try to go left
        col = start.getCol() -1;
        while(col >= 0){
            if(!b.getSquareArray()[start.getRow()][col].isOccupied()){
                moves.add(b.getSquareArray()[start.getRow()][col]);
            } else {
                if(b.getSquareArray()[start.getRow()][col].getOccupyingPiece().getColor()!= color){
                    moves.add(b.getSquareArray()[start.getRow()][col]);
                }
                break;
            }
            col--;
        }

        // try to go up-left
        col = start.getCol() -1;
        row = start.getRow() -1;
        while(row >= 0 && col >= 0){
            if(!b.getSquareArray()[row][col].isOccupied()){
                moves.add(b.getSquareArray()[row][col]);
            } else {
                if(b.getSquareArray()[row][col].getOccupyingPiece().getColor()!= color){
                    moves.add(b.getSquareArray()[row][col]);
                }
                break;
            }
            col--;
            row--;
        }

        // try to go down-right
        col = start.getCol() +1;
        row = start.getRow() +1;
        while(row < 8 && col < 8){
            if(!b.getSquareArray()[row][col].isOccupied()){
                moves.add(b.getSquareArray()[row][col]);
            } else {
                if(b.getSquareArray()[row][col].getOccupyingPiece().getColor()!= color){
                    moves.add(b.getSquareArray()[row][col]);
                }
                break;
            }
            col++;
            row++;
        }

        // try to go up-right
        col = start.getCol() +1;
        row = start.getRow() -1;
        while(row >= 0 && col < 8){
            if(!b.getSquareArray()[row][col].isOccupied()){
                moves.add(b.getSquareArray()[row][col]);
            } else {
                if(b.getSquareArray()[row][col].getOccupyingPiece().getColor()!= color){
                    moves.add(b.getSquareArray()[row][col]);
                }
                break;
            }
            col++;
            row--;
        }

        // try to go down-reft
        col = start.getCol() -1;
        row = start.getRow() +1;
        while(row < 8 && col >= 0){
            if(!b.getSquareArray()[row][col].isOccupied()){
                moves.add(b.getSquareArray()[row][col]);
            } else {
                if(b.getSquareArray()[row][col].getOccupyingPiece().getColor()!= color){
                    moves.add(b.getSquareArray()[row][col]);
                }
                break;
            }
            col--;
            row++;
        }
            
        return moves;
    }

}