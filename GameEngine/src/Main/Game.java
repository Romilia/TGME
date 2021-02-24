package Main;

import java.util.ArrayList;

import GameLogic.Move;
import Manager.TurnManager;

public class Game {
    private String gameName;
    private TurnManager turnManager;
    private Move move;
    private ArrayList<String> tiles = new ArrayList<String>();

    Game(String name)
    {
        gameName = name;
    }

    //TODO: Implement function
    void startGame()
    {
        System.out.println("GAME STARTED " + gameName);
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
