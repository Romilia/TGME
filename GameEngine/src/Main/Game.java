package Main;

import java.util.ArrayList;

import GameLogic.Moves;
import Manager.TurnManager;

public class Game {
    private String gameName;
    private TurnManager turnManager;
    private Moves move;
    private ArrayList<String> tiles = new ArrayList<String>();

    Game(String name)
    {
        gameName = name;
    }

    //TODO: Implement function
    void startGame()
    {
        System.out.println("GAME STARTED");
    }

    String getGameName()
    {
        return gameName;
    }

    //TODO: Implement function
    boolean gameEnded()
    {
        return false;
    }

    //TODO: Implement function
    void updateBoard()
    {

    }

    //TODO: Implement function
    ArrayList<String> getTiles()
    {
        return new ArrayList<String>();
    }

}