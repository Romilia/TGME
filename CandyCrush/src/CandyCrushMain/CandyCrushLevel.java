package CandyCrushMain;

import Main.Level;

import java.util.ArrayList;
import java.util.Arrays;

public class CandyCrushLevel extends Level{
    int stars;
    int movesLeft;
    int hintsLeft;
    private ArrayList<String> candyCrushTiles = new ArrayList<String>(Arrays.asList("P","B","O","Y"));

    public CandyCrushLevel(int row, int col){
        super((row+col) / 2); //TODO what to do with stars and how do they work? Do we just want to ignore?
        movesLeft = row + col;
        hintsLeft = 3;
        setTiles(candyCrushTiles);
        setBoard(row, col);
    }

    public void startLevel(int lvl)
    {
        System.out.println("Candy Crush Level " + lvl);
        getLevelBoard().print();


    }

    public int getHintsLeft() {
        return hintsLeft;
    }

}
