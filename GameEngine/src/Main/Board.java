package Main;
import java.util.ArrayList;

public class Board {

    static private int col;
    static private int row;
    String board[][];
    ArrayList<String> tiles;

    Board(ArrayList<String> tileTypes, int col, int row) {
        board = new String[row][col];
        tiles = tileTypes;
        populateBoard();
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

    public void populateBoard() {
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                int index = (int)(Math.random() *(tiles.size()-1));
                board[i][j] = tiles.get(index);
            }
        }
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
