package BejeweledMain;

import GameLogic.Move;
import GameLogic.Tuple;
import Main.Board;
import Manager.ScoreManager;
import Manager.TurnManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class BejeweledMove extends Move { // need to import Move

    private Board board;
    public ScoreManager scoreManager;
    public TurnManager turnManager;
    private int score = 0;
    private String[][] newBoard;



    public BejeweledMove() {
        super();
    }

    public BejeweledMove(Board board, ScoreManager scoreManager, TurnManager turnManager) {
        this.board = board;
        this.scoreManager = scoreManager;
        this.turnManager = turnManager;
    }


    private Boolean isValidMove(int row, int col, String direction) {
        final ArrayList<String> availableDirections = new ArrayList<String>() {{
            add("left");
            add("right");
            add("up");
            add("down");
        }};

        //if not within boundary and not an available direction, return false
        if (row < 0 || row >= this.board.getRow() || col < 0 || col >= this.board.getCol() ||
                !availableDirections.contains(direction.toLowerCase())) {
            return false;
        }
        return true;
    }

    public void makeMove(int targetScore, BejeweledTimer timer) {

        //set score back to 0 for player 2
        score = 0;

        //ensures that all the updates are made and still has moves to make
        ArrayList<Tuple> list = findAllMatchesAfterUpdate();
        while (list.size() >= 3 || !hasMovesToMake()) {
            if(list.size() < 3 && !hasMovesToMake()) {

            }
            this.board.updateBoard(list);
            while (!hasMovesToMake()) {
                this.board.populateBoard();
            }
            list = findAllMatchesAfterUpdate();
        }
        boolean run = true;
        while (run) // if no more matches, game ends
        {

            if (score >= targetScore){
                System.out.println("CONGRATS: you successfully achieved the target score!!!. Your final score is " + score);
                if (turnManager.getPlayerTurn() == 0) //player one
                {
                    scoreManager.addToCurrentP1Score(score);
                } else {
                    scoreManager.addToCurrentP2Score(score);
                }
                return; //return so that the Game Over message at the bottom doesn't get printed;
            }
            System.out.println("\nTime Left: " + (timer.getRunTime()));
            System.out.println("Current Score: " + score);
            this.board.print();
            int row = 0;
            int col = 0;
            String direction;

            Scanner input = new Scanner(System.in);

            String userInput;
            System.out.print("Enter the row of the position: ");
            userInput = input.nextLine();
            if(BejeweledTimer.getInstance().getRunTime() <= 0){
                run = false;
                break;
            }
            else{
                row = Integer.parseInt(userInput);
            }
            input = new Scanner(System.in);
            System.out.print("Enter the col of the position: ");
            userInput = input.nextLine();

            if(BejeweledTimer.getInstance().getRunTime() <= 0){
                run = false;
                break;
            }
            else{
                col = Integer.parseInt(userInput);
            }
            input = new Scanner(System.in);
            System.out.print("Switch with (left, right, up, down): ");
            direction = input.nextLine();
            if(BejeweledTimer.getInstance().getRunTime() <= 0){
                run = false;
                break;
            }


            //ensures that it is a valid move
            if (this.isValidMove(row, col, direction)) {
                ArrayList<Tuple> removableTiles;
                if (direction.equals("left")) {
                    removableTiles = getRemovableTilesSwitchingLeft(row, col);

                } else if (direction.equals("right")) {
                    removableTiles = getRemovableTilesSwitchingRight(row, col);
                } else if (direction.equals("up")) {
                    removableTiles = getRemovableTilesSwitchingUp(row, col);
                } else { //down
                    removableTiles = getRemovableTilesSwitchingDown(row, col);
                }

                HashSet<Tuple> removeDuplicates = new HashSet<Tuple>(removableTiles);
                if (removeDuplicates.size() >= 3) {
                    //board should remove all these Tuple pairs and generate new tiles onto the board
                    score += removeDuplicates.size();
                    this.board.setBoard(this.newBoard);
                    this.board.updateBoard(removableTiles);

                    //after update, should check if there is anymore matches formed
                    while (true) {
                        //start checking from bottom
                        removableTiles = findAllMatchesAfterUpdate();
                        removeDuplicates = new HashSet<Tuple>(removableTiles);
                        //if no more matches, break
                        if (removeDuplicates.size() < 3) {
                            break;
                        }

                        score += removeDuplicates.size();
                        //else update the board
                        this.board.setBoard(this.newBoard);
                        this.board.updateBoard(removableTiles);
//                        this.board.print();
                    }
                }
                else
                {
                    System.out.println("No Matches Found");
                }
            }
            else
            {
                System.out.println("Invalid Move. Try again.");
            }
            //if not a valid move, nothing changes and prompt user for input again

            //need to check if there is possible matches to be make
            if (!hasMovesToMake()) {
                if (turnManager.getPlayerTurn() == 0) //player one
                {
                    scoreManager.addToCurrentP1Score(score);
                } else {
                    scoreManager.addToCurrentP2Score(score);
                }

                break;
            }

//            break;
            //if not, break out the while loop
        }
        if (turnManager.getPlayerTurn() == 0) //player one
        {
            scoreManager.addToCurrentP1Score(score);
        } else {
            scoreManager.addToCurrentP2Score(score);
        }
        //prepare board for player 2
        System.out.println("GAME OVER: No more available moves to make. Failed to achieve target score.");
        BejeweledTimer.getInstance().stopTimer();
        return;
    }

    public boolean hasMovesToMake() {
        //start from bottom left corner
        for (int row = this.board.getRow() - 1; row >= 0; row--) {
            for (int col = 0; col < this.board.getCol(); col++) {
                ArrayList<Tuple> switchLeft = getRemovableTilesSwitchingLeft(row, col);
                ArrayList<Tuple> switchRight = getRemovableTilesSwitchingRight(row, col);

                ArrayList<Tuple> switchUp = getRemovableTilesSwitchingUp(row, col);
                ArrayList<Tuple> switchDown = getRemovableTilesSwitchingDown(row, col);

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

    private ArrayList<Tuple> getRemovableTilesSwitchingLeft(int row, int col) {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();

        Tuple leftTile = new Tuple(row, col - 1);

        if (leftTile.col >= 0)//left tile not out of bound
        {
            String[][] boardCopy = this.board.getBoard();
            //switch
            String val = boardCopy[row][col];
            boardCopy[row][col] = boardCopy[row][col - 1];
            boardCopy[row][col - 1] = val;

            ArrayList<Tuple> leftUp = checkUp(leftTile, boardCopy);
            ArrayList<Tuple> leftDown = checkDown(leftTile, boardCopy);

            if (leftUp.size() + leftDown.size() + 1 >= 3) {
                removableTiles.addAll(leftUp);
                removableTiles.addAll(leftDown);
                removableTiles.add(leftTile);
            }

            ArrayList<Tuple> leftLeft = checkLeft(leftTile, boardCopy);
            if (leftLeft.size() + 1 >= 3) {
                removableTiles.addAll(leftLeft);
                if (!removableTiles.contains(leftTile)) {
                    removableTiles.add(leftTile);
                }
            }

            //right tile
            //check in here because if left tile does not exist, cannot switch
            Tuple rightTile = new Tuple(row, col);
            ArrayList<Tuple> rightUp = checkUp(rightTile, boardCopy);
            ArrayList<Tuple> rightDown = checkDown(rightTile, boardCopy);

            if (rightUp.size() + rightDown.size() + 1 >= 3) {
                removableTiles.addAll(rightUp);
                removableTiles.addAll(rightDown);
                removableTiles.add(rightTile);
            }

            ArrayList<Tuple> rightRight = checkRight(rightTile, boardCopy);
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

    private ArrayList<Tuple> getRemovableTilesSwitchingRight(int row, int col) {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();

        Tuple rightTile = new Tuple(row, col + 1);

        if (rightTile.col < this.board.getCol())//right tile not out of bound
        {
            String[][] boardCopy = this.board.getBoard();
            //switch
            String val = boardCopy[row][col];
            boardCopy[row][col] = boardCopy[row][col + 1];
            boardCopy[row][col + 1] = val;

            //right tile
            ArrayList<Tuple> rightUp = checkUp(rightTile, boardCopy);
            ArrayList<Tuple> rightDown = checkDown(rightTile, boardCopy);

            if (rightUp.size() + rightDown.size() + 1 >= 3) {
                removableTiles.addAll(rightUp);
                removableTiles.addAll(rightDown);
                removableTiles.add(rightTile);
            }

            ArrayList<Tuple> rightRight = checkRight(rightTile, boardCopy);
            if (rightRight.size() + 1 >= 3) {
                removableTiles.addAll(rightRight);
                if (!removableTiles.contains(rightTile)) {
                    removableTiles.add(rightTile);
                }
            }

            //left tile
            //check in here because if right tile does not exist, cannot switch
            Tuple leftTile = new Tuple(row, col);
            ArrayList<Tuple> leftUp = checkUp(leftTile, boardCopy);
            ArrayList<Tuple> leftDown = checkDown(leftTile, boardCopy);

            if (leftUp.size() + leftDown.size() + 1 >= 3) {
                removableTiles.addAll(leftUp);
                removableTiles.addAll(leftDown);
                removableTiles.add(leftTile);
            }

            ArrayList<Tuple> leftLeft = checkLeft(leftTile, boardCopy);
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
        return removableTiles;
    }

    private ArrayList<Tuple> getRemovableTilesSwitchingUp(int row, int col) {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();

        Tuple topTile = new Tuple(row - 1, col);

        if (topTile.row >= 0)//up tile not out of bound
        {
            String[][] boardCopy = this.board.getBoard();
            //switch
            String val = boardCopy[row][col];
            boardCopy[row][col] = boardCopy[row - 1][col];
            boardCopy[row - 1][col] = val;

            //up tile
            ArrayList<Tuple> topLeft = checkLeft(topTile, boardCopy);
            ArrayList<Tuple> topRight = checkRight(topTile, boardCopy);

            if (topLeft.size() + topRight.size() + 1 >= 3) {
                removableTiles.addAll(topLeft);
                removableTiles.addAll(topRight);
                removableTiles.add(topTile);
            }

            ArrayList<Tuple> topUp = checkUp(topTile, boardCopy);
            if (topUp.size() + 1 >= 3) {
                removableTiles.addAll(topUp);
                if (!removableTiles.contains(topTile)) {
                    removableTiles.add(topTile);
                }
            }

            //down tile
            //check in here because if up tile does not exist, cannot switch

            Tuple bottomTile = new Tuple(row, col);
            ArrayList<Tuple> bottomLeft = checkLeft(bottomTile, boardCopy);
            ArrayList<Tuple> bottomRight = checkRight(bottomTile, boardCopy);

            if (bottomLeft.size() + bottomRight.size() + 1 >= 3) {
                removableTiles.addAll(bottomLeft);
                removableTiles.addAll(bottomRight);
                removableTiles.add(bottomTile);
            }

            ArrayList<Tuple> bottomDown = checkDown(bottomTile, boardCopy);
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
        return removableTiles;
    }

    private ArrayList<Tuple> getRemovableTilesSwitchingDown(int row, int col) {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();

        Tuple bottomTile = new Tuple(row + 1, col);

        if (bottomTile.row < this.board.getRow())//bottom tile not out of bound
        {
            String[][] boardCopy = this.board.getBoard();
            //switch
            String val = boardCopy[row][col];
            boardCopy[row][col] = boardCopy[row + 1][col];
            boardCopy[row + 1][col] = val;

            //down tile
            ArrayList<Tuple> bottomLeft = checkLeft(bottomTile, boardCopy);
            ArrayList<Tuple> bottomRight = checkRight(bottomTile, boardCopy);

            if (bottomLeft.size() + bottomRight.size() + 1 >= 3) {
                removableTiles.addAll(bottomLeft);
                removableTiles.addAll(bottomRight);
                removableTiles.add(bottomTile);
            }

            ArrayList<Tuple> bottomDown = checkDown(bottomTile, boardCopy);
            if (bottomDown.size() + 1 >= 3) {
                removableTiles.addAll(bottomDown);
                if (!removableTiles.contains(bottomTile)) {
                    removableTiles.add(bottomTile);
                }
            }

            //up tile
            //check in here because if bottom tile does not exist, cannot switch
            Tuple topTile = new Tuple(row, col);
            ArrayList<Tuple> topLeft = checkLeft(topTile, boardCopy);
            ArrayList<Tuple> topRight = checkRight(topTile, boardCopy);

            if (topLeft.size() + topRight.size() + 1 >= 3) {
                removableTiles.addAll(topLeft);
                removableTiles.addAll(topRight);
                removableTiles.add(topTile);
            }

            ArrayList<Tuple> topUp = checkUp(topTile, boardCopy);
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
        return removableTiles;
    }

    protected ArrayList<Tuple> checkUp(Tuple tile, String[][] boardCopy) {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();

        int comparingRow = tile.row - 1;
        String currentTile = boardCopy[tile.row][tile.col];
        while (comparingRow >= 0 && boardCopy[comparingRow][tile.col].equals(currentTile)) {
            Tuple t = new Tuple(comparingRow, tile.col);
            removableTiles.add(t);
            comparingRow -= 1;
        }
        return removableTiles;
    }

    private ArrayList<Tuple> checkDown(Tuple tile, String[][] boardCopy) {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();

        int comparingRow = tile.row + 1;
        String currentTile = boardCopy[tile.row][tile.col];

        while (comparingRow < this.board.getRow() && boardCopy[comparingRow][tile.col].equals(currentTile)) {
            Tuple t = new Tuple(comparingRow, tile.col);
            removableTiles.add(t);
            comparingRow += 1;
        }

        return removableTiles;
    }

    protected ArrayList<Tuple> checkLeft(Tuple tile, String[][] boardCopy) {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();

        int comparingCol = tile.col - 1;
        String currentTile = boardCopy[tile.row][tile.col];

        while (comparingCol >= 0 && boardCopy[tile.row][comparingCol].equals(currentTile)) {
            Tuple t = new Tuple(tile.row, comparingCol);
            removableTiles.add(t);
            comparingCol -= 1;
        }

        return removableTiles;
    }

    private ArrayList<Tuple> checkRight(Tuple tile, String[][] boardCopy) {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();

        int comparingCol = tile.col + 1;
        String currentTile = boardCopy[tile.row][tile.col];
        while (comparingCol < this.board.getCol() && boardCopy[tile.row][comparingCol].equals(currentTile)) {
            Tuple t = new Tuple(tile.row, comparingCol);
            removableTiles.add(t);
            comparingCol += 1;
        }
        return removableTiles;
    }

    public ArrayList<Tuple> findAllMatchesAfterUpdate() {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();

        //checking from bottom
        //starting from bottom left corner
        for (int row = this.board.getRow() - 1; row >= 0; row--) {
            for (int col = 0; col < this.board.getCol(); col++) {
                Tuple tile = new Tuple(row, col);

                //brute force
                //check up
                ArrayList<Tuple> up = checkUp(tile, this.board.getBoard());
                if (up.size() + 1 >= 3) {
                    removableTiles.addAll(up);
                    removableTiles.add(tile); //add the current tile because it is not included in the array list
                }

                //check right
                ArrayList<Tuple> right = checkRight(tile, this.board.getBoard());
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