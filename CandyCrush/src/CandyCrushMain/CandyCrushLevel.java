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
        super(5); //TODO get movesLeft? What happens with stars and these row and cols?
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
