package CandyCrushMain;

import Main.Game;

public class CandyCrush extends Game {
    private final int lives = 3;
    private CandyCrushLevel[] levels;
    private int minBoardSize;
    private int maxBoardSize;
    private CandyCrushLevel curLevel;

    public CandyCrush(){
        super("CANDY_CRUSH");
        //System.out.println("~~~~ Candy Crush ~~~~");
        playerCreation();

        initializeGame();
    }

    private void initializeGame()
    {
        levels = new CandyCrushLevel[5];
        curLevel = null;
        minBoardSize = 4;
        maxBoardSize = 6;

        for(int i = 0; i < 5 && !gameEnded(); i++){
            int row = generateNewBoardDimension();
            int col = generateNewBoardDimension();
            levels[i] = new CandyCrushLevel(row, col, turnManager, scoreManager);
            levels[i].startLevel(i+1);
        }

    }

    public int generateNewBoardDimension() {
        return (int) ((Math.random() * (maxBoardSize - minBoardSize)) + minBoardSize);
    }
}
