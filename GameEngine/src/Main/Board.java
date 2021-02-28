package Main;
import GameLogic.Tuple;

import java.util.ArrayList;

public class Board {

    static private int col;
    static private int row;
    String board[][];
    ArrayList<String> tiles;

    Board(ArrayList<String> tileTypes, int row, int col) {
        this.col = col;
        this.row = row;
        board = new String[row][col];
        tiles = tileTypes;
        populateBoard();
    }

    public String[][] getBoard() {
        //I made this change because with the original code, it will constantly return a new board with different values
        //original code --> return board;
        String[][] newBoard = new String[this.row][this.col];
    	for(int i = 0; i < this.row; i++) {
    		for(int j = 0; j < this.col; j++) {
    			newBoard[i][j] = this.board[i][j];
    		}
    	}
        return newBoard;
    }

    public void setBoard(String[][] newBoard) { this.board = newBoard; }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void updateBoard(ArrayList<Tuple> oldTiles) {
        //replace all tiles with " "
        for (int i = 0; i < oldTiles.size(); i++) {
            board[oldTiles.get(i).row][oldTiles.get(i).col] = " ";
        }
        //move everything down
        moveColumnsDown();
        addRandomTiles();
    }

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

    private void addRandomTiles() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j].equals(" ")) {
                    int index = (int)(Math.random() *(tiles.size()-1));
                    board[i][j] = tiles.get(index);
                }
            }
        }
    }


    public void populateBoard() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int index = (int)(Math.random() *(tiles.size()-1));
                board[i][j] = tiles.get(index);
            }
        }
    }

    public void print() {
        //top of board
        System.out.print("-");
        for(int i = 0; i < col; i++) {
            System.out.print("---");
        }
        System.out.println();
        //board contents and side boundaries
        for(int i = 0; i < row; i++) {
            System.out.print("|");
            for (int j = 0; j < col; j++) {
                //return String.format("%1$" + length + "s", inputString).replace(' ', '0');
                System.out.print(" " + board[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        //bottom boundary of board
        System.out.print("-");
        for(int i = 0; i < col; i++) {
            System.out.print("---");
        }
        System.out.println();
    }



}
