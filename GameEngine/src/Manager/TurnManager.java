package Manager;
import Player.Player;

public class TurnManager {
    int playerTurn;

    TurnManager(){
        playerTurn = 0;
    }

    int getPlayerTurn() {
        return playerTurn;
    }

    void toggleTurn(){
        playerTurn = playerTurn == 0 ? 1 : 0;
    }

}
