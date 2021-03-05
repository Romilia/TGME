package BejeweledMain;

import GameLogic.Move;
import GameLogic.Tuple;
import Main.Board;
import Manager.ScoreManager;
import Manager.TurnManager;

import java.util.ArrayList;
import java.util.HashSet;

//need to set the time
public class BejeweledMove extends Move { // need to import Move
    private Board board;
    public ScoreManager scoreManager;
    public TurnManager turnManager;
    private int score = 0;
    private String[][] newBoard;
    private BejeweledTimer timer;

    public BejeweledMove(Board board,ScoreManager scoreManager, TurnManager turnManager)
    {
        super();
        this.board = board;
        this.scoreManager = scoreManager;
        this.turnManager = turnManager;
        this.timer = new BejeweledTimer();
    }
    /*
    public boolean isValidMove(int select_row, int select_col, int target_row, int target_col){return true;}
    public void findMatch(Board board, int col, int row){}; // return type?
    public void findAllPossibleMatchesAfterupdate(Board board){} // return type?

     */
    public boolean hasMovesToMake() {
        //start from bottom left corner
        for (int row = this.board.getRow() - 1; row >= 0; row--) {
            for (int col = 0; col < this.board.getCol(); col++) {
                HashSet<Tuple> switchLeft = getRemovableTilesSwitchingLeft(row, col);
                HashSet<Tuple> switchRight = getRemovableTilesSwitchingRight(row, col);

                HashSet<Tuple> switchUp = getRemovableTilesSwitchingUp(row, col);
                HashSet<Tuple> switchDown = getRemovableTilesSwitchingDown(row, col);

                if (switchLeft.size() >= 3
                        || switchRight.size() >= 3
                        || switchUp.size() >= 3
                        || switchDown.size() >= 3) {
                    return true;
                }
            }
        }

        return false;
    }

    //take in time
    public void makeMove(int targetScore) {
        //set score back to 0 for player 2
//        timer.setRunTime(5);
        this.score = 0;
        int currentLevelTargetScore = targetScore/5;
        int currentLevel = 1;

        //ensures that all the updates are made and still has moves to make
        HashSet<Tuple> list = findAllMatchesAfterUpdate();
        while (list.size() >= 3 || !hasMovesToMake()) {
            this.board.updateBoard(list);
            while (!hasMovesToMake()) {
                this.board.populateBoard();
            }
            list = findAllMatchesAfterUpdate();
        }
        timer.startTimer();
        while (timer.getRunTime() > 0) // if times up, game ends
        {
            if (timer.getRunTime() == 0) {
                break;
            }
            System.out.println("\nCurrentLevel: " + currentLevel);
            System.out.println("Current Score: " + score);
            System.out.println("Time Left: " + timer.getRunTime());
            this.board.print();

            int row = super.promptRow();
            int col = super.promptCol();
            String direction = super.promptDirection();

            //ensures that it is a valid move
            if (this.isValidMove(row, col, direction, this.board.getRow(), this.board.getCol())) {
                HashSet<Tuple> removableTiles;
                if (direction.equals("left")) {
                    removableTiles = getRemovableTilesSwitchingLeft(row, col);

                } else if (direction.equals("right")) {
                    removableTiles = getRemovableTilesSwitchingRight(row, col);
                } else if (direction.equals("up")) {
                    removableTiles = getRemovableTilesSwitchingUp(row, col);
                } else { //down
                    removableTiles = getRemovableTilesSwitchingDown(row, col);
                }

                if (removableTiles.size() >= 3) {
                    //board should remove all these Tuple pairs and generate new tiles onto the board
                    this.score += removableTiles.size();

                    //the score is also the amount of time add to the timer
                    timer.addTime(removableTiles.size());

                    this.board.setBoard(this.newBoard);
                    this.board.updateBoard(removableTiles);

                    //after update, should check if there is anymore matches formed
                    while (true) {
                        //start checking from bottom
                        removableTiles = findAllMatchesAfterUpdate();
                        score += removableTiles.size();
                        //if no more matches, break
                        if (removableTiles.size() < 3) {
                            break;
                        }

                        //else update the board
                        this.board.setBoard(this.newBoard);
                        this.board.updateBoard(removableTiles);
                    }

                    if (this.score >= currentLevelTargetScore) {
                        currentLevel += 1;
                        currentLevelTargetScore += (targetScore / 5);
                    }

                } else {
                    System.out.println("No Matches Found");
                }
            } else {
                System.out.println("Invalid Move. Try again.");
            }
            //if not a valid move, nothing changes and prompt user for input again

            //need to check if there is possible matches to be make
            //if no more moves to make and has time left
            if (!hasMovesToMake() && timer.getRunTime() > 0) {
                this.board.populateBoard();
                list = findAllMatchesAfterUpdate();
                while (list.size() >= 3 || !hasMovesToMake()) {
                    this.board.updateBoard(list);
                    while (!hasMovesToMake()) {
                        this.board.populateBoard();
                    }
                    list = findAllMatchesAfterUpdate();
                }
            }
        }

        if (turnManager.getPlayerTurn() == 0) //player one
        {
            //new function in ScoreManager: addToCurrentP1Score() and getCurrentP1Score()
            scoreManager.addToCurrentP1Score(score);
        } else {
            //new function in ScoreManager: addToCurrentP2Score() and getCurrentP2Score()
            scoreManager.addToCurrentP2Score(score);
        }

        if(this.score >= targetScore)
        {
            System.out.println("CONGRATS: you successfully achieved the target score for all levels!!!\n");
        }
        else
        {
            System.out.println("GAME OVER: No more available moves to make. Failed to complete 5 levels.");
        }
    }

