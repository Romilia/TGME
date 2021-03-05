package GameLogic;

import java.util.*;

public class Move {

    //prompt user for selected position row and col and if switching with a direction forms a match
    //remove and update the board if there is a match

    public Move() {
    }
    protected int promptRow()
    {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the row of the position: ");
        int row = input.nextInt();
        return row;
    }

    protected int promptCol()
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the col of the position: ");
        int col = input.nextInt();
        return col;
    }

    protected String promptDirection()
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Switch with (left, right, up, down): ");
        input = new Scanner(System.in);
        String direction = input.nextLine();
        return direction;
    }

    protected Boolean isValidMove(int row, int col, String direction, int boardRow, int boardCol) {
        final ArrayList<String> availableDirections = new ArrayList<String>() {{
            add("left");
            add("right");
            add("up");
            add("down");
        }};

        //if not within boundary and not an available direction, return false
        if (row < 0 || row >= boardRow || col < 0 || col >= boardCol ||
                !availableDirections.contains(direction.toLowerCase())) {
            return false;
        }
        return true;
    }

    protected HashSet<Tuple> checkUp(Tuple tile, String[][] boardCopy) {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();

        int comparingRow = tile.row - 1;
        String currentTile = boardCopy[tile.row][tile.col];
        while (comparingRow >= 0 && boardCopy[comparingRow][tile.col].equals(currentTile)) {
            Tuple t = new Tuple(comparingRow, tile.col);
            removableTiles.add(t);
            comparingRow -= 1;
        }
        return removableTiles;
    }

    protected HashSet<Tuple> checkDown(Tuple tile, String[][] boardCopy, int boardRow) {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();

        int comparingRow = tile.row + 1;
        String currentTile = boardCopy[tile.row][tile.col];

        while (comparingRow < boardRow && boardCopy[comparingRow][tile.col].equals(currentTile)) {
            Tuple t = new Tuple(comparingRow, tile.col);
            removableTiles.add(t);
            comparingRow += 1;
        }

        return removableTiles;
    }

    protected HashSet<Tuple> checkLeft(Tuple tile, String[][] boardCopy) {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();

        int comparingCol = tile.col - 1;
        String currentTile = boardCopy[tile.row][tile.col];

        while (comparingCol >= 0 && boardCopy[tile.row][comparingCol].equals(currentTile)) {
            Tuple t = new Tuple(tile.row, comparingCol);
            removableTiles.add(t);
            comparingCol -= 1;
        }

        return removableTiles;
    }

    protected HashSet<Tuple> checkRight(Tuple tile, String[][] boardCopy, int boardCol) {
        HashSet<Tuple> removableTiles = new HashSet<Tuple>();

        int comparingCol = tile.col + 1;
        String currentTile = boardCopy[tile.row][tile.col];
        while (comparingCol < boardCol && boardCopy[tile.row][comparingCol].equals(currentTile)) {
            Tuple t = new Tuple(tile.row, comparingCol);
            removableTiles.add(t);
            comparingCol += 1;
        }
        return removableTiles;
    }
}
