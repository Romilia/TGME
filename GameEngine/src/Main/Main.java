package Main;

import Manager.ScoreManager;
import Player.Player;
import java.util.ArrayList;

public class Main {
    public static ArrayList<Player> existingPlayers = new ArrayList<Player>();
    public static ArrayList<ScoreManager> gameHistory = new ArrayList<>();
    private Game currentGame;

    public Main() {
    }

    public void setGame(Game currGame) {
        currentGame = currGame;
    }

    public void viewPlayerStats() {
        System.out.println("Number of games played: " + gameHistory.size());
        for (ScoreManager sm : gameHistory){
            System.out.println(sm);
        }
    }

}
