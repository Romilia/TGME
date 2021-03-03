package Main;

import GameLogic.Move;
import GameLogic.Tuple;

import java.util.ArrayList;
import java.util.HashSet;

public class Board {

    static private int col;
    static private int row;
    public String board[][];
    public ArrayList<String> tiles;

    Board(ArrayList<String> tileTypes, int row, int col) {
        this.col = col;
        this.row = row;
        board = new String[row][col];
        tiles = tileTypes;
        populateBoard();
    }

    public String[][] getBoard() {
        String[][] newBoard = new String[this.row][this.col];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                newBoard[i][j] = this.board[i][j];
            }
        }
        return newBoard;
    }

    public void setBoard(String[][] newBoard) {
        this.board = newBoard;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void updateBoard(HashSet<Tuple> oldTiles) {
        //replace all tiles with " "
        for (Tuple t: oldTiles) {
            board[t.row][t.col] = " ";
        }
//        System.out.println("Update Board");
//        this.print();
        //move everything down
        moveColumnsDown();
        addRandomTiles();
    }

    private void moveColumnsDown() {
        for(int col = 0; col < this.getCol(); col++)
        {
            ArrayList<String> values = new ArrayList<String>();
            for(int row = this.getRow()-1; row >= 0 ; row--)
            {
                //if value exist on board, meaning not empty space
                if(!this.board[row][col].equals(" "))
                {
                    //store in the value list
                    values.add(this.board[row][col]);
                }
            }

            //after all the values are added to the list
            //put it back to the board starting from bottom
            int listSize = values.size();
            int itemPosition = 0;
            if(listSize < getRow())
            {
                for(int row = this.getRow()-1; row >= 0; row--)
                {
                    if(itemPosition < listSize)
                    {
                        this.board[row][col] = values.get(itemPosition);
                        itemPosition += 1;
                    }
                    else
                    {
                        //set to empty space
                        this.board[row][col] = " ";
                    }
                }
            }
        }
//        for (int j = 0; j < col; j++) {
//            for (int i = row - 1; i >= 0; i--) {
//                if (board[i][j].equals(" ")) {
//                    int ct = countSpaces(i, j);
//                    for (int k = i; k > 0; k--) {
//                        if ((k - ct) >= 0) {
//                            board[k][j] = board[k - ct][j];
//                        }
//                        if (k == 1) {
//                            board[0][j] = " ";
//                        }
//                    }
//                }
//            }
//        }
////        this.print();
    }

    private int countSpaces(int r, int c) {
        int reCount = 1;
        for (int i = r - 1; i >= 0; i--) {
            if (board[i][c].equals(" ")) {
                reCount += 1;
            }
        }
        return reCount;
    }

    private void addRandomTiles() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j].equals(" ")) {
                    int index = (int) (Math.random() * (tiles.size() - 1));
                    board[i][j] = tiles.get(index);
                }
            }
        }
    }


    public void populateBoard() {
        //edited range
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int index = (int) (Math.random() * (tiles.size()));
                board[i][j] = tiles.get(index);
            }
        }
    }

    public void print() {
        //top of board
        System.out.print("  -");
        for (int i = 0; i < col; i++) {
            System.out.print("- " + i + " -");
        }
        System.out.print("-");
        System.out.println();
        //board contents and side boundaries
        for (int i = 0; i < row; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < col; j++) {
                if (board[i][j].contains("S")) {
                    System.out.print(" " + board[i][j] + "  ");
                }
                //return String.format("%1$" + length + "s", inputString).replace(' ', '0');
                else {
                    System.out.print("  " + board[i][j] + "  ");
                }
            }
            System.out.print("|");
            System.out.println();
        }
        //bottom boundary of board
        System.out.print("   ");
        for (int i = 0; i < col; i++) {
            System.out.print("-----");
        }
        System.out.println();
    }


}
