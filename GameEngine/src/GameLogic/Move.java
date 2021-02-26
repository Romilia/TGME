package GameLogic;
import java.util.*;

public class Move {
	
	//prompt user for selected position row and col and if switching with a direction forms a match
	//remove and update the board if there is a match
	
	private Board board;
	
	Moves(Board board)
	{
		this.board = board;
	}
	
//	public void promptUserInput()
//	{
//		Scanner input = new Scanner(System.in);
//		
//		System.out.print("Enter the row of the position: ");
//		int row = input.nextInt();
//		
//		System.out.print("Enter the col of the position: ");
//		int col = input.nextInt();
//		
//		System.out.print("Switch with (left, right, up, down): ");
//		input = new Scanner(System.in);
//		String direction = input.nextLine();
//	}
	
	public Boolean isValidMove(int row, int col, String direction)
	{
		ArrayList<String> availableDirections = new ArrayList<String>(){{
			add("left");
			add("right");
			add("up");
			add("down");
		}};
		
		//if not within boundary and not an available direction, return false
		if(row < 0 || row >= this.board.getRow() || col < 0 || col >= this.board.getCol() ||
				!availableDirections.contains(direction.toLowerCase()))
		{
			return false;
		}
		return true;
	}
	
	public void makeMove()
	{
		while(true) // if no more matches, game ends
		{
			Scanner input = new Scanner(System.in);
			
			System.out.print("Enter the row of the position: ");
			int row = input.nextInt();
			
			System.out.print("Enter the col of the position: ");
			int col = input.nextInt();
			
			System.out.print("Switch with (left, right, up, down): ");
			input = new Scanner(System.in);
			String direction = input.nextLine();
			
			//ensures that it is a valid move
			if(this.isValidMove(row,col,direction))
			{
				ArrayList<Tuple> removableTiles;
				if(direction == "left")
				{
					removableTiles = getRemovableTilesSwitchingLeft(row,col);
				}
				else if(direction == "right")
				{
					removableTiles = getRemovableTilesSwitchingRight(row,col);
				}
				else if(direction == "up")
				{
					removableTiles = getRemovableTilesSwitchingUp(row,col);
				}
				else if(direction == "down")
				{
					removableTiles = getRemovableTilesSwitchingDown(row,col);
				}
				
				if(removableTiles.size() >= 3)
				{
					//board should remove all these Tuple pairs and update board
					board.updateBoard(removableTiles);
					
					//after update, should check if there is anymore matches formed
					while(true)
					{
						
					}
				}
			}
			else
			{
				//if not a valid move, nothing changes and prompt user for input again
			}
			
			//need to check if there is possible matches to be make
			//if not, break out the while loop
		}
	}
	
	public ArrayList<Tuple> getRemovableTilesSwitchingLeft(int row, int col)
	{
		ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();
		
		Tuple leftTile = new Tuple(row,col-1);
		
		if(leftTile.col >= 0)//left tile not out of bound
		{
			String[][] boardCopy = this.board.getBoard();
			//switch
			String val = boardCopy[row][col];
			boardCopy[row][col] = boardCopy[row][col-1];
			boardCopy[row][col-1] = val;
			
			ArrayList<Tuple> leftUp = checkUp(leftTile,boardCopy);
			ArrayList<Tuple> leftDown = checkDown(leftTile,boardCopy);
			
			if(leftUp.size() + leftDown.size()+1 >= 3)
			{
				removableTiles.addAll(leftUp);
				removableTiles.addAll(leftDown);
				removableTiles.add(leftTile);
			}
			
			ArrayList<Tuple> leftLeft = checkLeft(leftTile,boardCopy);
			if(leftLeft.size()+1 >= 3)
			{
				removableTiles.addAll(leftLeft);
			}
			
			//right tile
			//check in here because if left tile does not exist, cannot switch
			Tuple rightTile = new Tuple(row,col);
			ArrayList<Tuple> rightUp = checkUp(rightTile,boardCopy);
			ArrayList<Tuple> rightDown = checkDown(rightTile,boardCopy);
			
			if(rightUp.size() + rightDown.size()+1 >= 3)
			{
				removableTiles.addAll(rightUp);
				removableTiles.addAll(rightDown);
				removableTiles.add(rightTile);
			}
			
			ArrayList<Tuple> rightRight = checkRight(rightTile,boardCopy);
			if(rightRight.size()+1 >= 3)
			{
				removableTiles.addAll(rightRight);
			}
		}
		
		return removableTiles; 
	}
	
