package Main;
import java.util.ArrayList;

public class Board {

    int col;
    int row;
    String board[][];
    ArrayList<String> tiles;

    Board(ArrayList<String> tileTypes, int col, int row) {
        board = new String[row][col];
        tiles = tileTypes;
    }

    public String[][] getBoard() {
        return board;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void updateBoard(int selectedRow, int selectedCol, int targetRow, int targetCol) {
        //TODO: remove matches and add new tiles, check for new matches
    }

    public void print() {
        //top of board
        for(int i = 0; i < row; i++) {
            System.out.print("---");
        }
        System.out.println();
        //board contents and side boundaries
        for(int i = 0; i < row; i++) {
            System.out.print("|");
            for(int j = 0; j < col; j++) {
                System.out.print(" "+board[i][j]+" ");
            }
            System.out.print("|");
        }
        System.out.println();
        //bottom boundary of board
        for(int i = 0; i < row; i++) {
            System.out.print("---");
        }
    }



}
