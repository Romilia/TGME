package CandyCrushMain;

import Main.Board;
import GameLogic.Move;
import Main.Board;
import Manager.ScoreManager;
import Manager.TurnManager;
import GameLogic.Tuple;

import java.util.*;

public class CandyCrushMove extends Move{
    private Board board;
    public ScoreManager scoreManager;
    public TurnManager turnManager;
    private int score = 0;
    private String[][] newBoard;
    private Tuple hint;

    public CandyCrushMove(Board board, ScoreManager scoreManager, TurnManager turnManager) {
        super(); //so i can use functions like check up,down,left,right
        this.board = board;
        this.scoreManager = scoreManager;
        this.turnManager = turnManager;
    }

    public boolean hasMovesToMake() {
        //start from bottom left corner
        for (int row = this.board.getRow() - 1; row >= 0; row--) {
            for (int col = 0; col < this.board.getCol(); col++) {
                //if the current tile is a special candy, then it has a move
                if(this.board.getBoard()[row][col].equals("!") || this.board.getBoard()[row][col].equals("*") || this.board.getBoard()[row][col].equals("#"))
                {
                    this.hint = new Tuple(row,col);
                    return true;
                }

                //if the tile that the current tile is switching with is a special candy, then it has a move
                //check left
                if(0 <= col-1 && (this.board.getBoard()[row][col-1].equals("!") || this.board.getBoard()[row][col-1].equals("*") || this.board.getBoard()[row][col-1].equals("#")))
                {
                    this.hint = new Tuple(row,col);
                    return true;
                }

                //check right
                if(col+1 < this.board.getCol() && (this.board.getBoard()[row][col+1].equals("!") || this.board.getBoard()[row][col+1].equals("*") || this.board.getBoard()[row][col+1].equals("#")))
                {
                    this.hint = new Tuple(row,col);
                    return true;
                }

                //check up
                if(0 <= row-1 && (this.board.getBoard()[row-1][col].equals("!") || this.board.getBoard()[row-1][col].equals("*") || this.board.getBoard()[row-1][col].equals("#")))
                {
                    this.hint = new Tuple(row,col);
                    return true;
                }

                //check down
                if(row+1 < this.board.getRow() && (this.board.getBoard()[row+1][col].equals("!") || this.board.getBoard()[row+1][col].equals("*") || this.board.getBoard()[row+1][col].equals("#")))
                {
                    this.hint = new Tuple(row,col);
                    return true;
                }

                HashSet<Tuple> switchLeft = this.getRemovableTilesSwitchingLeft(row, col);
                HashSet<Tuple> switchRight = this.getRemovableTilesSwitchingRight(row, col);

                HashSet<Tuple> switchUp = this.getRemovableTilesSwitchingUp(row, col);
                HashSet<Tuple> switchDown = this.getRemovableTilesSwitchingDown(row, col);

                if (switchLeft.size() >= 3
                        || switchRight.size() >= 3
                        || switchUp.size() >= 3
                        || switchDown.size() >= 3) {
                    this.hint = new Tuple(row,col);
                    return true;
                }
            }
        }

        return false;
    }

