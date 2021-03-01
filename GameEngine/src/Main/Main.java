package Main;

import Player.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Player> existingPlayers = new ArrayList<Player>();
    private Game currentGame;

    public Main() {
    }

    public void setGame(Game currGame) {
        currentGame = currGame;
    }

    public void viewPlayer(String playerName) {
        boolean found = false;
        for (Player player : existingPlayers) {
            if (player.getName().equals(playerName)) {
                found = true;
                System.out.println("High Score: " + player.calculateHighScore());
            }
        }

        if (!found) {
            System.out.println(playerName + " was not found!");
        }
    }

}
