package BejeweledMain;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class BejeweledTimer {
    public static BejeweledTimer bTimer;
    private Timer timer;
    private int runTime;


    //public BejeweledTimer() {}

    public static BejeweledTimer getInstance()
    {
        if (bTimer == null)
            bTimer = new BejeweledTimer();

        return bTimer;
    }
    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }


    public void startTimer() {
        timer = new Timer();
        runTime = 10;

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runTime--;
                if (runTime < 0) {
                    timer.cancel();
                    System.out.println("\nYou're Out of Time, Press <ENTER> to Continue.");
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