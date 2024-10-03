// Board.java

import java.util.Arrays;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;

	private int[] widths, heights;
	private int maxHeight;

	private boolean[][] xGrid;
	private int[] xWidths, xHeights;
	private int xMaxHeight;

	
	
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		committed = true;
		
		widths = new int[height];
		heights = new int[width];
		Arrays.fill(widths, 0);
		Arrays.fill(heights, 0);

		maxHeight = 0;

		xGrid = new boolean[width][height];

		xWidths = new int[height];
		xHeights = new int[width];
		Arrays.fill(xWidths, 0);
		Arrays.fill(xHeights, 0);

		xMaxHeight = 0;
	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {	 
		return maxHeight;
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
			int[] checkWidths = new int[height];
			int[] checkHeights = new int[width];
			int checkMaxHeight = 0;

			for(int row = 0; row < height; row ++){
				for(int col = 0; col < width; col ++){
					if(grid[col][row]){
						checkWidths[row] ++;
						if(checkHeights[col] < row + 1) checkHeights[col] = row + 1;
						checkMaxHeight = Math.max(checkMaxHeight, checkHeights[col]);
					}
				}
			}

			if(!Arrays.equals(checkWidths, widths) || !Arrays.equals(checkHeights, heights) || checkMaxHeight != maxHeight){
				throw new RuntimeException("Board is insane");
			}
		}
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		if(x + piece.getWidth() > width || x < 0){
			return PLACE_OUT_BOUNDS;
		}
		int y = 0;
		int[] skirt = piece.getSkirt();
		for(int i = 0; i < piece.getWidth(); i ++){
			int h = heights[x + i] - skirt[i];
			y  = Math.max(y, h);
		}
		return y;
	}

	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		return heights[x];
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		 return widths[y];
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		if(x < 0 || x >= width || y < 0 || y >= height){
			return true;
		}
		return grid[x][y];
	}
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
			
		int result = PLACE_OK;
		committed = false;
		backup();
		
		if(x + piece.getWidth() > width || y + piece.getHeight() > height || x < 0 || y < 0){
			return PLACE_OUT_BOUNDS;
		}

		for (TPoint p : piece.getBody()) {
			int currentX = x + p.x;
			int currentY = y + p.y;
			if(grid[currentX][currentY]){
				return PLACE_BAD;
			}
			grid[currentX][currentY] = true;
			widths[currentY] ++;
			if(widths[currentY] == width){
				result = PLACE_ROW_FILLED;
			}
			if(heights[currentX] < currentY + 1) {
				heights[currentX] = currentY + 1;
			}
			maxHeight = Math.max(maxHeight, heights[currentX]);
		}
		if(result != PLACE_BAD) {
			sanityCheck();
		}
		return result;
	}

	private void backup(){
		for(int i = 0; i < grid.length; i ++) {
			System.arraycopy(grid[i], 0, xGrid[i], 0, grid[i].length);
		}
		System.arraycopy(widths, 0, xWidths, 0, height);
		System.arraycopy(heights, 0, xHeights, 0, width);
		xMaxHeight = maxHeight;
	}
	
	
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		if(committed){
			committed = false;
			backup();
		}

		int rowsCleared = 0;
		int fromRow = 0;
		for(int i = 0; i < maxHeight; i ++){
			if(widths[i] == width) {
				rowsCleared++;
				fromRow = i + 1;
			}
			if(fromRow == height){
				break;
			}
			for (int col = 0; col < width; col++) {
				while(widths[fromRow] == width) {
					fromRow++;
				}
				if(fromRow == height){
					break;
				}
				grid[col][i] = grid[col][fromRow];
				widths[i] = widths[fromRow];
			}
			fromRow ++;
		}

		for(int i = maxHeight - rowsCleared; i < maxHeight; i ++){
			for(int col = 0; col < width; col ++){
				grid[col][i] = false;
			}
			widths[i] = 0;
		}

		maxHeight -= rowsCleared;

		Arrays.fill(heights, 0);
		for(int row = maxHeight - 1; row >= 0; row --){
			for(int col = 0; col < width; col ++){
				if(grid[col][row]){
					if(row + 1 > heights[col]){
						heights[col] = row + 1;
					}
				}
			}
		}
		sanityCheck();
		return rowsCleared;
	}



	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		if(committed){
			return;
		}
		int[] temp = widths;
		widths = xWidths;
		xWidths = temp;

		temp = heights;
		heights = xHeights;
		xHeights = temp;
		maxHeight = xMaxHeight;

		boolean[][] temp1 = grid;
		grid = xGrid;
		xGrid = temp1;

		committed = true;
		sanityCheck();
	}
	
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
	}


	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


