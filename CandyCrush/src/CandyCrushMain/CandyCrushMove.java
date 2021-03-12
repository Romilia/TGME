package CandyCrushMain;

import Main.Board;
import GameLogic.Move;
import Manager.ScoreManager;
import Manager.TurnManager;
import GameLogic.Tuple;

import java.util.*;

public class CandyCrushMove extends Move{
    private Board board;
    public ScoreManager scoreManager;
    public TurnManager turnManager;
    //    private int score = 0;
    private String[][] newBoard;
    private Tuple hint;
    private ArrayList<String> tiles;
    private ArrayList<String> symbols = new ArrayList<String>(Arrays.asList("!","#","*"));
    private ArrayList<String> directions = new ArrayList<String>(Arrays.asList("up",  "left"));
    //! is bomb, removes the neighbor tiles.
    //# is rocket, removes the whole column or row depending on the direction, if two rockets are switched, removes both the row and column with the switched tile.
    //* is chocolatesprinkle in the original candy crush game, which removes all the chosen tile, meaning if * switch with G, then all G tiles are removed.

    public CandyCrushMove(Board board, ScoreManager scoreManager, TurnManager turnManager, ArrayList<String> tiles) {
        super(); //to access functions like check up,down,left,right
        this.board = board;
        this.scoreManager = scoreManager;
        this.turnManager = turnManager;
        this.tiles = tiles;
    }

