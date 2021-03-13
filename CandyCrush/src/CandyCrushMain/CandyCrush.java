package CandyCrushMain;

import Main.Game;

public class CandyCrush extends Game {
    private CandyCrushLevel[] levels;
    private int minBoardSize;
    private int maxBoardSize;

    public CandyCrush() {
        super("CANDY_CRUSH");
    }

    public boolean startGame() {
        playerCreation();

        initializeGame();
        return anotherGame();
    }

    public void initializeGame() {
        levels = new CandyCrushLevel[5];
        minBoardSize = 4;
        maxBoardSize = 6;

        for (int i = 0; i < 5; i++) {
            int row = generateNewBoardDimension();
            int col = generateNewBoardDimension();
            levels[i] = new CandyCrushLevel(row, col, turnManager, scoreManager);
            levels[i].startLevel(i + 1);
        }

        scoreManager.setGameName("CandyCrush");
        gameHistory.add(scoreManager);

    }


    public int generateNewBoardDimension() {
        return (int) ((Math.random() * (maxBoardSize - minBoardSize)) + minBoardSize);
    }
}
