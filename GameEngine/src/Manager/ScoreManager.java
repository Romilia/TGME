package Manager;

import Player.Player;

public class ScoreManager {
    private String gameName;
    private Player player1;
    private Player player2;
    private int curScoreP1;
    private int curScoreP2;


    public ScoreManager(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getCurScoreP1(){
        return curScoreP1;
    }

    public int getCurScoreP2(){
        return  curScoreP2;
    }
    public void addToCurrentP1Score(int score) {
        curScoreP1 += score;
    }

    public void addToCurrentP2Score(int score) {
        curScoreP2 += score;
    }

    @Override
    public String toString() {
        return "\nGame:" + this.gameName + "\n" +
               "Player " + player1.getName() + "'s Score:" + curScoreP1 + "\n" +
                "Player " + player2.getName() + "'s Score:" + curScoreP2;
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }
}
