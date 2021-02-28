package Manager;
import Player.Player;

public class TurnManager {
    private int playerTurn;

    public TurnManager(){
        playerTurn = 0;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void toggleTurn(){
        playerTurn = playerTurn == 0 ? 1 : 0;
    }

}
