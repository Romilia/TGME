package BejeweledMain;

import GameLogic.Move;
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
    private BejeweledMove bejeweledMove;

    public void setMove(TurnManager turnManager, ScoreManager scoreManager)
    {
        bejeweledMove = new BejeweledMove(super.getLevelBoard(),scoreManager,turnManager);
    }

    public BejeweledLevel(int row, int column, TurnManager turnManager, ScoreManager scoreManager) {
        super(5);
        timer = BejeweledTimer.getInstance();
        setTiles(bejeweledTiles);
        setBoard(5,5);
        this.setMove(turnManager, scoreManager);
        movesLeft = 10;
        //getLevelBoard().print();
    }
    public BejeweledMove getBejeweledMove(){
        return bejeweledMove;
    }

    public void startLevel(int lvl) {
        System.out.println("Bejeweled Level " + lvl);
        //System.out.println("Player " + move.scoreManager.getPlayer1().getName() + " Turn");

        System.out.println("TARGET SCORE: " + getTargetScore());
        for (int i = 0; i < 2 ; i++){
            String currentPlayer;
            if(bejeweledMove.turnManager.getPlayerTurn() == 0) {
                currentPlayer =  bejeweledMove.scoreManager.getPlayer1().getName();
            }
            else
            {
                currentPlayer =  bejeweledMove.scoreManager.getPlayer2().getName();
            }
            System.out.println("PLAYER TURN: " + currentPlayer);


            timer.startTimer();
            System.out.println("Timer Started at: " + timer.getRunTime());
            bejeweledMove.makeMove(getTargetScore());
            bejeweledMove.turnManager.toggleTurn();
            System.out.println("Time Left: " + (timer.getRunTime() + 1));
            timer.stopTimer();
            timer.setRunTime(10);// reset timer
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