package BejeweledMain;

import java.util.Timer;
import java.util.TimerTask;

public class BejeweledTimer {
    private final Timer timer;
    private int runTime;

    public BejeweledTimer(){
        timer = new Timer();
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }
    public void startTimer(){
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runTime--;
                if (runTime< 0)
                    timer.cancel();
            }
        }, 0, 1000); //period is in milliseconds
    }

    public void addTime(int timeToAdd){ // Do we ADD ON time or CHANGE the time?
        if(runTime == 0){
            runTime += timeToAdd;
        }
    }

    //TODO: Method to stop the timer

}