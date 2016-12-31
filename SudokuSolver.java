public class SudokuSolver {
	// declare constants
	public static final int SIZE = 9;

	// declare grid
	private int[][] grid;

	
	/** 
	* Constructor: SudokuSolver
	* Description: creates SudokuSolver object and assigns given sudoku puzzle to grid
	* Parameters: int[][] grid
	* Return: none
	*/
	public SudokuSolver(int[][] grid) {
		this.grid = grid;
	}

	/**
	* Method: showGrid
	* Parameters: none
	* Description: displays the grid in easy to read 3x3 chunks
	* Return: none
	*/
	public void showGrid(){
		for(int x = 0; x < SIZE; x++) {
			// adds blank line when rows % 3 == 0 for easier to read grid 
			if(x % 3 == 0) {
				System.out.println();
			}
			for(int y = 0; y < SIZE; y++) {
				// adds space when cols % 3 == 0 for easier to read grid
				if(y % 3 == 0) {
					System.out.print(" ");
				}
				System.out.print(grid[x][y] + " ");
			}
			// new line when entire row printed
			System.out.println();
		}
		System.out.println();
	}

	/**
	* Method: findUnassignedLocation
	* Parameters: none
	* Description: Iterates through the grid until it finds a cell with
	* 			   the value of 0, then assigns the coordinates to an 
	*              int array, [x,y]. If no cell is found with a value
	*			   of 0, coordinates get set to [-1,-1]
	* Return: int[] cell 
	*/
	public int[] findUnassignedLocation() {
		int[] cell = new int[2];
		for(int x = 0; x < SIZE; x++) {
			for(int y = 0; y < SIZE; y++) {
				if(grid[x][y] == 0) {
					cell[0] = x;
					cell[1] = y;
					return cell;
				}
			}
		}
		cell[0] = -1;
		cell[1] = -1;
		return cell;
	}

	/**
	* Method: usedInRow
	* Parameters: int row, int num
	* Description: Checks through the given row to see if it already
	* 			   contains the given num
	* Return: true if given num is found, false otherwise
	*/
	private boolean usedInRow(int row, int num) {
		for(int y = 0; y < SIZE; y++) {
			if(grid[row][y] == num) {
				return true;
			}
		}
		return false;
	}

	/**
	* Method: usedInCol
	* Parameters: int col, int num
	* Description: Checks through the given col to see if it already
	* 			   contains the given num
	* Return: true if given num is found, false otherwise
	*/
	private boolean usedInCol(int col, int num) {
		for(int x = 0; x < SIZE; x++) {
			if(grid[x][col] == num) {
				return true;
			}
		}
		return false;
	}

	/**
	* Method: usedInCube
	* Parameters: int r, int c, int num
	* Description: Calculates which of the nine 3x3 sub-grids the
	* 			   coordinates (r,c) fall in, then searches that sub-grid
	*              to see if it already contains the given num
	* Return: true if given num is found, false otherwise
	*/
	private boolean usedInCube(int r, int c, int num) {
		int row = r - r % 3;
		int col = c - c % 3;
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				if(grid[x + row][y + col] == num) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	* Method: checkIfSafe
	* Parameters: int row, int col, int num
	* Description: Calls each of the usedIn methods to determine if the given
	* 			   num can be assigned to the given row, col 
	* Return: true if each of the usedIn method calls return false, false otherwise
	*/
	public boolean checkIfSafe(int row, int col, int num) {
		return !usedInRow(row, num) && !usedInCol(col, num) && !usedInCube(row, col, num);
	}

	/**
	* Method: solveSudoku
	* Description: Recursive method that gets the coordinates of the next empty cell
	*              and then checks to see if got valid coordinates, if it does, the 
	*			   method then loops through the valid sudoku cell values (1-9) and 
	* 	 	 	   checks if it's safe to assign the current value, if it is, the
	* 			   method calls itself to check if the return value is true, if it is,
	*              the method returns true back through the recursive levels. If it
	*              doesn't return true there, it means the value is invalid at that
	*	 	  	   location and it resets the cell to 0 and backtracks to the previous
	*              recursive call
	* Return: true if no unassigned cells are found, true if recursive call returns true, false otherwise
	*/
	public boolean solveSudoku() {
		int[] newCell = findUnassignedLocation();
		int row = newCell[0];
		int col = newCell[1];
		if(row == -1) {
			return true;
		}
		for(int i = 1; i < 10; i++) {
			if(checkIfSafe(row, col, i)) {
				grid[row][col] = i;
				if(solveSudoku()) {
					return true;
				}
				grid[row][col] = 0;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		// this is where we would load the puzzle from OCR into our program
		int[][] puzzle = new int[][]{{3, 0, 6, 5, 0, 8, 4, 0, 0},
                      				 {5, 2, 0, 0, 0, 0, 0, 0, 0},
                      				 {0, 8, 7, 0, 0, 0, 0, 3, 1},
                      				 {0, 0, 3, 0, 1, 0, 0, 8, 0},
                      				 {9, 0, 0, 8, 6, 3, 0, 0, 5},
                      				 {0, 5, 0, 0, 9, 0, 6, 0, 0},
                      				 {1, 3, 0, 0, 0, 0, 2, 5, 0},
                      				 {0, 0, 0, 0, 0, 0, 0, 7, 4},
                      				 {0, 0, 5, 2, 0, 6, 3, 0, 0}};

		SudokuSolver ss = new SudokuSolver(puzzle);
		System.out.println("Puzzle to solve...");
		ss.showGrid();
		if(ss.solveSudoku()) {
			System.out.println("Displaying solution for puzzle.");
			ss.showGrid();
		}
		else {
			System.out.println("There is no solution for this puzzle.");
		}

	}
}
