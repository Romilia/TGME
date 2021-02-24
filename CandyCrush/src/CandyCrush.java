public class CandyCrush {
    private final int lives = 10; //TODO: decide on how many lives
    private CandyCrushLevel[] levels;
    private int minBoardSize;
    private int maxBoardSize;
    private CandyCrushLevel curLevel;

    public CandyCrush(){
        //TODO decide levels, minBoardSize, maxBoardSize, curLevel
        curLevel = null;
    }

    public int generateNewBoardDimension() {
        return (int) ((Math.random() * (maxBoardSize - minBoardSize)) + minBoardSize);
    }
}
