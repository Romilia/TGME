package Main;

import GameLogic.Move;

import java.util.ArrayList;

public class Level {
    Board board;
    int targetScore;
    ArrayList<String> tiles;
    Move move;

    Level(ArrayList<String> tiles, int row, int col, int target){
        this.targetScore = target;
        this.tiles = tiles;
        board = new Board(tiles, row, col);
        move = new Move(board);
    }

    int getTargetScore(){
        return targetScore;
    }
}
