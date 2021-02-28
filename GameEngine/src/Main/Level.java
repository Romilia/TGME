package Main;

import GameLogic.Move;
import Manager.ScoreManager;
import Manager.TurnManager;

import java.util.ArrayList;

public class Level {
    private Board board;
    private int targetScore;
    private ArrayList<String> tiles;
    public Move move;


    public Level(int target){
        this.targetScore = target;
//        this.tiles = tiles;
//        board = new Board(tiles, row, col);
//       move = new Move(board, scoreManager, turnManager); //TODO how do I initialize Move here since Move takes in a score manager and turn manager
    }

    protected void setTiles(ArrayList<String> gameTiles)
    {
        tiles = gameTiles;
    }

    protected void setBoard(int row, int col)
    {
        board = new Board(tiles, row, col);
    }

    protected void setMove(TurnManager turnManager, ScoreManager scoreManager){
        move = new Move(board, scoreManager, turnManager);
    }

    protected Board getLevelBoard()
    {
        return board;
    }


    int getTargetScore(){
        return targetScore;
    }
}
