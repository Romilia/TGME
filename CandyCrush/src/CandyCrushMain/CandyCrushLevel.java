package CandyCrushMain;

import Main.Level;
import Manager.ScoreManager;
import Manager.TurnManager;
import java.util.ArrayList;
import java.util.Arrays;

public class CandyCrushLevel extends Level {
    int movesLeft;
    int hintsLeft;
    private CandyCrushMove candyCrushMove;
    private ArrayList<String> candyCrushTiles = new ArrayList<String>(Arrays.asList("P", "O", "Y", "A", "D"));

    private void setMove(TurnManager turnManager, ScoreManager scoreManager)
    {
        candyCrushMove = new CandyCrushMove(super.board,scoreManager,turnManager,this.candyCrushTiles);
    }
    public CandyCrushLevel(int row, int col, TurnManager turnManager, ScoreManager scoreManager) {
        super((row + col) / 2); //TODO what to do with stars and how do they work? Do we just want to ignore?
        movesLeft = row + col;
        hintsLeft = 3;
        setTiles(candyCrushTiles);
        setBoard(row, col);
        this.setMove(turnManager, scoreManager);
    }

    public void startLevel(int lvl) {
        System.out.println("\n>>>CANDY CRUSH LEVEL " + lvl + "<<<");
        System.out.println("NUMBER OF MOVES AVAILABLE: " + movesLeft);
        System.out.println("TARGET SCORE: " + getTargetScore());
        int p1LastScore = candyCrushMove.scoreManager.getCurScoreP1();
        int p2LastScore = candyCrushMove.scoreManager.getCurScoreP2();
        for (int i = 0; i < 2 ; i++){
            String currentPlayer;
            if(candyCrushMove.turnManager.getPlayerTurn() == 0) {
                currentPlayer =  candyCrushMove.scoreManager.getPlayer1().getName();
            }
            else
            {
                currentPlayer =  candyCrushMove.scoreManager.getPlayer2().getName();
            }
            System.out.println("\nPLAYER TURN: " + currentPlayer);
            candyCrushMove.makeMove(hintsLeft,movesLeft, getTargetScore());
            if (candyCrushMove.turnManager.getPlayerTurn() == 0){
                if (candyCrushMove.scoreManager.getCurScoreP1() - p1LastScore >= super.getTargetScore()){
                    System.out.println("You got 3 stars!!");
                }
            }
            else{
                if (candyCrushMove.scoreManager.getCurScoreP2() - p2LastScore >= super.getTargetScore()){
                    System.out.println("You got 3 stars!!");
                }
            }
            candyCrushMove.turnManager.toggleTurn();
        }
    }

}