    private HashSet<Tuple> getRemovableTilesSwitchingLeft(int row, int col) {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();

        Tuple leftTile = new Tuple(row, col - 1);

        if (leftTile.col >= 0)//left tile not out of bound
        {
            String[][] boardCopy = this.board.getBoard();
            //switch
            String val = boardCopy[row][col];
            boardCopy[row][col] = boardCopy[row][col - 1];
            boardCopy[row][col - 1] = val;

            HashSet<Tuple> leftUp = checkUp(leftTile, boardCopy);
            HashSet<Tuple> leftDown = checkDown(leftTile, boardCopy,this.board.getRow());

            if (leftUp.size() + leftDown.size() + 1 >= 3) {
                removableTiles.addAll(leftUp);
                removableTiles.addAll(leftDown);
                removableTiles.add(leftTile);
            }

            HashSet<Tuple> leftLeft = checkLeft(leftTile, boardCopy);
            if (leftLeft.size() + 1 >= 3) {
                removableTiles.addAll(leftLeft);
                if (!removableTiles.contains(leftTile)) {
                    removableTiles.add(leftTile);
                }
            }

            //right tile
            //check in here because if left tile does not exist, cannot switch
            Tuple rightTile = new Tuple(row, col);
            HashSet<Tuple> rightUp = checkUp(rightTile, boardCopy);
            HashSet<Tuple> rightDown = checkDown(rightTile, boardCopy,this.board.getRow());

            if (rightUp.size() + rightDown.size() + 1 >= 3) {
                removableTiles.addAll(rightUp);
                removableTiles.addAll(rightDown);
                removableTiles.add(rightTile);
            }

            HashSet<Tuple> rightRight = checkRight(rightTile, boardCopy,this.board.getCol());
            if (rightRight.size() + 1 >= 3) {
                removableTiles.addAll(rightRight);
                if (!removableTiles.contains(rightTile)) {
                    removableTiles.add(rightTile);
                }
            }
            if (removableTiles.size() >= 3) {
                this.newBoard = boardCopy;
            }
        }
        return removableTiles;
    }

    private HashSet<Tuple> getRemovableTilesSwitchingRight(int row, int col) {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();

        Tuple rightTile = new Tuple(row, col + 1);

        if (rightTile.col < this.board.getCol())//right tile not out of bound
        {
            String[][] boardCopy = this.board.getBoard();
            //switch
            String val = boardCopy[row][col];
            boardCopy[row][col] = boardCopy[row][col + 1];
            boardCopy[row][col + 1] = val;

            //right tile
            HashSet<Tuple> rightUp = checkUp(rightTile, boardCopy);
            HashSet<Tuple> rightDown = checkDown(rightTile, boardCopy,this.board.getRow());

            if (rightUp.size() + rightDown.size() + 1 >= 3) {
                removableTiles.addAll(rightUp);
                removableTiles.addAll(rightDown);
                removableTiles.add(rightTile);
            }

            HashSet<Tuple> rightRight = checkRight(rightTile, boardCopy,this.board.getCol());
            if (rightRight.size() + 1 >= 3) {
                removableTiles.addAll(rightRight);
                if (!removableTiles.contains(rightTile)) {
                    removableTiles.add(rightTile);
                }
            }

            //left tile
            //check in here because if right tile does not exist, cannot switch
            Tuple leftTile = new Tuple(row, col);
            HashSet<Tuple> leftUp = checkUp(leftTile, boardCopy);
            HashSet<Tuple> leftDown = checkDown(leftTile, boardCopy,this.board.getRow());

            if (leftUp.size() + leftDown.size() + 1 >= 3) {
                removableTiles.addAll(leftUp);
                removableTiles.addAll(leftDown);
                removableTiles.add(leftTile);
            }

            HashSet<Tuple> leftLeft = checkLeft(leftTile, boardCopy);
            if (leftLeft.size() + 1 >= 3) {
                removableTiles.addAll(leftLeft);
                if (!removableTiles.contains(leftTile)) {
                    removableTiles.add(leftTile);
                }
            }
            if (removableTiles.size() >= 3) {
                this.newBoard = boardCopy;
            }
        }
//        System.out.println(removableTiles);
        return removableTiles;
    }

