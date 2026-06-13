//Name: Jian Acol
//PD: 7
//Description: This is a rook, which moves all the way up and down, and left and right.

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
public class Rook extends Piece {
    
    public Rook(boolean isWhite, String img_file) {  
        super(isWhite, img_file);
    }
    
    public String toString() {
        if (getColor())
            return "I am the rook, and I am white";
        else
            return "I am the rook, and I am black";
    }

    

    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.

    //precondition: the board already has to be initialized
    //postcondition: returns the arraylist called "controlled" where it shows every legal capture
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlled = new ArrayList<>();

        //right
        for (int col = start.getCol() + 1; col <= 7; col++){
            Square right = board[start.getRow()][col];
            controlled.add(right);
            if(right.isOccupied()){
                break;
            }
        }

        //left
        for (int col = start.getCol() - 1; col >= 0; col--){
            Square left = board[start.getRow()][col];
            controlled.add(left);
            if(left.isOccupied()){
                break;
            }
        }

        //up
        for (int row = start.getRow() + 1; row <= 7; row++){
            Square up = board[row][start.getCol()];
            controlled.add(up);
            if(up.isOccupied()){
                break;
            }
        }

        //down
        for (int row = start.getRow() - 1; row >= 0; row--){
            Square down = board[row][start.getCol()];
            controlled.add(down);
            if(down.isOccupied()){
                break;
            }
        }

        return controlled;
    }
    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.

    //precondition: must have a board object and that board must be initialized
    //postcondition: returns an arraylist called "moves" where it lists all the empty squares the rook can move into
    public ArrayList<Square> getLegalMoves(Board b, Square start){
        // b.getSquareArray() //gives the 2d board we made earlier
        // you are at coordinate start.getRow() start.getCol()
        // so check to see if your square is on the board and is not occupied by another piece. 
        ArrayList<Square> moves = new ArrayList<>();

        //loop for each direction we want to go in
        //right
        for(int col = start.getCol()+1; col<=7; col++){
            Square right = b.getSquareArray()[start.getRow()][col];
            if(!right.isOccupied() || right.getOccupyingPiece().getColor()!= getColor()){
                
                moves.add(right);
                if(right.isOccupied()){
                    break;
                }
            }
            else{
                break;
            }
        }
        
        //left
        for(int col = start.getCol()-1; col>=0; col--){
            Square left = b.getSquareArray()[start.getRow()][col];
            if(!left.isOccupied() || left.getOccupyingPiece().getColor()!= getColor()){
                moves.add(left);
                if(left.isOccupied()){
                    break;
                }
            }
            else{
                break;
            }
        }

        //top
        for(int row = start.getRow()+1; row<=7; row++){
            Square up = b.getSquareArray()[row][start.getCol()];
            if(!up.isOccupied() || up.getOccupyingPiece().getColor()!= getColor()){
                moves.add(up);
                if(up.isOccupied()){
                    break;
                }
            }
            else{
                break;
            }
        }

        for(int row = start.getRow()-1; row>=0; row--){
            Square down = b.getSquareArray()[row][start.getCol()];
            if(!down.isOccupied() || down.getOccupyingPiece().getColor()!= getColor()){
                moves.add(down);
                if(down.isOccupied()){
                    break;
                }
            }
            else{
                break;
            }
        }

    	return moves;
    }
}