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

    void toggleTurn(){
        playerTurn = playerTurn == 0 ? 1 : 0;
    }

}
