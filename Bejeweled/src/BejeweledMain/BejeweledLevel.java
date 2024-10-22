package BejeweledMain;

import Main.Level;
import Manager.ScoreManager;
import Manager.TurnManager;

import java.util.ArrayList;
import java.util.Arrays;


public class BejeweledLevel extends Level {
    private BejeweledTimer timer;
    private ArrayList<String> bejeweledTiles = new ArrayList<String>(Arrays.asList("R", "G", "B", "Y"));
    private BejeweledMove bejeweledMove;


    public void setMove(TurnManager turnManager, ScoreManager scoreManager)
    {
        bejeweledMove = new BejeweledMove(super.getLevelBoard(),scoreManager,turnManager);
    }

    public BejeweledLevel(int row, int column, TurnManager turnManager, ScoreManager scoreManager) {
        super(5);
        timer = BejeweledTimer.getInstance();
        setTiles(bejeweledTiles);
        setBoard(row,column);
        this.setMove(turnManager, scoreManager);

    }


    public void startLevel(int lvl) {
        System.out.println("\n>>>BEJEWELED LEVEL " + lvl + "<<<");
        System.out.println("TARGET SCORE: " + getTargetScore());
        if(timer.getExtraTimeSize() <= 0) {
            timer.initExtraTime(bejeweledMove.scoreManager.getPlayer1().getName(), 0);
            timer.initExtraTime(bejeweledMove.scoreManager.getPlayer2().getName(), 0);
        }

        for (int i = 0; i < 2 ; i++){
            String currentPlayer;
            if(bejeweledMove.turnManager.getPlayerTurn() == 0) {
                currentPlayer =  bejeweledMove.scoreManager.getPlayer1().getName();
            }
            else
            {
                currentPlayer =  bejeweledMove.scoreManager.getPlayer2().getName();
            }

            int newRunTime = timer.getPlayerExtraTime(currentPlayer) + 45;
            timer.setRunTime(newRunTime);
            System.out.println("TIMER STARTED AT: " + timer.getRunTime()); //need to update if we change the runTime in BejeweledTimer
            System.out.println("\nPLAYER TURN: " + currentPlayer);

            timer.startTimer();
            bejeweledMove.makeMove(getTargetScore(), timer);
            bejeweledMove.turnManager.toggleTurn();
            timer.stopTimer();
            if(timer.getRunTime() > 0){
                timer.addPlayerExtraTime(currentPlayer,5);
            }
            else{
                timer.addPlayerExtraTime(currentPlayer,0);
            }
        }
    }
}