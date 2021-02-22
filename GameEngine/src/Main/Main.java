package Main;

import Player.Player;

import java.util.ArrayList;

public class Main {
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
    }


    //TODO: Who is calling this function?
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
        for(Player player: players)
        {
            if(player.getName().equals(playerName))
            {

            }
        }
    }

}