    private HashSet<Tuple> getRemovableTilesSwitchingUp(int row, int col) {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();

        Tuple topTile = new Tuple(row - 1, col);

        if (topTile.row >= 0)//up tile not out of bound
        {
            String[][] boardCopy = this.board.getBoard();
            //switch
            String val = boardCopy[row][col];
            boardCopy[row][col] = boardCopy[row - 1][col];
            boardCopy[row - 1][col] = val;

            //up tile
            HashSet<Tuple> topLeft = checkLeft(topTile, boardCopy);
            HashSet<Tuple> topRight = checkRight(topTile, boardCopy,this.board.getCol());

            if (topLeft.size() + topRight.size() + 1 >= 3) {
                removableTiles.addAll(topLeft);
                removableTiles.addAll(topRight);
                removableTiles.add(topTile);
            }

            HashSet<Tuple> topUp = checkUp(topTile, boardCopy);
            if (topUp.size() + 1 >= 3) {
                removableTiles.addAll(topUp);
                if (!removableTiles.contains(topTile)) {
                    removableTiles.add(topTile);
                }
            }

            //down tile
            //check in here because if up tile does not exist, cannot switch

            Tuple bottomTile = new Tuple(row, col);
            HashSet<Tuple> bottomLeft = checkLeft(bottomTile, boardCopy);
            HashSet<Tuple> bottomRight = checkRight(bottomTile, boardCopy, this.board.getCol());

            if (bottomLeft.size() + bottomRight.size() + 1 >= 3) {
                removableTiles.addAll(bottomLeft);
                removableTiles.addAll(bottomRight);
                removableTiles.add(bottomTile);
            }

            HashSet<Tuple> bottomDown = checkDown(bottomTile, boardCopy,this.board.getRow());
            if (bottomDown.size() + 1 >= 3) {
                removableTiles.addAll(bottomDown);
                if (!removableTiles.contains(bottomTile)) {
                    removableTiles.add(bottomTile);
                }
            }
            if (removableTiles.size() >= 3) {
                this.newBoard = boardCopy;
            }
        }
//        System.out.println(removableTiles);
        return removableTiles;
    }

    private HashSet<Tuple> getRemovableTilesSwitchingDown(int row, int col) {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();

        Tuple bottomTile = new Tuple(row + 1, col);

        if (bottomTile.row < this.board.getRow())//bottom tile not out of bound
        {
            String[][] boardCopy = this.board.getBoard();
            //switch
            String val = boardCopy[row][col];
            boardCopy[row][col] = boardCopy[row + 1][col];
            boardCopy[row + 1][col] = val;

            //down tile
            HashSet<Tuple> bottomLeft = checkLeft(bottomTile, boardCopy);
            HashSet<Tuple> bottomRight = checkRight(bottomTile, boardCopy,this.board.getCol());

            if (bottomLeft.size() + bottomRight.size() + 1 >= 3) {
                removableTiles.addAll(bottomLeft);
                removableTiles.addAll(bottomRight);
                removableTiles.add(bottomTile);
            }

            HashSet<Tuple> bottomDown = checkDown(bottomTile, boardCopy,this.board.getRow());
            if (bottomDown.size() + 1 >= 3) {
                removableTiles.addAll(bottomDown);
                if (!removableTiles.contains(bottomTile)) {
                    removableTiles.add(bottomTile);
                }
            }

            //up tile
            //check in here because if bottom tile does not exist, cannot switch
            Tuple topTile = new Tuple(row, col);
            HashSet<Tuple> topLeft = checkLeft(topTile, boardCopy);
            HashSet<Tuple> topRight = checkRight(topTile, boardCopy,this.board.getCol());

            if (topLeft.size() + topRight.size() + 1 >= 3) {
                removableTiles.addAll(topLeft);
                removableTiles.addAll(topRight);
                removableTiles.add(topTile);
            }

            HashSet<Tuple> topUp = checkUp(topTile, boardCopy);
            if (topUp.size() + 1 >= 3) {
                removableTiles.addAll(topUp);
                if (!removableTiles.contains(topTile)) {
                    removableTiles.add(topTile);
                }
            }
            if (removableTiles.size() >= 3) {
                this.newBoard = boardCopy;
            }
        }
//        System.out.println(removableTiles);
        return removableTiles;
    }

    public HashSet<Tuple> findAllMatchesAfterUpdate() {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();

        //checking from bottom
        //starting from bottom left corner
        for (int row = this.board.getRow() - 1; row >= 0; row--) {
            for (int col = 0; col < this.board.getCol(); col++) {
                Tuple tile = new Tuple(row, col);

                //brute force
                //check up
                HashSet<Tuple> up = checkUp(tile, this.board.getBoard());
                if (up.size() + 1 >= 3) {
                    removableTiles.addAll(up);
                    removableTiles.add(tile); //add the current tile because it is not included in the array list
                }

                //check right
                HashSet<Tuple> right = checkRight(tile, this.board.getBoard(), this.board.getCol());
                if (right.size() + 1 >= 3) {
                    removableTiles.addAll(right);

                    if (!removableTiles.contains(tile)) {
                        removableTiles.add(tile);
                    }
                }
            }
        }

        return removableTiles;
    }
}