    public void makeMove(int hintLeft, int numOfMoves, int targetScore) {
        //set score back to 0 for player 2
        this.score = 0;
        hintLeft = 3;
        //ensures that all the updates are made and still has moves to make
        HashSet<Tuple> list = findAllMatchesAfterUpdate();
        while (list.size() >= 3 || !hasMovesToMake()) {
            this.board.updateBoard(list);
            while (!hasMovesToMake()) {
                this.board.populateBoard();
            }
            list = findAllMatchesAfterUpdate();
        }

        while (numOfMoves > 0 && this.score < targetScore) // if no moves left or score reached, game ends
        {
            System.out.println("\nMoves left: " + numOfMoves);
            System.out.println("Current Score: " + score);
            System.out.println("Hint:" + hintLeft);

            this.board.print();

            Scanner input = new Scanner(System.in);
            System.out.println("Do you want to use the hint?(Yes/No):");
            String decision = input.nextLine();
            if(decision.toLowerCase().equals("yes")) {
                if(hintLeft > 0) {
                    System.out.println("hint:" + this.hint.row + " " + this.hint.col);
                    hintLeft -= 1;
                }
                else
                {
                    System.out.println("You have no more hint.");
                }
            }

            int row = super.promptRow();
            int col = super.promptCol();
            String direction = super.promptDirection();

            //ensures that it is a valid move
            if (super.isValidMove(row, col, direction, this.board.getRow(), this.board.getCol())) {
                HashSet<Tuple> removableTiles;
                if (direction.equals("left")) {
                    removableTiles = this.getRemovableTilesSwitchingLeft(row, col);

                } else if (direction.equals("right")) {
                    removableTiles = this.getRemovableTilesSwitchingRight(row, col);
                } else if (direction.equals("up")) {
                    removableTiles = this.getRemovableTilesSwitchingUp(row, col);
                } else { //down
                    removableTiles = this.getRemovableTilesSwitchingDown(row, col);
                }

                if (removableTiles.size() >= 3) {
                    //board should remove all these Tuple pairs and generate new tiles onto the board
                    this.score += removableTiles.size();
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
                } else {
                    System.out.println("No Matches Found");
                }
            } else {
                System.out.println("Invalid Move. Try again.");
            }
            //if not a valid move, nothing changes and prompt user for input again
            numOfMoves--;

            //need to check if there is possible matches to be make
            //if no more moves to make and there is # of moves left, generate new board
            if (!hasMovesToMake() && numOfMoves > 0) {
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

        if(score >= targetScore)
        {
            System.out.println("CONGRATS: you successfully achieved the target score!!!\n");
        }
        else
        {
            System.out.println("GAME OVER: No more available moves to make. Failed to achieve target score.");
        }
    }

    //need to check for bomb, rocket, and chocolate sprinkle candies
    private HashSet<Tuple> getRemovableTilesSwitchingLeft(int row, int col)
    {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();

        Tuple leftTile = new Tuple(row,col-1);
        Tuple rightTile = new Tuple(row, col);

        if(leftTile.col >= 0)//left tile not out of bound
        {
            String[][] boardCopy = this.board.getBoard();
            //switch
            String val = boardCopy[row][col];
            boardCopy[row][col] = boardCopy[row][col-1];
            boardCopy[row][col-1] = val;

            //if it is switching with a special tile
            if(this.board.getBoard()[leftTile.row][leftTile.col].equals("*")
                    ||this.board.getBoard()[rightTile.row][rightTile.col].equals("*"))
            {
                removableTiles.addAll(chocolateSprinkleEffect(leftTile,rightTile));
            }
            else if(this.board.getBoard()[leftTile.row][leftTile.col].equals("!")
                    ||this.board.getBoard()[rightTile.row][rightTile.col].equals("!"))
            {
                //bomb
                removableTiles.addAll(bombEffect(leftTile,rightTile,boardCopy));
            }
            else if(this.board.getBoard()[leftTile.row][leftTile.col].equals("#")
                    || this.board.getBoard()[rightTile.row][rightTile.col].equals("#"))
            {
                //rocket
                removableTiles.addAll(rocketEffect(leftTile,"left"));
            }
            else {
                HashSet<Tuple> leftUp = super.checkUp(leftTile, boardCopy);
                HashSet<Tuple> leftDown = super.checkDown(leftTile, boardCopy, this.board.getRow());
                HashSet<Tuple> leftLeft = super.checkLeft(leftTile, boardCopy);

                removableTiles.addAll(this.getRemovableTiles(leftTile, boardCopy, leftUp, leftDown, leftLeft));

                //right tile
                //check in here because if left tile does not exist, cannot switch
                HashSet<Tuple> rightUp = super.checkUp(rightTile, boardCopy);
                HashSet<Tuple> rightDown = super.checkDown(rightTile, boardCopy, this.board.getRow());
                HashSet<Tuple> rightRight = super.checkRight(rightTile, boardCopy, this.board.getCol());

                removableTiles.addAll(this.getRemovableTiles(rightTile, boardCopy, rightUp, rightDown, rightRight));
            }
            if(removableTiles.size() >= 3)
            {
                this.newBoard = boardCopy;
            }
        }

        return removableTiles;
    }

    private HashSet<Tuple> chocolateSprinkleEffect(Tuple selectedTile, Tuple switchWithTile)
    {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();
        boolean twoChocolate = false;
        // if both tiles are chocolate sprinkles (*), remove everything on board
        if(this.board.getBoard()[switchWithTile.row][switchWithTile.col].equals("*"))
        {
            if(this.board.getBoard()[selectedTile.row][selectedTile.col].equals("*"))
            {
                twoChocolate = true;
            }
            removableTiles.add(selectedTile);

            //if only switchWithTile is *, then need to remove all the tiles same as selectedTile
        }
        else
        {
            //if only selectedTile is *, then need to remove all the tiles same as switchWithTile
            selectedTile = switchWithTile;
            removableTiles.add(switchWithTile);
        }

        for(int row = 0 ; row < this.board.getRow(); row++)
        {
            for(int col = 0; col < this.board.getCol();col++)
            {
                if(twoChocolate)
                {
                    removableTiles.add(new Tuple(row, col));
                }
                else {
                    if (this.board.getBoard()[row][col].equals(this.board.getBoard()[selectedTile.row][selectedTile.col])) {
                        removableTiles.add(new Tuple(row, col));
                    }
                }
            }
        }

        return removableTiles;
    }

    // used in bombEffect function
    private void addTiles(Tuple tile,HashSet<Tuple> removableTiles)
    {
        //add itself
        removableTiles.add(tile);
        //up row
        if(0 <= tile.row-1)
        {
            //top tile
            removableTiles.add(new Tuple(tile.row-1,tile.col));

            if(0 <= tile.col-1)
            {
                //top left
                removableTiles.add(new Tuple(tile.row-1,tile.col-1));
            }

            if(tile.col+1 < this.board.getCol())
            {
                //top right
                removableTiles.add(new Tuple(tile.row-1,tile.col+1));
            }
        }

        if(0 <= tile.col-1)
        {
            //current left
            removableTiles.add(new Tuple(tile.row,tile.col-1));
        }

        if(tile.col+1 < this.board.getCol())
        {
            //current right
            removableTiles.add(new Tuple(tile.row,tile.col+1));
        }

        //bottom row
        if(tile.row+1 < this.board.getRow())
        {
            //bottom tile
            removableTiles.add(new Tuple(tile.row+1,tile.col));

            if(0 <= tile.col-1)
            {
                //bottom left
                removableTiles.add(new Tuple(tile.row+1,tile.col-1));
            }

            if(tile.col+1 < this.board.getCol())
            {
                //bottom right
                removableTiles.add(new Tuple(tile.row+1,tile.col+1));
            }
        }
    }
    private HashSet<Tuple> bombEffect(Tuple selectedTile, Tuple switchWithTile, String[][] boardCopy)
    {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();
        if(boardCopy[selectedTile.row][selectedTile.col].equals("!"))
        {
            addTiles(selectedTile,removableTiles);
        }

        if(boardCopy[switchWithTile.row][switchWithTile.col].equals("!"))
        {
            addTiles(switchWithTile,removableTiles);
        }

        return removableTiles;
    }

    private HashSet<Tuple> rocketEffect(Tuple tile, String direction)
    {
        HashSet<Tuple> removableTiles = new HashSet<>();
        if(direction.equals("up") || direction.equals("down"))
        {
            for(int row = 0; row < this.board.getRow(); row++)
            {
                Tuple t = new Tuple(row,tile.col);
                removableTiles.add(t);
            }
        }
        else if(direction.equals("left") || direction.equals("right"))
        {
            for(int col = 0; col < this.board.getCol();col++)
            {
                Tuple t = new Tuple(tile.row,col);
                removableTiles.add(t);
            }
        }

//        findSpecialCandies(removableTiles);
        return removableTiles;
    }

    private void findSpecialCandies(HashSet<Tuple> removableTiles)
    {
        int num = 0;
        ArrayList<Tuple> arr = new ArrayList<Tuple>(removableTiles);
        while(num < arr.size()){
            HashSet<Tuple> newTiles = new HashSet<Tuple>();

            //if this is a special candy, let it take effect
            if(this.board.getBoard()[arr.get(num).row][arr.get(num).col].equals("!") || this.board.getBoard()[arr.get(num).row][arr.get(num).col].equals("*") || this.board.getBoard()[arr.get(num).row][arr.get(num).col].equals("#")) {
                if (this.board.getBoard()[arr.get(num).row][arr.get(num).col].equals("!")) {
                    newTiles = this.bombEffect(arr.get(num), arr.get(num), this.board.getBoard());

                } else if (this.board.getBoard()[arr.get(num).row][arr.get(num).col].equals("*")) {
                    Random rand = new Random();
                    ArrayList<Tuple> directions = getDirections(arr.get(num));
                    int upperbound = directions.size();
                    int int_rand = rand.nextInt(upperbound);
                    newTiles = this.chocolateSprinkleEffect(arr.get(num), directions.get(int_rand));

                } else if (this.board.getBoard()[arr.get(num).row][arr.get(num).col].equals("#")) {
                    ArrayList<String> directions = new ArrayList<String>(Arrays.asList("up", "down", "left", "right"));
                    Random rand = new Random();
                    int upperbound = 4;
                    int int_rand = rand.nextInt(upperbound);
                    newTiles = this.rocketEffect(arr.get(num), directions.get(int_rand));
                }
            }

            //if the newtiles contains all the tiles, add everything to removable and break
            if(newTiles.size() == this.board.getCol()*this.board.getRow())
            {
                removableTiles.addAll(newTiles);
                break;
            }

            //loop over the newTiles to see if it is contained in removableTiles
            for(Tuple nt: newTiles)
            {
                //if not contained, add to removableTiles
                if(!removableTiles.contains(nt))
                {
                    removableTiles.add(nt);
                    arr.add(nt);
                }
            }
            num +=1;
        }
    }

    private ArrayList<Tuple> getDirections(Tuple t)
    {
        ArrayList<Tuple> directions = new ArrayList<Tuple>();
        if(0 <= t.row-1)
        {
            Tuple newT = new Tuple(t.row-1,t.col);
            directions.add(newT);
        }
        if(t.row+1 < this.board.getRow())
        {
            Tuple newT = new Tuple(t.row+1,t.col);
            directions.add(newT);
        }
        if(0<=t.col-1)
        {
            Tuple newT = new Tuple(t.row,t.col-1);
            directions.add(newT);
        }
        if(t.col+1 < this.board.getCol())
        {
            Tuple newT = new Tuple(t.row,t.col+1);
            directions.add(newT);
        }
        return directions;
    }

    private HashSet<Tuple> getRemovableTilesSwitchingRight(int row, int col)
    {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();

        Tuple rightTile = new Tuple(row,col+1);
        Tuple leftTile = new Tuple(row, col);

        if(rightTile.col < this.board.getCol())//right tile not out of bound
        {
            String[][] boardCopy = this.board.getBoard();
            //switch
            String val = boardCopy[row][col];
            boardCopy[row][col] = boardCopy[row][col+1];
            boardCopy[row][col+1] = val;

            if(this.board.getBoard()[leftTile.row][leftTile.col].equals("*")
                    ||this.board.getBoard()[rightTile.row][rightTile.col].equals("*"))
            {
                removableTiles.addAll(chocolateSprinkleEffect(leftTile,rightTile));
            }
            else if(this.board.getBoard()[leftTile.row][leftTile.col].equals("!")
                    ||this.board.getBoard()[rightTile.row][rightTile.col].equals("!"))
            {
                //bomb
                removableTiles.addAll(bombEffect(leftTile,rightTile,boardCopy));
            }
            else if(this.board.getBoard()[leftTile.row][leftTile.col].equals("#")
                    || this.board.getBoard()[rightTile.row][rightTile.col].equals("#"))
            {
                //rocket
                removableTiles.addAll(rocketEffect(rightTile,"right"));
            }
            else {
                //right tile
                HashSet<Tuple> rightUp = super.checkUp(rightTile, boardCopy);
                HashSet<Tuple> rightDown = super.checkDown(rightTile, boardCopy, this.board.getRow());
                HashSet<Tuple> rightRight = super.checkRight(rightTile, boardCopy, this.board.getCol());

                removableTiles.addAll(this.getRemovableTiles(rightTile, boardCopy, rightUp, rightDown, rightRight));

                //left tile
                //check in here because if right tile does not exist, cannot switch
                HashSet<Tuple> leftUp = super.checkUp(leftTile, boardCopy);
                HashSet<Tuple> leftDown = super.checkDown(leftTile, boardCopy, this.board.getRow());
                HashSet<Tuple> leftLeft = super.checkLeft(leftTile, boardCopy);

                removableTiles.addAll(this.getRemovableTiles(leftTile, boardCopy, leftUp, leftDown, leftLeft));
            }
            if(removableTiles.size() >= 3)
            {
                this.newBoard = boardCopy;
            }
        }

        return removableTiles;
    }

    private HashSet<Tuple> getRemovableTilesSwitchingUp(int row, int col)
    {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();

        Tuple topTile = new Tuple(row-1,col);
        Tuple bottomTile = new Tuple(row,col);

        if(topTile.row >= 0)//up tile not out of bound
        {
            String[][] boardCopy = this.board.getBoard();
            //switch
            String val = boardCopy[row][col];
            boardCopy[row][col] = boardCopy[row-1][col];
            boardCopy[row-1][col] = val;

            if(this.board.getBoard()[topTile.row][topTile.col].equals("*")
                    ||this.board.getBoard()[bottomTile.row][bottomTile.col].equals("*"))
            {
                removableTiles.addAll(chocolateSprinkleEffect(topTile,bottomTile));
            }
            else if(this.board.getBoard()[topTile.row][topTile.col].equals("!")
                    ||this.board.getBoard()[bottomTile.row][bottomTile.col].equals("!"))
            {
                //bomb
                removableTiles.addAll(bombEffect(topTile,bottomTile,boardCopy));
            }
            else if(this.board.getBoard()[topTile.row][topTile.col].equals("#")
                    || this.board.getBoard()[bottomTile.row][bottomTile.col].equals("#"))
            {
                //rocket
                removableTiles.addAll(rocketEffect(topTile,"up"));
            }
            else {
                //up tile
                HashSet<Tuple> topLeft = super.checkLeft(topTile, boardCopy);
                HashSet<Tuple> topRight = super.checkRight(topTile, boardCopy, this.board.getCol());
                HashSet<Tuple> topUp = super.checkUp(topTile, boardCopy);

                removableTiles.addAll(this.getRemovableTiles(topTile, boardCopy, topLeft, topRight, topUp));

                //down tile
                //check in here because if up tile does not exist, cannot switch

                HashSet<Tuple> bottomLeft = super.checkLeft(bottomTile, boardCopy);
                HashSet<Tuple> bottomRight = super.checkRight(bottomTile, boardCopy, this.board.getCol());
                HashSet<Tuple> bottomDown = super.checkDown(bottomTile, boardCopy, this.board.getRow());

                removableTiles.addAll(this.getRemovableTiles(bottomTile, boardCopy, bottomLeft, bottomRight, bottomDown));
            }
            if(removableTiles.size() >= 3)
            {
                this.newBoard = boardCopy;
            }
        }

        return removableTiles;
    }

    private HashSet<Tuple> getRemovableTilesSwitchingDown(int row, int col)
    {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();

        Tuple bottomTile = new Tuple(row+1,col);
        Tuple topTile = new Tuple(row, col);

        if(bottomTile.row < this.board.getRow())//bottom tile not out of bound
        {
            String[][] boardCopy = this.board.getBoard();
            //switch
            String val = boardCopy[row][col];
            boardCopy[row][col] = boardCopy[row+1][col];
            boardCopy[row+1][col] = val;

            if(this.board.getBoard()[topTile.row][topTile.col].equals("*")
                    ||this.board.getBoard()[bottomTile.row][bottomTile.col].equals("*"))
            {
                removableTiles.addAll(chocolateSprinkleEffect(topTile,bottomTile));
            }
            else if(this.board.getBoard()[topTile.row][topTile.col].equals("!")
                    ||this.board.getBoard()[bottomTile.row][bottomTile.col].equals("!"))
            {
                //bomb
                removableTiles.addAll(bombEffect(topTile,bottomTile,boardCopy));
            }
            else if(this.board.getBoard()[topTile.row][topTile.col].equals("#")
                    || this.board.getBoard()[bottomTile.row][bottomTile.col].equals("#"))
            {
                //rocket
                removableTiles.addAll(rocketEffect(topTile,"down"));
            }
            else {
                //down tile
                HashSet<Tuple> bottomLeft = super.checkLeft(bottomTile, boardCopy);
                HashSet<Tuple> bottomRight = super.checkRight(bottomTile, boardCopy, this.board.getCol());
                HashSet<Tuple> bottomDown = super.checkDown(bottomTile, boardCopy, this.board.getRow());

                removableTiles.addAll(this.getRemovableTiles(bottomTile, boardCopy, bottomLeft, bottomRight, bottomDown));
                //up tile
                //check in here because if bottom tile does not exist, cannot switch
                HashSet<Tuple> topLeft = super.checkLeft(topTile, boardCopy);
                HashSet<Tuple> topRight = super.checkRight(topTile, boardCopy, this.board.getCol());
                HashSet<Tuple> topUp = super.checkUp(topTile, boardCopy);

                removableTiles.addAll(this.getRemovableTiles(topTile, boardCopy, topLeft, topRight, topUp));
            }
            if(removableTiles.size() >= 3)
            {
                this.newBoard = boardCopy;
            }
        }

        return removableTiles;
    }

    private HashSet<Tuple> getRemovableTiles(Tuple tile, String[][] boardCopy,HashSet<Tuple> list1,HashSet<Tuple> list2,HashSet<Tuple> list3)
    {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();
        //R for rocket, B for bomb, C for chocolate sprinkle candy
        if(list1.size() + list2.size() + list3.size() + 1 >= 7 && list3.size() >= 2)
        {
            //chocolate sprinkle
            //set the current tile to chocolate sprinkle

            removableTiles.addAll(list1);
            removableTiles.addAll(list2);
            removableTiles.addAll(list3);
            boardCopy[tile.row][tile.col] = "*";
        }
        else if(5 <= list1.size() + list2.size() + list3.size() + 1 &&
                list1.size() + list2.size() + list3.size() + 1 <= 6 &&
                (list3.size() == 2 || list3.size() == 0))
        {
            //it is a bomb
            //set the current tile to bomb

            removableTiles.addAll(list1);
            removableTiles.addAll(list2);
            removableTiles.addAll(list3);
            boardCopy[tile.row][tile.col] = "!";
        }
        else if((5 == list1.size() + list2.size() + list3.size() + 1 && list3.size() == 1)
                || (list1.size() + list2.size() + list3.size() + 1 == 4 && list3.size() == 0))
        {
            //it is rocket
            //set the current tile to rocket
            //  B				  B					  B					 Y
            //B B Y      or     Y B B		or    B B B B      or    B B B B
            //  B                 B					  Y					 B
            //  B                 B
            removableTiles.addAll(list1);
            removableTiles.addAll(list2);
            boardCopy[tile.row][tile.col] = "#";
        }
        else if(list3.size() <= 1 && list1.size() + list2.size() + 1 == 3)
        {
            //regular
            removableTiles.addAll(list1);
            removableTiles.addAll(list2);
            removableTiles.add(tile);
        }
        else if(list1.size()==0 && list2.size()==0 && list3.size() == 2)
        {
            //regular
            removableTiles.addAll(list3);
            removableTiles.add(tile);
        }
        return removableTiles;
    }

    public HashSet<Tuple> findAllMatchesAfterUpdate()
    {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();
        String[][] visited = new String[this.board.getRow()][this.board.getCol()];
        String[][] boardCopy = this.board.getBoard();

        //starting from bottom left corner
        for(int row = this.board.getRow()-1; row >= 0; row--)
        {
            for(int col = 0; col < this.board.getCol(); col++)
            {
                Tuple tile = new Tuple(row,col);
                HashSet<Tuple> tilesFound = new HashSet<Tuple>();
                //if this tile has not been visited
                if(visited[row][col] == null)
                {
                    HashSet<Tuple> up = super.checkUp(tile,this.board.getBoard());
                    HashSet<Tuple> down = super.checkDown(tile,this.board.getBoard(),this.board.getRow());
                    HashSet<Tuple> left = super.checkLeft(tile,this.board.getBoard());
                    HashSet<Tuple> right = super.checkRight(tile,this.board.getBoard(),this.board.getCol());

                    if(7 <= up.size() + down.size() + left.size() + right.size() + 1)
                    {
                        //chocolate sprinkle candy
                        if(up.size()>= 2)
                        {
                            tilesFound.addAll(up);
                        }
                        if(down.size() >= 2)
                        {
                            tilesFound.addAll(down);
                        }
                        if(left.size() >= 2)
                        {
                            tilesFound.addAll(left);
                        }
                        if(right.size() >= 2)
                        {
                            tilesFound.addAll(right);
                        }
                        boardCopy[row][col] = "*";
                    }
                    else if(3 <= up.size() + down.size() + left.size() + right.size() + 1 )
                    {
                        //bomb, rock , regular matches handles in getRemovableTiles
                        if(up.size() == 0)
                        {
                            tilesFound = getRemovableTiles(tile,boardCopy,left,right,down);
                        }
                        else if(down.size() == 0)
                        {
                            tilesFound = getRemovableTiles(tile,boardCopy,left,right,up);
                        }
                        else if(left.size() == 0)
                        {
                            tilesFound = getRemovableTiles(tile,boardCopy,up,down,right);
                        }
                        else if(right.size() == 0)
                        {
                            tilesFound = getRemovableTiles(tile,boardCopy,up,down,left);
                        }

                        //special candy set in board copy done in getRemovableTiles
                    }

                    visited[row][col] = "visited";
                    for(Tuple t: tilesFound)
                    {
                        visited[t.row][t.col] = "visited";
                    }
                    this.newBoard = boardCopy;
                    removableTiles.addAll(tilesFound);
                }
            }
        }
        return removableTiles;
    }

}


