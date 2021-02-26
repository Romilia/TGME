package Main;

import java.util.ArrayList;
import java.util.Scanner;

import GameLogic.Move;
import Manager.TurnManager;
import Player.Player;

public class Game extends Main{
    private Scanner scan = new Scanner(System.in);
    private String gameName;
    private ArrayList<Player> players = new ArrayList<Player>();

    public Game(String name)
    {
        gameName = name;
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
                players.add( new Player(name));

                System.out.println("Enter Player 2's Username:");
                name = scan.nextLine();
                players.add( new Player(name));
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
