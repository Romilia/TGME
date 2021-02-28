package BejeweledMain;

import GameLogic.Tuple;
import Main.Game;

import java.util.ArrayList;

public class Bejeweled extends Game {
    private int targetScore;
    private ArrayList<Tuple> levelTargetScoreList;
    private BejeweledLevel level;
    private int curLevel;

    public Bejeweled(){
        super("BEJEWELED");
        System.out.println("~~~~ Bejeweled ~~~~");
        playerCreation();
        initializeGame();
        // init targetScore
        // init levelTargetScoreList

    };

    private void initializeGame()
    {
        level = new BejeweledLevel(5,5);
    }

    public int getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
    }

    public BejeweledLevel getLevel() {
        return level;
    }

    public void setLevel(BejeweledLevel level) {
        this.level = level;
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

    public void increaseTime(){
        level.getTimer().addTime(30);
    }


}