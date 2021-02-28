import Main.Game;
import GameLogic.Tuple;
import java.util.ArrayList;
import java.util.List;

public class Bejeweled extends Game{
    private int targetScore;
    private List<Tuple> levelTargetScoreList;
    private BejeweledLevel level;
    private int curLevel;

    public Bejeweled(String gameName){
        super(gameName);
        levelTargetScoreList = new ArrayList<>();
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
        level.getTimer().addTime(3); // Are we adding a specific amt of time?

    }


}
