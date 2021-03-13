package BejeweledMain;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class BejeweledTimer {
    public static BejeweledTimer bTimer;
    private Timer timer;
    private int runTime;
    private HashMap<String, Integer> extraTime = new HashMap<>();

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

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runTime--;
                if (runTime < 0) {
                    timer.cancel();
                    System.out.print("\nYou're Out of Time, Press <ENTER> to Continue.");
                }

            }
        }, 0, 1000); //period is in milliseconds
    }

    public void stopTimer() {
        if (runTime >= 0) {
            timer.cancel();
        }
    }

    public void initExtraTime(String name, int time){
        extraTime.put(name,time);
    }

    public int getPlayerExtraTime(String name){

        for(var player : extraTime.keySet()){
            if(player.equals(name)){
                return extraTime.get(player);
            }
        }
        return 0;
    }

    public void addPlayerExtraTime(String name, int time){

        for(var player : extraTime.keySet()){
            if(player.equals(name)){
                if(time == 0){
                    extraTime.put(player, 0);
                }
                else {
                    extraTime.put(player, extraTime.get(player) + time);
                }
            }
        }
    }

    public int getExtraTimeSize(){
        return extraTime.size();
    }


}