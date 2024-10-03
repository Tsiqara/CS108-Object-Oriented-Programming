
// Test cases for CharGrid -- a few basic tests are provided.

import junit.framework.TestCase;

public class CharGridTest extends TestCase {
	
	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
			};
		
		
		CharGrid cg = new CharGrid(grid);
				
		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
	}
	
	
	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
		assertEquals(9, cg.charArea(' '));
	}

	public void testCharAreaSpaces() {
		char[][] grid = new char[][] {
				{' ', ' ', ' '},
				{' ', ' ', ' '},
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(0, cg.charArea('a'));
		assertEquals(6, cg.charArea(' '));
	}

	public void testCharAreaEmptyArrays() {
		char[][] grid = new char[][] {
				{},
				{},
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(0, cg.charArea('a'));
		assertEquals(0, cg.charArea(' '));
	}
	public void testCharAreaEmpty() {
		char[][] grid = new char[][] {};

		CharGrid cg = new CharGrid(grid);

		assertEquals(0, cg.charArea('a'));
		assertEquals(0, cg.charArea(' '));
	}

	public void testCharArea3() {
		char[][] grid = new char[][] {
				{'a', 'b', 'c', 'd'},
				{'a', ' ', 'c', 'b'},
				{'x', 'b', 'c', 'a'}
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(12, cg.charArea('a'));
		assertEquals(3, cg.charArea('c'));
		assertEquals(1, cg.charArea(' '));
		assertEquals(0, cg.charArea('m'));
	}

	public void testCharAreaUneven() {
		char[][] grid = new char[][] {
				{'a', 'b', 'c'},
				{'a', ' ', 'c', 'b'},
				{'x', 'b'}
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(2, cg.charArea('a'));
		assertEquals(2, cg.charArea('c'));
		assertEquals(9, cg.charArea('b'));
		assertEquals(0, cg.charArea('m'));
	}

	public void testPlus1(){
		char[][] grid = new char[][] {
				{' ', ' ', 'p'},
				{' ', ' ', 'p', ' ', ' ', ' ', ' ', 'x'},
				{'p', 'p', 'p', 'p', 'p', ' ', 'x', 'x', 'x'},
				{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x'},
				{' ', ' ', 'p', ' ', 'y', 'y', 'y'},
				{'z', 'z', 'z', 'z', 'z', 'y', 'z', 'z', 'z'},
				{' ', ' ', 'x', 'x', ' ', 'y'}
		};

		CharGrid cg = new CharGrid(grid);
		assertEquals(2, cg.countPlus());
	}

	public void testPlus2(){
		char[][] grid = new char[][] {
				{' ', 'p', 'p'},
				{'p', 'p', 'p'},
				{' ', 'p', 'p'}
		};

		CharGrid cg = new CharGrid(grid);
		assertEquals(1, cg.countPlus());
	}

	public void testPlus3(){
		char[][] grid = new char[][] {
				{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{' ', ' ', 'p', ' ', ' ', ' ', ' ', 'x', ' '},
				{' ', 'p', 'p', 'p', ' ', ' ', 'x', 'x', 'x'},
				{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
				{' ', ' ', ' ', 'p', 'y', 'y', 'y', ' ', ' '},
				{'z', 'z', 'p', 'p', 'p', 'y', 'z', 'z', 'z'},
				{' ', ' ', 'x', 'p', ' ', ' ', ' ', ' ', ' '}
		};

		CharGrid cg = new CharGrid(grid);
		assertEquals(4, cg.countPlus());
	}
	public void testPlusSpaces(){
		char[][] grid = new char[][] {
				{' ', ' '},
				{' ', ' ', ' '},
				{' ', ' ', ' '}
		};

		CharGrid cg = new CharGrid(grid);
		assertEquals(1, cg.countPlus());
	}
	public void testPlusEmptyArrays(){
		char[][] grid = new char[][] {
				{},
				{}
		};

		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
	}

	public void testPlusEmpty(){
		char[][] grid = new char[][] {};

		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
	}
	public void testPlusNoPlus(){
		char[][] grid = new char[][] {
				{' ', 'a', 'b'},
				{'p', 'p', 'p'},
				{' ', 'p', 'p'},
		};

		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
	}

}
