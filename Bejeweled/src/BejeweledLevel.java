import Main.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BejeweledLevel extends Level {

    private float timeLimit;
    private BejeweledTimer timer;
    // Is this what is stored here?
    // Will we need to add more to it? - Can it just be an array?
    // Does tiles just store chars?
    private static List<String> tiles = new ArrayList<>(Arrays.asList("g","b","r","y"));

    public BejeweledLevel(int row, int column){
        super(tiles,row,column);
        timer = new BejeweledTimer();
        timeLimit = 240;

    }

    public static List<String> getTiles() {
        return tiles;
    }

    public static void setTiles(List<String> tiles) {
        BejeweledLevel.tiles = tiles;
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

}