	public ArrayList<Tuple> getRemovableTilesSwitchingRight(int row, int col)
	{
		ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();
		
		Tuple rightTile = new Tuple(row,col+1);
		
		if(rightTile.col < this.board.getCol())//right tile not out of bound
		{
			String[][] boardCopy = this.board.getBoard();
			//switch
			String val = boardCopy[row][col];
			boardCopy[row][col] = boardCopy[row][col+1];
			boardCopy[row][col+1] = val;
			
			//right tile
			ArrayList<Tuple> rightUp = checkUp(rightTile,boardCopy);
			ArrayList<Tuple> rightDown = checkDown(rightTile,boardCopy);
			
			if(rightUp.size() + rightDown.size()+1 >= 3)
			{
				removableTiles.addAll(rightUp);
				removableTiles.addAll(rightDown);
				removableTiles.add(rightTile);
			}
			
			ArrayList<Tuple> rightRight = checkRight(rightTile,boardCopy);
			if(rightRight.size()+1 >= 3)
			{
				removableTiles.addAll(rightRight);
			}
			
			//left tile 
			//check in here because if right tile does not exist, cannot switch
			Tuple leftTile = new Tuple(row,col);
			ArrayList<Tuple> leftUp = checkUp(leftTile,boardCopy);
			ArrayList<Tuple> leftDown = checkDown(leftTile,boardCopy);
			
			if(leftUp.size() + leftDown.size()+1 >= 3)
			{
				removableTiles.addAll(leftUp);
				removableTiles.addAll(leftDown);
				removableTiles.add(leftTile);
			}
			
			ArrayList<Tuple> leftLeft = checkLeft(leftTile,boardCopy);
			if(leftLeft.size()+1 >= 3)
			{
				removableTiles.addAll(leftLeft);
			}
		}
		
		return removableTiles; 
	}
	
	public ArrayList<Tuple> getRemovableTilesSwitchingUp(int row, int col)
	{
		ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();
		
		Tuple topTile = new Tuple(row-1,col);
		
		if(topTile.row >= 0)//up tile not out of bound
		{
			String[][] boardCopy = this.board.getBoard();
			//switch
			String val = boardCopy[row][col];
			boardCopy[row][col] = boardCopy[row-1][col];
			boardCopy[row-1][col] = val;
			
			//up tile
			ArrayList<Tuple> topLeft = checkLeft(topTile,boardCopy);
			ArrayList<Tuple> topRight = checkRight(topTile,boardCopy);
			
			if(topLeft.size() + topRight.size()+1 >= 3)
			{
				removableTiles.addAll(topLeft);
				removableTiles.addAll(topRight);
				removableTiles.add(topTile);
			}
			
			ArrayList<Tuple> topUp = checkUp(topTile,boardCopy);
			if(topUp.size()+1 >= 3)
			{
				removableTiles.addAll(topUp);
			}
			
			//down tile
			//check in here because if up tile does not exist, cannot switch
			
			Tuple bottomTile = new Tuple(row,col);
			ArrayList<Tuple> bottomLeft = checkLeft(bottomTile,boardCopy);
			ArrayList<Tuple> bottomRight = checkRight(bottomTile,boardCopy);
			
			if(bottomLeft.size() + bottomRight.size()+1 >= 3)
			{
				removableTiles.addAll(bottomLeft);
				removableTiles.addAll(bottomRight);
				removableTiles.add(bottomTile);
			}
			
			ArrayList<Tuple> bottomDown = checkDown(bottomTile,boardCopy);
			if(bottomDown.size()+1 >= 3)
			{
				removableTiles.addAll(bottomDown);
			}
		}
		
		return removableTiles; 
	}
	
