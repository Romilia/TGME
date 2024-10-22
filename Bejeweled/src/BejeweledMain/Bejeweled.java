package BejeweledMain;

import Main.Game;


public class Bejeweled extends Game {
    private BejeweledLevel[] levels;
    private int curLevel;

    public Bejeweled() {

        super("BEJEWELED");

    }

    public boolean startGame() {
        playerCreation();
        initializeGame();
        return anotherGame();
    }

    private void initializeGame() {
        levels = new BejeweledLevel[5];
        curLevel = 0;

        for (int i = 0; i < 5; i++) {
            levels[i] = new BejeweledLevel(5, 5, turnManager, scoreManager);
            levels[i].startLevel(i + 1);
            curLevel++;
        }

        scoreManager.setGameName("Bejeweled");
        gameHistory.add(scoreManager);
    }
}