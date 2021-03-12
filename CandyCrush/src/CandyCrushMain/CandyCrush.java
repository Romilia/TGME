package CandyCrushMain;

import Main.Game;
import Player.Player;

import java.util.Scanner;

public class CandyCrush extends Game {
    private final int lives = 3;
    private CandyCrushLevel[] levels;
    private int minBoardSize;
    private int maxBoardSize;
    private CandyCrushLevel curLevel;
    private Scanner scan = new Scanner(System.in);

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
        curLevel = null;
        minBoardSize = 4;
        maxBoardSize = 6;

        for (int i = 0; i < 5; i++) {
            int row = generateNewBoardDimension();
            int col = generateNewBoardDimension();
            levels[i] = new CandyCrushLevel(row, col, turnManager, scoreManager);
            levels[i].startLevel(i + 1);
        }

        Player player1 = scoreManager.getPlayer1();
        Player player2 = scoreManager.getPlayer2();
        scoreManager.setGameName("CandyCrush");
        player1.addToGameHistory(scoreManager);
        player2.addToGameHistory(scoreManager);

    }


    public int generateNewBoardDimension() {
        return (int) ((Math.random() * (maxBoardSize - minBoardSize)) + minBoardSize);
    }
}
