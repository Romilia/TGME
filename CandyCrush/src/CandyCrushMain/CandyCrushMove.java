package CandyCrushMain;

import Main.Board;
import GameLogic.Move;
import java.util.ArrayList;

public class CandyCrushMove extends Move{
    public CandyCrushMove() {
        super();
    }

    private boolean isValidMove(int selected_row, int selected_col, int target_row, int target_col){
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
