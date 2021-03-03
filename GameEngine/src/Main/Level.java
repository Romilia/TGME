package Main;

import GameLogic.Move;
import Manager.ScoreManager;
import Manager.TurnManager;

import java.util.ArrayList;


public class Level {
    protected Board board;
    int targetScore;
    private ArrayList<String> tiles;
//    public Move move;
//    CandyCrushMove candyCrushMove;
//    BejeweledMove bejeweledMove;


    public Level(int target) {
        this.targetScore = target;
    }

    protected void setTiles(ArrayList<String> gameTiles) {
        tiles = gameTiles;
    }

    protected void setBoard(int row, int col) {
        board = new Board(tiles, row, col);
    }

//    protected void setMove(TurnManager turnManager, ScoreManager scoreManager, String type) {
////        move = new Move(board, scoreManager, turnManager)
//    }

    protected Board getLevelBoard() {
        return board;
    }


    public int getTargetScore() {
        return targetScore;
    }
}