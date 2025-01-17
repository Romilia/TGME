package Main;

import GameLogic.Tuple;

import java.util.ArrayList;

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

    public void updateBoard(ArrayList<Tuple> oldTiles) {
        //replace all tiles with " "
        for (Tuple t: oldTiles) {
            board[t.row][t.col] = " ";
        }
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
