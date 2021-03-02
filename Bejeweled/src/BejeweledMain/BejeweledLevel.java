package BejeweledMain;

import Main.Level;
import Manager.ScoreManager;
import Manager.TurnManager;

import java.util.ArrayList;
import java.util.Arrays;

public class BejeweledLevel extends Level {
    private float timeLimit;
    private BejeweledTimer timer;
    private ArrayList<String> bejeweledTiles = new ArrayList<String>(Arrays.asList("R", "G", "B", "Y"));
    int movesLeft;

    public BejeweledLevel(int row, int column, TurnManager turnManager, ScoreManager scoreManager) {
        super(5);
        timer = new BejeweledTimer();
        setTiles(bejeweledTiles);
        setBoard(5,5);
        setMove(turnManager, scoreManager);
        movesLeft = 10;
        //setBoard(row, column);
        getLevelBoard().print();
    }

    public void startLevel(int lvl) {
        System.out.println("Bejeweled Level " + lvl);
        //System.out.println("Player " + move.scoreManager.getPlayer1().getName() + " Turn");

        System.out.println("NUMBER OF MOVES AVAILABLE: " + movesLeft);
        System.out.println("TARGET SCORE: " + getTargetScore());
        for (int i = 0; i < 2 ; i++){
            String currentPlayer;
            if(move.turnManager.getPlayerTurn() == 0) {
                currentPlayer =  move.scoreManager.getPlayer1().getName();
            }
            else
            {
                currentPlayer =  move.scoreManager.getPlayer2().getName();
            }
            System.out.println("PLAYER TURN: " + currentPlayer);
            timer.startTimer();
            System.out.println("Timer Started at: " + timer.getRunTime());
            move.makeMove(movesLeft, getTargetScore());
            move.turnManager.toggleTurn();
            System.out.println("Time Left: " + timer.getRunTime());
            timer.stopTimer();
            timer.setRunTime(240);// reset timer
        }
    }

    public float getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(float timeLimit) {
        this.timeLimit = timeLimit;
    }

    public BejeweledTimer getTimer() {
        return timer;
    }

    public void setTimer(BejeweledTimer timer) {
        this.timer = timer;
    }

    public ArrayList<String> getBejeweledTiles() {
        return bejeweledTiles;
    }

    public void setBejeweledTiles(ArrayList<String> bejeweledTiles) {
        this.bejeweledTiles = bejeweledTiles;
    }


}