    public boolean hasMovesToMake() {
        //start from bottom left corner
        for (int row = this.board.getRow() - 1; row >= 0; row--) {
            for (int col = 0; col < this.board.getCol(); col++) {
                //if the current tile is a special candy, then it has a move
                if(this.symbols.contains(this.board.getBoard()[row][col]))
                {
                    this.hint = new Tuple(row,col);
                    return true;
                }

                //if the tile that the current tile is switching with is a special candy, then it has a move
                //check left
                if(0 <= col-1 && this.symbols.contains(this.board.getBoard()[row][col-1]))
                {
                    this.hint = new Tuple(row,col);
                    return true;
                }

                //check right
                if(col+1 < this.board.getCol() && this.symbols.contains(this.board.getBoard()[row][col+1]))
                {
                    this.hint = new Tuple(row,col);
                    return true;
                }

                //check up
                if(0 <= row-1 && this.symbols.contains(this.board.getBoard()[row-1][col]))
                {
                    this.hint = new Tuple(row,col);
                    return true;
                }

                //check down
                if(row+1 < this.board.getRow() && this.symbols.contains(this.board.getBoard()[row+1][col]))
                {
                    this.hint = new Tuple(row,col);
                    return true;
                }

                ArrayList<Tuple> switchLeft = this.getRemovableTilesSwitchingLeft(row, col);
                ArrayList<Tuple> switchRight = this.getRemovableTilesSwitchingRight(row, col);

                ArrayList<Tuple> switchUp = this.getRemovableTilesSwitchingUp(row, col);
                ArrayList<Tuple> switchDown = this.getRemovableTilesSwitchingDown(row, col);

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
        int score = 0;
        hintLeft = 3;
        //ensures that all the updates are made and still has moves to make
        ArrayList<Tuple> list = this.findAllMatchesAfterUpdate();
        while (list.size() >= 3 || !hasMovesToMake()) {
            this.board.updateBoard(list);
            while (!hasMovesToMake()) {
                this.board.populateBoard();
            }
            list = this.findAllMatchesAfterUpdate();
        }

        while (numOfMoves > 0 && score < targetScore) // if no moves left or score reached, game ends
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
                ArrayList<Tuple> removableTiles;
                if (direction.equals("left")) {
                    removableTiles = this.getRemovableTilesSwitchingLeft(row, col);

                } else if (direction.equals("right")) {
                    removableTiles = this.getRemovableTilesSwitchingRight(row, col);

                } else if (direction.equals("up")) {
                    removableTiles = this.getRemovableTilesSwitchingUp(row, col);
                } else { //down
                    removableTiles = this.getRemovableTilesSwitchingDown(row, col);
                }

                HashSet<Tuple> removeDuplicates = new HashSet<Tuple>(removableTiles);
                removeDuplicates = new HashSet<Tuple>(removableTiles);
                if (removableTiles.size() >= 3) {
                    //board should remove all these Tuple pairs and generate new tiles onto the board
                    score += removeDuplicates.size();
                    this.board.setBoard(this.newBoard);
                    this.board.updateBoard(removableTiles);

                    //after update, should check if there is anymore matches formed
                    while (true) {
                        //start checking from bottom
                        removableTiles = this.findAllMatchesAfterUpdate();
                        removeDuplicates = new HashSet<>(removableTiles);
//                        score += removeDuplicates.size();
                        //if no more matches, break
                        if (removeDuplicates.size() < 3) {
                            break;
                        }
                        score += removeDuplicates.size();
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
                list = this.findAllMatchesAfterUpdate();
                while (list.size() >= 3 || !hasMovesToMake()) {
                    this.board.updateBoard(list);
                    while (!hasMovesToMake()) {
                        this.board.populateBoard();
                    }
                    list = this.findAllMatchesAfterUpdate();
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
            System.out.println("CONGRATS: you successfully achieved the target score!!!. Your final score is " + score);
        }
        else
        {
            System.out.println("GAME OVER: No more available moves to make. Failed to achieve target score.");
        }
    }

    //need to check for bomb, rocket, and chocolate sprinkle candies
    private ArrayList<Tuple> getRemovableTilesSwitchingLeft(int row, int col)
    {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();

        Tuple leftTile = new Tuple(row,col-1);//switch with tile
        Tuple rightTile = new Tuple(row, col);//selected tile

        if(leftTile.col >= 0)//left tile not out of bound
        {
            String[][] boardCopy = this.board.getBoard();
            //switch
            String val = boardCopy[row][col];
            boardCopy[row][col] = boardCopy[row][col-1];
            boardCopy[row][col-1] = val;

            //if it is switching with a special tile
            String leftTileColor = this.board.getBoard()[leftTile.row][leftTile.col];
            String rightTileColor = this.board.getBoard()[rightTile.row][rightTile.col];

            ArrayList<Tuple> parentTiles = new ArrayList<Tuple>(Arrays.asList(leftTile,rightTile));
            boolean isSymbol = false;
            if(this.symbols.contains(leftTileColor) || this.symbols.contains(rightTileColor))
            {
                isSymbol = true;
            }
            if(leftTileColor.equals("*")||rightTileColor.equals("*"))
            {
                removableTiles.addAll(chocolateSprinkleEffect(leftTile,rightTile,null));
                if(removableTiles.size() == this.board.getRow()*this.board.getCol())
                {
                    return removableTiles;
                }
            }
            else if(leftTileColor.equals("!")||rightTileColor.equals("!"))
            {
                //bomb
                removableTiles.addAll(bombEffect(leftTile,rightTile,boardCopy));
            }
            else if(leftTileColor.equals("#")|| rightTileColor.equals("#"))
            {
                //rocket
                if(leftTileColor.equals(rightTileColor))
                {
                    //get the tiles in the same row and column
                    removableTiles.addAll(rocketEffect(leftTile,"both"));

                }
                else {
                    removableTiles.addAll(rocketEffect(leftTile,"left"));
                }
            }
            else {
                //regular matches
                ArrayList<Tuple> leftUp = super.checkUp(leftTile, boardCopy);
                ArrayList<Tuple> leftDown = super.checkDown(leftTile, boardCopy, this.board.getRow());
                ArrayList<Tuple> leftLeft = super.checkLeft(leftTile, boardCopy);

                removableTiles.addAll(this.getRemovableTiles(leftTile, boardCopy, leftUp, leftDown, leftLeft));

                //right tile
                //check in here because if left tile does not exist, cannot switch
                ArrayList<Tuple> rightUp = super.checkUp(rightTile, boardCopy);
                ArrayList<Tuple> rightDown = super.checkDown(rightTile, boardCopy, this.board.getRow());
                ArrayList<Tuple> rightRight = super.checkRight(rightTile, boardCopy, this.board.getCol());

                removableTiles.addAll(this.getRemovableTiles(rightTile, boardCopy, rightUp, rightDown, rightRight));
            }
            if(removableTiles.size() >= 3)
            {
                this.newBoard = boardCopy;
            }
            //don't want the selectedTiles and switchWithTiles to get added in the list
            if(isSymbol) {
                removableTiles.remove(leftTile);
                removableTiles.remove(rightTile);
                //symbol >= 3, regular == 3 tiles
                findSpecialCandies(removableTiles, parentTiles);
                removableTiles.addAll(parentTiles);
            }
        }

        return removableTiles;
    }

    private ArrayList<Tuple> chocolateSprinkleEffect(Tuple selectedTile, Tuple switchWithTile, String color)
    {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();
        if(color != null)
        {
            for(int row = 0; row < this.board.getRow();row++)
            {
                for(int col = 0; col < this.board.getCol();col++)
                {
                    String currentTileColor = this.board.getBoard()[row][col];
                    if(currentTileColor.equals(color))
                    {
                        removableTiles.add(new Tuple(row,col));
                    }
                }
            }
        }
        else {
            boolean twoChocolate = false;
            // if both tiles are chocolate sprinkles (*), remove everything on board
            String selectedTileColor = this.board.getBoard()[selectedTile.row][selectedTile.col];
            String switchWithTileColor = this.board.getBoard()[switchWithTile.row][switchWithTile.col];
            if(switchWithTileColor.equals("*"))
            {
                if(selectedTileColor.equals("*"))
                {
                    twoChocolate = true;
                }
                //if only switchWithTile is *, then need to remove all the tiles same as selectedTile
            }
            else
            {
                //if only selectedTile is *, then need to remove all the tiles same as switchWithTile
                selectedTileColor = switchWithTileColor;
            }

            ArrayList<Tuple> effect = new ArrayList<Tuple>();
            Random rand = new Random();
            int upperbound = this.directions.size();

            if(selectedTileColor.equals("!"))
            {
                effect = bombEffect(selectedTile,selectedTile,this.board.getBoard());
            }
            else if(selectedTileColor.equals("#"))
            {
                effect = rocketEffect(selectedTile,this.directions.get(rand.nextInt(upperbound)));
            }
            else if(switchWithTileColor.equals("!"))
            {
                effect = bombEffect(switchWithTile,switchWithTile,this.board.getBoard());
            }
            else if(switchWithTileColor.equals("#"))
            {

                effect = rocketEffect(switchWithTile,this.directions.get(rand.nextInt(upperbound)));
            }
            removableTiles.addAll(effect);

            for(int row = 0 ; row < this.board.getRow(); row++)
            {
                for(int col = 0; col < this.board.getCol();col++)
                {
                    if(twoChocolate)
                    {
                        removableTiles.add(new Tuple(row, col));
                    }
                    else {
                        String tile = this.board.getBoard()[row][col];
                        Tuple tuple = new Tuple(row, col);
                        if (tile.equals(selectedTileColor) && !removableTiles.contains(tuple)) {
                            removableTiles.add(tuple);
                        }
                    }
                }
            }
        }
        return removableTiles;
    }

    // used in bombEffect function
    private void addTiles(Tuple tile,ArrayList<Tuple> removableTiles)
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
    private ArrayList<Tuple> bombEffect(Tuple selectedTile, Tuple switchWithTile, String[][] boardCopy)
    {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();
        if(boardCopy[selectedTile.row][selectedTile.col].equals("!"))
        {
            addTiles(selectedTile,removableTiles);
        }

        //if is the same tile, dont want to add again
        if(!(selectedTile == switchWithTile) && boardCopy[switchWithTile.row][switchWithTile.col].equals("!"))
        {
            addTiles(switchWithTile,removableTiles);
        }

        return removableTiles;
    }

    private ArrayList<Tuple> rocketEffect(Tuple tile, String direction)
    {
        ArrayList<Tuple> removableTiles = new ArrayList<>();
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
        else if(direction.equals("both"))
        {
            for(int row = 0; row < this.board.getRow();row++)
            {
                Tuple t = new Tuple(row,tile.col);
                removableTiles.add(t);
            }

            for(int col = 0; col < this.board.getCol();col++)
            {
                Tuple t = new Tuple(tile.row,col);
                if(!removableTiles.contains(t))
                {
                    removableTiles.add(t);
                }
            }
        }

        return removableTiles;
    }

    private void findSpecialCandies(ArrayList<Tuple> removableTiles,ArrayList<Tuple> parentTiles)
    {
        int num = 0;
        ArrayList<Tuple> arr = new ArrayList<Tuple>(removableTiles);
        while(num < arr.size()){
            ArrayList<Tuple> newTiles = new ArrayList<Tuple>();

            //if this is a special candy, let it take effect
            String current_tile = this.board.getBoard()[arr.get(num).row][arr.get(num).col];
            if(this.symbols.contains(current_tile))
            {
                if (current_tile.equals("!")) {
                    newTiles = this.bombEffect(arr.get(num), arr.get(num), this.newBoard);
                } else if (current_tile.equals("*")) {
                    Random rand = new Random();
                    int upperbound = this.tiles.size();
                    int int_rand = rand.nextInt(upperbound);
                    newTiles = this.chocolateSprinkleEffect(arr.get(num), arr.get(num),this.tiles.get(int_rand));

                } else if (current_tile.equals("#")) {
                    Random rand = new Random();
                    int upperbound = this.directions.size();
                    newTiles = this.rocketEffect(arr.get(num), this.directions.get(rand.nextInt(upperbound)));
                }
            }

            //loop over the newTiles to see if it is contained in removableTiles
            for(Tuple nt: newTiles)
            {
                //if not contained in arr and parentTiles, add to arr
                if(!arr.contains(nt) && !parentTiles.contains(nt))
                {
                    arr.add(nt);
                }
            }
            num +=1;
        }

        for(Tuple t: arr)
        {
            removableTiles.add(t);
        }
    }

    private ArrayList<Tuple> getRemovableTilesSwitchingRight(int row, int col)
    {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();

        Tuple rightTile = new Tuple(row,col+1);
        Tuple leftTile = new Tuple(row, col);

        if(rightTile.col < this.board.getCol())//right tile not out of bound
        {
            String[][] boardCopy = this.board.getBoard();
            //switch
            String val = boardCopy[row][col];
            boardCopy[row][col] = boardCopy[row][col+1];
            boardCopy[row][col+1] = val;

            String leftTileColor = this.board.getBoard()[leftTile.row][leftTile.col];
            String rightTileColor = this.board.getBoard()[rightTile.row][rightTile.col];

            ArrayList<Tuple> parentTiles = new ArrayList<Tuple>(Arrays.asList(leftTile,rightTile));
            boolean isSymbol = false;
            if(this.symbols.contains(leftTileColor) || this.symbols.contains(rightTileColor))
            {
                isSymbol = true;
            }

            if(leftTileColor.equals("*")||rightTileColor.equals("*"))
            {
                removableTiles.addAll(chocolateSprinkleEffect(leftTile,rightTile,null));
                if(removableTiles.size() == this.board.getRow()*this.board.getCol())
                {
                    return removableTiles;
                }
            }
            else if(leftTileColor.equals("!")||rightTileColor.equals("!"))
            {
                //bomb
                removableTiles.addAll(bombEffect(leftTile,rightTile,boardCopy));
            }
            else if(leftTileColor.equals("#") || rightTileColor.equals("#"))
            {
                //rocket
                if(leftTileColor.equals(rightTileColor))
                {
                    removableTiles.addAll(rocketEffect(rightTile,"both"));
                }
                else
                {
                    removableTiles.addAll(rocketEffect(rightTile,"right"));
                }
            }
            else {
                //right tile
                ArrayList<Tuple> rightUp = super.checkUp(rightTile, boardCopy);
                ArrayList<Tuple> rightDown = super.checkDown(rightTile, boardCopy, this.board.getRow());
                ArrayList<Tuple> rightRight = super.checkRight(rightTile, boardCopy, this.board.getCol());

                removableTiles.addAll(this.getRemovableTiles(rightTile, boardCopy, rightUp, rightDown, rightRight));

                //left tile
                //check in here because if right tile does not exist, cannot switch
                ArrayList<Tuple> leftUp = super.checkUp(leftTile, boardCopy);
                ArrayList<Tuple> leftDown = super.checkDown(leftTile, boardCopy, this.board.getRow());
                ArrayList<Tuple> leftLeft = super.checkLeft(leftTile, boardCopy);

                removableTiles.addAll(this.getRemovableTiles(leftTile, boardCopy, leftUp, leftDown, leftLeft));
            }
            if(removableTiles.size() >= 3)
            {
                this.newBoard = boardCopy;
            }
            if(isSymbol) {
                removableTiles.remove(leftTile);
                removableTiles.remove(rightTile);
                findSpecialCandies(removableTiles,parentTiles);
                removableTiles.addAll(parentTiles);
            }
        }
        return removableTiles;
    }

    private ArrayList<Tuple> getRemovableTilesSwitchingUp(int row, int col)
    {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();

        Tuple topTile = new Tuple(row-1,col);
        Tuple bottomTile = new Tuple(row,col);

        if(topTile.row >= 0)//up tile not out of bound
        {
            String[][] boardCopy = this.board.getBoard();
            //switch
            String val = boardCopy[row][col];
            boardCopy[row][col] = boardCopy[row-1][col];
            boardCopy[row-1][col] = val;

            String topTileColor = this.board.getBoard()[topTile.row][topTile.col];
            String bottomTileColor = this.board.getBoard()[bottomTile.row][bottomTile.col];
            ArrayList<Tuple> parentTiles = new ArrayList<Tuple>(Arrays.asList(topTile,bottomTile));
            boolean isSymbol = false;
            if(this.symbols.contains(topTileColor) || this.symbols.contains(bottomTileColor))
            {
                isSymbol = true;
            }
            if(topTileColor.equals("*") ||bottomTileColor.equals("*"))
            {
                removableTiles.addAll(chocolateSprinkleEffect(topTile,bottomTile,null));
                if(removableTiles.size() == this.board.getRow()*this.board.getCol())
                {
                    return removableTiles;
                }
            }
            else if(topTileColor.equals("!") ||bottomTileColor.equals("!"))
            {
                //bomb
                removableTiles.addAll(bombEffect(topTile,bottomTile,boardCopy));
            }
            else if(topTileColor.equals("#") || bottomTileColor.equals("#"))
            {
                //rocket
                if(topTileColor.equals(bottomTileColor))
                {
                    removableTiles.addAll(rocketEffect(topTile,"both"));
                }
                else
                {
                    removableTiles.addAll(rocketEffect(topTile,"up"));
                }
            }
            else {
                //up tile
                ArrayList<Tuple> topLeft = super.checkLeft(topTile, boardCopy);
                ArrayList<Tuple> topRight = super.checkRight(topTile, boardCopy, this.board.getCol());
                ArrayList<Tuple> topUp = super.checkUp(topTile, boardCopy);
                removableTiles.addAll(this.getRemovableTiles(topTile, boardCopy, topLeft, topRight, topUp));
                //down tile
                //check in here because if up tile does not exist, cannot switch

                ArrayList<Tuple> bottomLeft = super.checkLeft(bottomTile, boardCopy);
                ArrayList<Tuple> bottomRight = super.checkRight(bottomTile, boardCopy, this.board.getCol());
                ArrayList<Tuple> bottomDown = super.checkDown(bottomTile, boardCopy, this.board.getRow());

                removableTiles.addAll(this.getRemovableTiles(bottomTile, boardCopy, bottomLeft, bottomRight, bottomDown));
            }
            if(removableTiles.size() >= 3)
            {
                this.newBoard = boardCopy;
            }
            if(isSymbol) {
                removableTiles.remove(topTile);
                removableTiles.remove(bottomTile);
                findSpecialCandies(removableTiles, parentTiles);
                removableTiles.addAll(parentTiles);
            }
        }
        return removableTiles;
    }

    private ArrayList<Tuple> getRemovableTilesSwitchingDown(int row, int col)
    {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();

        Tuple bottomTile = new Tuple(row+1,col);
        Tuple topTile = new Tuple(row, col);

        if(bottomTile.row < this.board.getRow())//bottom tile not out of bound
        {
            String[][] boardCopy = this.board.getBoard();
            //switch
            String val = boardCopy[row][col];
            boardCopy[row][col] = boardCopy[row+1][col];
            boardCopy[row+1][col] = val;

            String topTileColor = this.board.getBoard()[topTile.row][topTile.col];
            String bottomTileColor = this.board.getBoard()[bottomTile.row][bottomTile.col];
            ArrayList<Tuple> parentTiles = new ArrayList<Tuple>(Arrays.asList(topTile,bottomTile));
            boolean isSymbol = false;
            if(this.symbols.contains(topTileColor) || this.symbols.contains(bottomTileColor))
            {
                isSymbol = true;
            }
            if(topTileColor.equals("*")||bottomTileColor.equals("*"))
            {
                removableTiles.addAll(chocolateSprinkleEffect(topTile,bottomTile,null));
                if(removableTiles.size() == this.board.getRow()*this.board.getCol())
                {
                    return removableTiles;
                }
            }
            else if(topTileColor.equals("!") ||bottomTileColor.equals("!"))
            {
                //bomb
                removableTiles.addAll(bombEffect(topTile,bottomTile,boardCopy));
            }
            else if(topTileColor.equals("#")|| bottomTileColor.equals("#"))
            {
                //rocket
                if(topTileColor.equals(bottomTileColor))
                {
                    removableTiles.addAll(rocketEffect(topTile,"both"));
                }
                else {
                    removableTiles.addAll(rocketEffect(topTile,"down"));
                }
            }
            else {
                //down tile
                ArrayList<Tuple> bottomLeft = super.checkLeft(bottomTile, boardCopy);
                ArrayList<Tuple> bottomRight = super.checkRight(bottomTile, boardCopy, this.board.getCol());
                ArrayList<Tuple> bottomDown = super.checkDown(bottomTile, boardCopy, this.board.getRow());

                removableTiles.addAll(this.getRemovableTiles(bottomTile, boardCopy, bottomLeft, bottomRight, bottomDown));
                //up tile
                //check in here because if bottom tile does not exist, cannot switch
                ArrayList<Tuple> topLeft = super.checkLeft(topTile, boardCopy);
                ArrayList<Tuple> topRight = super.checkRight(topTile, boardCopy, this.board.getCol());
                ArrayList<Tuple> topUp = super.checkUp(topTile, boardCopy);

                removableTiles.addAll(this.getRemovableTiles(topTile, boardCopy, topLeft, topRight, topUp));
            }
            if(removableTiles.size() >= 3)
            {
                this.newBoard = boardCopy;
            }
            if(isSymbol)
            {
                removableTiles.remove(topTile);
                removableTiles.remove(bottomTile);
                findSpecialCandies(removableTiles,parentTiles);
                removableTiles.addAll(parentTiles);
            }
        }
        return removableTiles;
    }

    private ArrayList<Tuple> getRemovableTiles(Tuple tile, String[][] boardCopy,ArrayList<Tuple> list1,ArrayList<Tuple> list2,ArrayList<Tuple> list3)
    {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();
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
        else if(list3.size() <= 0 && list1.size() + list2.size() + 1 == 3)
        {
            //regular
            removableTiles.addAll(list1);
            removableTiles.addAll(list2);
            removableTiles.add(tile);
        }
        else if(((list1.size()== 1 && list2.size()==0)||(list1.size() == 0 && list2.size() == 1) || (list1.size() == 0 && list2.size() == 0)) && list3.size()+1 == 3)
        {
            //regular
            removableTiles.addAll(list3);
            removableTiles.add(tile);
        }
        return removableTiles;
    }

    public ArrayList<Tuple> findAllMatchesAfterUpdate()
    {
        ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();
        String[][] visited = new String[this.board.getRow()][this.board.getCol()];
        String[][] boardCopy = this.board.getBoard();

        //starting from bottom left corner
        for(int row = this.board.getRow()-1; row >= 0; row--)
        {
            for(int col = 0; col < this.board.getCol(); col++)
            {
                Tuple tile = new Tuple(row,col);
                ArrayList<Tuple> tilesFound = new ArrayList<Tuple>();
                //if this tile has not been visited
                if(visited[row][col] == null)
                {
                    String currentTile = this.board.getBoard()[row][col];
                    ArrayList<String> symbols = new ArrayList<String>(Arrays.asList("!","*","#"));
                    if(!symbols.contains(currentTile))
                    {
                        ArrayList<Tuple> up = super.checkUp(tile,this.board.getBoard());
                        ArrayList<Tuple> down = super.checkDown(tile,this.board.getBoard(),this.board.getRow());
                        ArrayList<Tuple> left = super.checkLeft(tile,this.board.getBoard());
                        ArrayList<Tuple> right = super.checkRight(tile,this.board.getBoard(),this.board.getCol());

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
                    visited[row][col] = "visited";
                }
            }
        }
        return removableTiles;
    }

}


