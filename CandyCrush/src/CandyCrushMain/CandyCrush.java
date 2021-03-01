package CandyCrushMain;

import Main.Game;

import java.util.Scanner;

public class CandyCrush extends Game {
    private final int lives = 3; //TODO: ask if 3 lives is good
    private CandyCrushLevel[] levels;
    private int minBoardSize;
    private int maxBoardSize;
    private CandyCrushLevel curLevel;
    private Scanner scan = new Scanner(System.in);

    public CandyCrush(){
        super("CANDY_CRUSH");
        //System.out.println("~~~~ Candy Crush ~~~~");

    }

    public boolean startGame()
    {
        playerCreation();

        //TODO decide levels, minBoardSize, maxBoardSize, curLevel
        initializeGame();
        return anotherGame();
    }

    public void initializeGame()
    {
        levels = new CandyCrushLevel[5];
        curLevel = null;
        minBoardSize = 4;
        maxBoardSize = 6; //TODO the smallest board we can get is 4x4 adn the biggest is 6x6. Is this fine?

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
