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
        String oldVal = board[targetRow][targetCol];
        board[targetRow][targetCol] = board[selectedRow][selectedCol];
        board[selectedRow][selectedCol] = oldVal;
        //find matches
        String match = findMatch(selectedRow, selectedCol);
        if(match != "") {

        }
    }

    private String findMatch(int selectedRow, int selectedCol) {
        //left
        if ((selectedCol-1) >= 0 && (selectedCol-2) >= 0) {
            if (board[selectedRow][selectedCol] == board[selectedRow][selectedCol-1]
                    && board[selectedRow][selectedCol] == board[selectedRow][selectedCol-2]) {
                return "left";
            }
        }
        //right
        else if ((selectedCol+1)<= (col-1) && (selectedCol+2) <= (col-1)) {
            if (board[selectedRow][selectedCol] == board[selectedRow][selectedCol+1]
                    && board[selectedRow][selectedCol] == board[selectedRow][selectedCol+2]) {
                return "right";
            }
        }
        //up
        else if ((selectedRow-1) >= 0 && (selectedRow-2) >= 0) {
            if (board[selectedRow][selectedCol] == board[selectedRow-1][selectedCol]
              && board[selectedRow][selectedCol] == board[selectedRow-2][selectedCol]) {
                return "up";
            }
        }
        //down
        else if ((selectedRow+1) <= (row-1) && (selectedRow+2) <= (row-1)) {
            if (board[selectedRow][selectedCol] == board[selectedRow+1][selectedCol]
                    && board[selectedRow][selectedCol] == board[selectedRow+2][selectedCol]) {
                return "down";
            }
        }
        return "";
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
