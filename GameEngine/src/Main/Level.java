package Main;

import GameLogic.Move;

import java.util.ArrayList;

public class Level {
    Board board;
    int targetScore;
    ArrayList<String> tiles;
    Move move;


    public Level( int target){
        this.targetScore = target;
//        this.tiles = tiles;
//        board = new Board(tiles, row, col);
       /* move = new Move(board);*/ //TODO how do I initialize Move here since Move takes in a score manager and turn manager
    }

    protected void setTiles(ArrayList<String> gameTiles)
    {
        tiles = gameTiles;
    }

    protected void setBoard(int row, int col)
    {
        board = new Board(tiles, row, col);
    }

    protected Board getLevelBoard()
    {
        return board;
    }

    int getTargetScore(){
        return targetScore;
    }
}
