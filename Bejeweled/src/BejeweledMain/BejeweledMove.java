package BejeweledMain;

import GameLogic.Move;
import Main.Board;

import java.util.ArrayList;

public class BejeweledMove extends Move { // need to import Move

    public BejeweledMove() {

        super();
    }

    private boolean isValidMove(int selected_row, int selected_col, int target_row, int target_col) {
        //TODO check valid move
        return false;
    }

    public ArrayList<Integer> findMatch(Board board, int col, int row) {
        //TODO, what exactly is this list returning?
        return new ArrayList<>();
    }

    public void findAllPossibleMatchesAfterUpdate(Board board) {
        //TODO findAllPossibleMatchesAfterUpdate
    }

}
