package CandyCrushMain;

import Main.Game;

public class CandyCrush extends Game {
    private final int lives = 10; //TODO: decide on how many lives
    private CandyCrushLevel[] levels;
    private int minBoardSize;
    private int maxBoardSize;
    private CandyCrushLevel curLevel;

    public CandyCrush(){
        super("CANDY_CRUSH");
        System.out.println("~~~~ Candy Crush ~~~~");
        playerCreation();

        //TODO decide levels, minBoardSize, maxBoardSize, curLevel
        initializeGame();
    }

    private void initializeGame()
    {
        levels = new CandyCrushLevel[5];
        curLevel = null;
        minBoardSize = 3;
        maxBoardSize = 5;

        for(int i = 0; i < 5 && !gameEnded(); i++){
            levels[i] = new CandyCrushLevel(generateNewBoardDimension(),generateNewBoardDimension());
            levels[i].startLevel(i+1);
        }
    }

    public int generateNewBoardDimension() {
        return (int) ((Math.random() * (maxBoardSize - minBoardSize)) + minBoardSize);
    }
}
