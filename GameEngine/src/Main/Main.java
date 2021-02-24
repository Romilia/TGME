package Main;

import Player.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private Scanner scan = new Scanner(System.in);
    private Game games[];
    private ArrayList<Player> players = new ArrayList<Player>();
    private Game currentGame;
    private String names[] = new String[]{"BEJEWELED", "CANDY_CRUSH"};


     public Main(){
        int MAX_GAMES = 2;
        games = new Game[MAX_GAMES];

        for(int i = 0; i < MAX_GAMES; i++)
        {
            games[i] = new Game(names[i]);
        }

         menuChoice();
     }

     public void menuChoice()
     {
         System.out.println("Make a Selection");
         System.out.println("1. Bejeweled");
         System.out.println("2. Candy Crush");
         System.out.println("3. View Player Stats");
         int choice = scan.nextInt();
         scan.nextLine();


         switch (choice){
             case 1:
                 chooseGame("BEJEWELED");
                 break;
             case 2:
                 chooseGame("CANDY_CRUSH");
                 break;
             case 3:
                 System.out.println("Enter the player name:");
                 String name = scan.nextLine();
                 viewPlayer(name);
                 break;
         }

     }

    public void chooseGame(String name)
    {
        for(Game game: games)
        {
            if(game.getGameName().equals(name))
            {
                currentGame = game;
                break;
            }
        }
        currentGame.startGame();
    }

    //TODO: Figure out what to show for chosen player
    public void viewPlayer(String playerName)
    {
        boolean found = false;
        for(Player player: players)
        {
            if(player.getName().equals(playerName))
            {
                found = true;
                System.out.println("High Score: " + player.calculateHighScore());
            }
        }

        if(!found)
        {
            System.out.println(playerName + " was not found!");
        }
    }

}