	public ArrayList<Tuple> getRemovableTilesSwitchingDown(int row, int col)
	{
		ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();
		
		Tuple bottomTile = new Tuple(row+1,col);
		
		if(bottomTile.row < this.board.getRow())//bottom tile not out of bound
		{
			String[][] boardCopy = this.board.getBoard();
			//switch
			String val = boardCopy[row][col];
			boardCopy[row][col] = boardCopy[row+1][col];
			boardCopy[row+1][col] = val;
			
			//down tile
			ArrayList<Tuple> bottomLeft = checkLeft(bottomTile,boardCopy);
			ArrayList<Tuple> bottomRight = checkRight(bottomTile,boardCopy);
			
			if(bottomLeft.size() + bottomRight.size()+1 >= 3)
			{
				removableTiles.addAll(bottomLeft);
				removableTiles.addAll(bottomRight);
				removableTiles.add(bottomTile);
			}
			
			ArrayList<Tuple> bottomDown = checkDown(bottomTile,boardCopy);
			if(bottomDown.size()+1 >= 3)
			{
				removableTiles.addAll(bottomDown);
			}
			
			//up tile
			//check in here because if bottom tile does not exist, cannot switch
			Tuple topTile = new Tuple(row,col);
			ArrayList<Tuple> topLeft = checkLeft(topTile,boardCopy);
			ArrayList<Tuple> topRight = checkRight(topTile,boardCopy);
			
			if(topLeft.size() + topRight.size()+1 >= 3)
			{
				removableTiles.addAll(topLeft);
				removableTiles.addAll(topRight);
				removableTiles.add(bottomTile);
			}
			
			ArrayList<Tuple> topUp = checkUp(topTile,boardCopy);
			if(topUp.size()+1 >= 3)
			{
				removableTiles.addAll(topUp);
			}
		}
		
		return removableTiles; 
	}
	
	private ArrayList<Tuple> checkUp(Tuple tile, String[][] boardCopy)
	{
		ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();
		
		int comparingRow = tile.row-1;
		String currentTile = boardCopy[tile.row][tile.col];
		
		while(comparingRow >= 0 &&  currentTile == boardCopy[comparingRow][tile.col])
		{
			Tuple t = new Tuple(comparingRow,tile.col);
			removableTiles.add(t);
			comparingRow -= 1;
		}

		return removableTiles;
	}
	
	private ArrayList<Tuple> checkDown(Tuple tile, String[][] boardCopy)
	{
		ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();
		
		int comparingRow = tile.row+1;
		String currentTile = boardCopy[tile.row][tile.col];
		
		while(comparingRow < this.board.getRow() &&  currentTile == boardCopy[comparingRow][tile.col])
		{
			Tuple t = new Tuple(comparingRow,tile.col);
			removableTiles.add(t);
			comparingRow += 1;
		}

		return removableTiles;
	}
	
	private ArrayList<Tuple> checkLeft(Tuple tile, String[][] boardCopy)
	{
		ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();
		
		int comparingCol = tile.row-1;
		String currentTile = boardCopy[tile.row][tile.col];
		
		while(comparingCol >= 0 &&  currentTile == boardCopy[tile.row][comparingCol])
		{
			Tuple t = new Tuple(tile.row,comparingCol);
			removableTiles.add(t);
			comparingCol -= 1;
		}

		return removableTiles;
	}
	
	private ArrayList<Tuple> checkRight(Tuple tile, String[][] boardCopy)
	{
		ArrayList<Tuple> removableTiles = new ArrayList<Tuple>();
		
		int comparingCol = tile.col+1;
		String currentTile = boardCopy[tile.row][tile.col];
		
		while(comparingCol < this.board.getCol() &&  currentTile == boardCopy[tile.row][comparingCol])
		{
			Tuple t = new Tuple(tile.row,comparingCol);
			removableTiles.add(t);
			comparingCol += 1;
		}

		return removableTiles;
	}
	
	public void findAllMatchesAfterUpdate()
	{
		
	}
}

