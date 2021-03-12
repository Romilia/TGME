package Player;

import Main.Game;
import Manager.ScoreManager;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
    private String name;
    private int lives;
    public ArrayList<ScoreManager> gameHistory;

    public Player(String givenName) {
        name = givenName;
        lives = -1;
        gameHistory = new ArrayList<>();
    }


    public void setLives(int givenLives) {
        lives = givenLives;
    }

    // Getters Below
    public String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }

    public void addToGameHistory(ScoreManager scoreManager)
    {
        gameHistory.add(scoreManager);
    }
}
