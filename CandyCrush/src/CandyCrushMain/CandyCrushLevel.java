package CandyCrushMain;

import Main.Level;
import Manager.ScoreManager;
import Manager.TurnManager;

import java.util.ArrayList;
import java.util.Arrays;

public class CandyCrushLevel extends Level {
    int stars;
    int movesLeft;
    int hintsLeft;
    private ArrayList<String> candyCrushTiles = new ArrayList<String>(Arrays.asList("P", "B", "O", "Y", "A", "D", "C"));

    public CandyCrushLevel(int row, int col, TurnManager turnManager, ScoreManager scoreManager) {
        super((row + col) / 2); //TODO what to do with stars and how do they work? Do we just want to ignore?
        movesLeft = row + col;
        hintsLeft = 3;
        setTiles(candyCrushTiles);
        setBoard(row, col);
        setMove(turnManager, scoreManager);
    }

    public void startLevel(int lvl) {
        System.out.println("Candy Crush Level " + lvl);
//        getLevelBoard().print();
//        for (int i = 0; i < 2 ; i++){
        System.out.println("Player " + move.scoreManager.getPlayer1().getName() + " Turn");
        move.makeMove();
        move.turnManager.toggleTurn();
//        }
    }

    public int getHintsLeft() {
        return hintsLeft;
    }

}
