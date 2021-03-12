package Main;

import java.util.ArrayList;


public class Level {
    protected Board board;
    int targetScore;
    private ArrayList<String> tiles;

    public Level(int target) {
        this.targetScore = target;
    }

    protected void setTiles(ArrayList<String> gameTiles) {
        tiles = gameTiles;
    }

    protected void setBoard(int row, int col) {
        board = new Board(tiles, row, col);
    }

    protected Board getLevelBoard() {
        return board;
    }


    public int getTargetScore() {
        return targetScore;
    }
}