package CandyCrushMain;

import Main.Level;
public class CandyCrushLevel {

    int stars;
    int movesLeft;
    int hintsLeft;

    public CandyCrushLevel(int row, int col){
        super(); //TODO get movesLeft? What happens with stars and these row and cols?
        hintsLeft = 3;
    }

    public int getHintsLeft() {
        return hintsLeft;
    }

}
