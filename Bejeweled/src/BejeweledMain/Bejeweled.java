package BejeweledMain;

import Main.Game;

public class Bejeweled extends Game { // HOW TO ACCESS GAME CLASS?
    private int targetScore;
    //private levelTargetScoreList; // JAVA TUPLES?
    private BejeweledLevel level;
    private int curLevel;

    public Bejeweled(){
        super("BEJEWELED");
        System.out.println("~~~~ Bejeweled ~~~~");
        playerCreation();
    };

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

    //TODO
    public void increaseTime(){
        // HOW ARE WE IMPLEMENTING THIS?
    }


}
