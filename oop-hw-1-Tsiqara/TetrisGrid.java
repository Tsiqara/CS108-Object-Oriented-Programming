//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

public class TetrisGrid {
	private boolean[][] grid;
	
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
		this.grid = grid;
	}
	
	
	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		if(grid.length == 0){
			return;
		}

		boolean isFull = true;
		for(int i = 0; i < grid[0].length; i ++){
			isFull = true;
			for(int j = 0; j < grid.length; j ++){
				if(!grid[j][i]){
					isFull = false;
				}
			}
			if(isFull){
				shiftRows(i);
				clearRows();
			}
		}
	}

	private void shiftRows(int y){
		for(int i = y; i < grid[0].length - 1; i ++){
			for(int j = 0; j < grid.length; j ++){
				grid[j][i] = grid[j][i+1];
			}
		}
		for(int x = 0; x < grid.length; x ++){
			grid[x][grid[0].length - 1] = false;
		}
	}
	
	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return grid;
	}
}
