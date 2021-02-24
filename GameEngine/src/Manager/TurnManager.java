package Manager;
import Player.Player;

public class TurnManager {
    int playerTurn;

    TurnManager(Player[] players){
        playerTurn = 1;
    }

    int getPlayerTurn() {
        return playerTurn;
    }

    void toggleTurn(){
        playerTurn = playerTurn == 1 ? 2 : 1;
    }

    // TODO: implement function
    void changePlayer(){

    }
}
