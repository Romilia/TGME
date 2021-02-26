package Main;
import GameLogic.Tuple;

import java.util.ArrayList;

public class Board {

    static private int col;
    static private int row;
    String board[][];
    ArrayList<String> tiles;

    Board(ArrayList<String> tileTypes, int col, int row) {
        this.col = col;
        this.row = row;
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

    public void updateBoard(ArrayList<Tuple> oldTiles) {
        //TODO: remove matches and add new tiles, check for new matches
        //replace all tiles with " "
        for (int i = 0; i < oldTiles.size(); i++) {
            board[oldTiles.get(i).row][oldTiles.get(i).col] = " ";
        }
        //move everything down
        moveColumnsDown();

        //find matches
        //String match = findMatch();
//        if(match != "") {
//
//        }
    }
//    for(int i = row-1; i >= 0; i--) {
//        for(int j = 0; j < col; j++) {
//            if (board[i][j].equals(" ") && i != 0) {
//                for (int k = i; k > 0; k--) {
//                    board[k][j] = board[k - 1][j];
//                    if (k == 1) {
//                        board[k-1][j] = " ";
//                    }
//                }
//
//            }
//        }
//    }

    private void moveColumnsDown() {
        for (int j = 0; j < col; j++) {
            for (int i = row-1; i >= 0; i++) {
                if (board[i][j].equals(" ")) {
                    int ct = countSpaces(i, j);
                    for (int k = i; k > 0; k--) {
                        if ((k-ct) >= 0) {
                            board[k][j] = board[k - ct][j];
                        }
                        if (k == 1) {
                            board[0][j] = " ";
                        }
                    }
                }
            }
        }
    }

    private int countSpaces(int row, int col) {
        int reCount = 1;
        for (int i = row-1; i >= 0; i++) {
            if (board[i][col].equals(" ")) {
                reCount += 1;
            }
        }
        return reCount;
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
