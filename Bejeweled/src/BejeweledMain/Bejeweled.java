package BejeweledMain;

import GameLogic.Tuple;
import Main.Game;
import Player.Player;

import java.util.ArrayList;

public class Bejeweled extends Game {
    private int targetScore;
    private ArrayList<Tuple> levelTargetScoreList;
    private BejeweledLevel[] levels;
    private int curLevel;

    public Bejeweled() {

        super("BEJEWELED");

    }

    public boolean startGame() {
        playerCreation();
        initializeGame();
        return anotherGame();
    }

    private void initializeGame() {
        levels = new BejeweledLevel[5];
        curLevel = 0;

        for (int i = 0; i < 5 && !gameEnded(); i++) {
            levels[i] = new BejeweledLevel(5, 5, turnManager, scoreManager);
            levels[i].startLevel(i + 1);
            curLevel++;
        }
        Player player1 = scoreManager.getPlayer1();
        Player player2 = scoreManager.getPlayer2();
        player1.addToGameHistory(scoreManager);
        player2.addToGameHistory(scoreManager);
        System.out.println(scoreManager.getCurrentP1Score());
        System.out.println(scoreManager.getCurrentP2Score());
    }

    public int getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
    }

    public BejeweledLevel[] getLevel() {
        return levels;
    }

    public void setLevel(BejeweledLevel[] level) {
        this.levels = level;
    }

    public int getCurLevel() {
        return curLevel;
    }

    public void setCurLevel(int curLevel) {
        this.curLevel = curLevel;
    }

    public ArrayList<Tuple> getLevelTargetScoreList() {
        return levelTargetScoreList;
    }

    public void setLevelTargetScoreList(ArrayList<Tuple> levelTargetScoreList) {
        this.levelTargetScoreList = levelTargetScoreList;
    }

    public void increaseTime() {
        levels[curLevel].getTimer().addTime(30);
    }


}