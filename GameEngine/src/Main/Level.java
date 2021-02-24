package Main;

import java.util.ArrayList;

public class Level {
    Board board;
    int targetScore;
    ArrayList<String> tiles;

    Level(ArrayList<String> tiles, int row, int col){
        this.tiles = tiles;
        board = new Board(tiles, row, col);
    }

    int getTargetScore(){
        return targetScore;
    }
}
