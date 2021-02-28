package Main;

import java.util.ArrayList;
import java.util.Scanner;

import GameLogic.Move;
import Manager.ScoreManager;
import Manager.TurnManager;
import Player.Player;

public class Game extends Main{
    private Scanner scan = new Scanner(System.in);
    private String gameName;
    protected TurnManager turnManager;
    protected ScoreManager scoreManager;
    private ArrayList<Player> players = new ArrayList<Player>();

    public Game(String name)
    {
        gameName = name;
        turnManager = new TurnManager();
    }


    protected void playerCreation()
    {
        System.out.println("----" + gameName + "----");
        System.out.println("Select a choice");
        System.out.println("1. Create new players");
        System.out.println("2. Play as existing players");
        int choice = scan.nextInt();
        scan.nextLine();

        switch(choice)
        {
            case 1:
                System.out.println("Enter Player 1's Username:");
                String name = scan.nextLine();
                Player player1 = new Player(name);
                players.add(player1);
                existingPlayers.add(player1);

                System.out.println("Enter Player 2's Username:");
                name = scan.nextLine();
                Player player2 = new Player(name);
                players.add(player2);
                existingPlayers.add(player2);
                scoreManager = new ScoreManager(player1, player2);
                break;
            case 2:
                System.out.println("Enter Player 1's Existing Username:");
                String name1 = scan.nextLine();
                System.out.println("Enter Player 2's Existing Username:");
                String name2 = scan.nextLine();
                for(Player player: existingPlayers)
                {
                    if(player.getName().equals(name1) || player.getName().equals(name2) )
                    {
                        players.add(player);
                    }
                }
                scoreManager = new ScoreManager(players.get(0), players.get(1));
                break;
        }
    }

    String getGameName()
    {
        return gameName;
    }

    //TODO: Implement function
    protected boolean gameEnded()
    {
        return true;
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
