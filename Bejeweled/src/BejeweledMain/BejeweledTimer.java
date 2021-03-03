package BejeweledMain;

import java.util.Timer;
import java.util.TimerTask;

public class BejeweledTimer {
    private Timer timer;
    private int runTime;

    public BejeweledTimer() {

        runTime = 240; // set initial time to complete level at 3 minutes
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runTime--;
                if (runTime < 0) {
                    timer.cancel();
                    System.out.println("Out of Time!");
                }
            }
        }, 0, 1000); //period is in milliseconds
    }

    public void addTime(int timeToAdd) {
        if (runTime == 0) {
            runTime += timeToAdd;
        }
    }

    public void stopTimer() {
        if (runTime > 0) {
            timer.cancel();
        }
    }

}