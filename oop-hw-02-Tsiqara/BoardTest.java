import junit.framework.TestCase;


public class BoardTest extends TestCase {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;
	Piece stick, stickR;
	Piece l1, l1R1, l1R2, l1R3, l2, l2R1, l2R2, l2R3;
	Piece s2, s2R;
	Piece square;

	// This shows how to build things in setUp() to re-use
	// across tests.
	
	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	
	protected void setUp() throws Exception {
		b = new Board(3, 6);
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();

		stick = new Piece(Piece.STICK_STR);
		stickR = stick.computeNextRotation();

		l1 = new Piece(Piece.L1_STR);
		l1R1 = l1.computeNextRotation();
		l1R2 = l1R1.computeNextRotation();
		l1R3 = l1R2.computeNextRotation();

		l2 = new Piece(Piece.L2_STR);
		l2R1 = l2.computeNextRotation();
		l2R2 = l2R1.computeNextRotation();
		l2R3 = l2R2.computeNextRotation();

		s2 = new Piece(Piece.S2_STR);
		s2R = s2.computeNextRotation();

		square = new Piece(Piece.SQUARE_STR);

		b.place(pyr1, 0, 0);
	}
	
	// Check the basic width/height/max after the one placement
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}
	
	// Place sRotated into the board, then check some measures
	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}
	
	// Make  more tests, by putting together longer series of
	// place, clearRows, undo, place ... checking a few col/row/max
	// numbers that the board looks right after the operations.

	//check dropHeights() function
	public void testDropHeights(){
		assertEquals(2, b.dropHeight(sRotated, 0));
		assertEquals(1, b.dropHeight(sRotated, 1));
		assertEquals(Board.PLACE_OUT_BOUNDS, b.dropHeight(sRotated, 2));

		assertEquals(2, b.dropHeight(s, 0));
		assertEquals(Board.PLACE_OUT_BOUNDS, b.dropHeight(s, 1));

		assertEquals(2, b.dropHeight(pyr1, 0));
		assertEquals(Board.PLACE_OUT_BOUNDS, b.dropHeight(pyr1, 1));

		assertEquals(Board.PLACE_OUT_BOUNDS, b.dropHeight(pyr2, 0));
		assertEquals(2, b.dropHeight(pyr2, 0));
		assertEquals(1, b.dropHeight(pyr2, 1));
		assertEquals(Board.PLACE_OUT_BOUNDS, b.dropHeight(pyr2, 2));

		assertEquals(2, b.dropHeight(pyr3, 0));
		assertEquals(Board.PLACE_OUT_BOUNDS, b.dropHeight(pyr3, 1));

		assertEquals(1, b.dropHeight(pyr4, 0));
		assertEquals(2, b.dropHeight(pyr4, 1));
		assertEquals(Board.PLACE_OUT_BOUNDS, b.dropHeight(pyr4, 2));

		assertEquals(1, b.dropHeight(stick, 0));
		assertEquals(2, b.dropHeight(stick, 1));
		assertEquals(1, b.dropHeight(stick, 2));

		assertEquals(Board.PLACE_OUT_BOUNDS, b.dropHeight(stickR, 0));
		assertEquals(Board.PLACE_OUT_BOUNDS, b.dropHeight(stickR, 1));
		assertEquals(Board.PLACE_OUT_BOUNDS, b.dropHeight(stickR, 2));

		assertEquals(2, b.dropHeight(l1, 0));
		assertEquals(2, b.dropHeight(l1, 1));
		assertEquals(Board.PLACE_OUT_BOUNDS, b.dropHeight(l1, 2));

		assertEquals(2, b.dropHeight(l1R1, 0));
		assertEquals(Board.PLACE_OUT_BOUNDS, b.dropHeight(l1R1, 1));

		assertEquals(2, b.dropHeight(l1R2, 0));
		assertEquals(1, b.dropHeight(l1R2, 1));

		assertEquals(1, b.dropHeight(l1R3, 0));

		assertEquals(2, b.dropHeight(square, 0));
		assertEquals(2, b.dropHeight(square, 1));

		assertEquals(2, b.dropHeight(l2, 0));
		assertEquals(2, b.dropHeight(l2, 1));
		assertEquals(Board.PLACE_OUT_BOUNDS, b.dropHeight(l2, 2));

		assertEquals(1, b.dropHeight(l2R1, 0));

		assertEquals(1, b.dropHeight(l2R2, 0));
		assertEquals(2, b.dropHeight(l2R2, 1));
		assertEquals(2, b.dropHeight(l2R3, 0));
		assertEquals(Board.PLACE_OUT_BOUNDS, b.dropHeight(l2R3, 1));

		assertEquals(2, b.dropHeight(s2, 0));
		assertEquals(1, b.dropHeight(s2R, 0));
		assertEquals(2, b.dropHeight(s2R, 1));
	}

	//Place pyr3 into the board and check measures
	public void testPlaceSimple(){
		b.commit();
		int res = b.place(pyr3, 0, 2);
		assertEquals(Board.PLACE_ROW_FILLED, res);
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(1, b.getRowWidth(2));
		assertEquals(3, b.getRowWidth(3));
		assertEquals(0, b.getRowWidth(4));

		assertEquals(4, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(4, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());

		assertEquals(true, b.getGrid(1,3));
		assertEquals(false, b.getGrid(0,2));
		assertEquals(false, b.getGrid(2,1));
		assertEquals(true, b.getGrid(4,5));
	}

	public void testClearRows(){
		b.commit();
		int res = b.place(l1R1, 0, b.dropHeight(l1R1, 0));
		assertEquals(Board.PLACE_ROW_FILLED, res);
		int deleted = b.clearRows();
		assertEquals(2, deleted);
		assertEquals(1, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
		assertEquals(0, b.getRowWidth(3));

		assertEquals(0, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(2, b.getColumnHeight(2));
	}


	// do place()/clearRows() series, and then an undo() to see that the board gets back to the right state
	public void testPlaceClearRowsAndUndo(){
		b.commit();
		int res = b.place(l2R1, 0, 1);
		assertEquals(Board.PLACE_ROW_FILLED, res);

		assertEquals(3, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(1));
		assertEquals(3, b.getRowWidth(2));
		assertEquals(0, b.getRowWidth(3));

		assertEquals(3, b.getColumnHeight(0));
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(3, b.getMaxHeight());

		int deleted = b.clearRows();
		assertEquals(2, deleted);

		assertEquals(2, b.getRowWidth(0));
		assertEquals(0, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));

		assertEquals(0, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(1, b.getColumnHeight(2));
		assertEquals(1, b.getMaxHeight());

		b.undo();

		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));

		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(1, b.getColumnHeight(2));
		assertEquals(2, b.getMaxHeight());
	}

	public void testAdvanced1(){
		b.commit();
		int res = b.place(s2R, 0, 1);
		assertEquals(Board.PLACE_OK, res);
		int deleted = b.clearRows();
		assertEquals(1, deleted);

		assertEquals(2, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(1));
		assertEquals(1, b.getRowWidth(2));
		assertEquals(0, b.getRowWidth(3));

		assertEquals(2, b.getColumnHeight(0));
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(0, b.getColumnHeight(2));
		assertEquals(3, b.getMaxHeight());

		b.undo();

		res = b.place(l2, 1, 2);
		assertEquals(Board.PLACE_OK, res);
		deleted = b.clearRows();
		assertEquals(1, deleted);

		b.commit();

		res = b.place(l2R2, 0, 0);
		assertEquals(Board.PLACE_ROW_FILLED, res);

		assertEquals(2, b.getRowWidth(0));
		assertEquals(3, b.getRowWidth(1));
		assertEquals(3, b.getRowWidth(2));
		assertEquals(1, b.getRowWidth(3));
		assertEquals(0, b.getRowWidth(4));

		assertEquals(3, b.getColumnHeight(0));
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(4, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());

		deleted = b.clearRows();
		assertEquals(2, deleted);
		assertEquals(2, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));

		assertEquals(1, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(2, b.getColumnHeight(2));
		assertEquals(2, b.getMaxHeight());

		deleted = b.clearRows();
		assertEquals(0, deleted);
		assertEquals(2, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));

		assertEquals(1, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(2, b.getColumnHeight(2));
		assertEquals(2, b.getMaxHeight());
	}

	public void testAdvanced2() {
		b.commit();
		int res = b.place(stick, 0, 1);
		assertEquals(Board.PLACE_OK, res);
		int deleted = b.clearRows();
		assertEquals(1, deleted);

		assertEquals(2, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(1, b.getRowWidth(2));
		assertEquals(1, b.getRowWidth(3));

		assertEquals(4, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(0, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());

		b.commit();

		res = b.place(square, 1, 1);
		assertEquals(Board.PLACE_ROW_FILLED, res);

		assertEquals(2, b.getRowWidth(0));
		assertEquals(3, b.getRowWidth(1));
		assertEquals(3, b.getRowWidth(2));
		assertEquals(1, b.getRowWidth(3));

		assertEquals(4, b.getColumnHeight(0));
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());

		deleted = b.clearRows();
		assertEquals(2, deleted);

		assertEquals(2, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));

		assertEquals(2, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(0, b.getColumnHeight(2));
		assertEquals(2, b.getMaxHeight());
	}

	public void testBadPlacement(){
		b.commit();
		//out of bound
		int res = b.place(stickR, 0, 0);
		assertEquals(Board.PLACE_OUT_BOUNDS, res);
		b.undo();

		//overlaps
		res = b.place(square, 1, 1);
		assertEquals(Board.PLACE_BAD, res);
		b.undo();

		//good placement
		res = b.place(s, 0, 2);
		assertEquals(Board.PLACE_OK, res);
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(2, b.getRowWidth(2));
		assertEquals(2, b.getRowWidth(3));

		assertEquals(3, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(4, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}

	public void testPlaceNotCommitted(){
		try{
			int res = b.place(s2, 0, 2);
		}catch(RuntimeException e){
			assertEquals("place commit problem", e.getMessage());
		}
	}

	public void testClearBeforePlace(){
		b.clearRows();
		assertEquals(3, b.getWidth());
		assertEquals(6, b.getHeight());

		assertEquals(1, b.getRowWidth(0));
		assertEquals(0, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));

		assertEquals(0, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(0, b.getColumnHeight(2));
		assertEquals(1, b.getMaxHeight());
	}
}
