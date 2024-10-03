// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

import javax.sql.rowset.BaseRowSet;

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		if(grid.length == 0){
			return 0;
		}

		int left = grid[0].length;
		int right = 0;
		int top = 0;
		int bottom = 0;
		boolean contains = false;

		for(int i = 0; i < grid.length; i ++){
			for(int j = 0; j < grid[i].length; j ++){
				if(grid[i][j] == ch){
					if(!contains){
						top = i;
					}
					left = Math.min(left, j);
					right = Math.max(right, j);
					contains = true;
					bottom = i;
				}
			}
		}
		if(!contains){
			return 0;
		}
		return (bottom - top + 1) * (right - left + 1);
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		if(grid.length < 3){
			return 0;
		}

		int count = 0;
		//min 1 symbol in arm so looking for plus center from margin 1
		for(int i = 1; i < grid.length - 1; i ++){
			for(int j = 1; j < grid[i].length - 1; j ++){
				count += isPlus(i, j) ? 1 : 0;
			}
		}

		return count;
	}

	private boolean isPlus(int x, int y){
		int left = 0, right = 0, top = 0, bottom = 0;
		boolean l = true, r = true, t = true, b = true;
		int limit = Math.max(grid.length, maxY());
		for(int i = 1; i < limit; i ++){
			if(x - i >= 0 && grid[x-i].length > y) {
				if (grid[x - i][y] != grid[x][y]) {
					t = false;
				} else if (t && grid[x - i][y] == grid[x][y]) {
					top++;
				}
			}

			if(x + i < grid.length && grid[x + i].length > y) {
				if (grid[x + i][y] != grid[x][y]) {
					b = false;
				} else if (grid[x + i][y] == grid[x][y] && b) {
					bottom++;
				}
			}

			if(y - i >= 0) {
				if (grid[x][y - i] != grid[x][y]) {
					l = false;
				} else if (grid[x][y - i] == grid[x][y] && l) {
					left++;
				}
			}

			if(y + i < grid[x].length) {
				if (grid[x][y + i] != grid[x][y]) {
					r = false;
				} else if (grid[x][y + i] == grid[x][y] && r) {
					right++;
				}
			}
		}
		return areEqual(left, right, top, bottom);
	}

	private boolean areEqual(int l, int r, int t, int b){
		if(l != r || l != t || l != b || r != t || r != b || t != b){
			return false;
		}

		if(l == 0){
			return false;
		}
		return true;
	}

	private int maxY(){
		int max = 0;
		for(int i = 0; i < grid.length; i ++){
			max = Math.max(max, grid[i].length);
		}
		return max;
	}
}
