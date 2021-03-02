package BejeweledMain;

import Main.Level;

import java.util.ArrayList;
import java.util.Arrays;

public class BejeweledLevel extends Level {
    private float timeLimit;
    private BejeweledTimer timer;
    private ArrayList<String> bejeweledTiles = new ArrayList<String>(Arrays.asList("R", "G", "B", "Y"));

    public BejeweledLevel(int row, int column) {
        super(5);
        setTiles(bejeweledTiles);
        setBoard(row, column);
        System.out.println("Bejeweled Level");
        getLevelBoard().print();
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
