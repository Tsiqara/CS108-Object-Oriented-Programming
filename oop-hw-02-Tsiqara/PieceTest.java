import junit.framework.TestCase;

import java.io.PipedWriter;
import java.util.*;

import static java.sql.JDBCType.NULL;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest extends TestCase {
	// You can create data to be used in your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s, sRotated;
	private Piece st1, st2, st3, st1R, st2R, st3R;
	private Piece l1_1, l1_2, l1_3, l1_1R1, l1_2R1, l1_3R1, l1_1R2, l1_2R2, l1_3R2, l1_1R3, l1_2R3, l1_3R3;
	private Piece l2_1, l2_2, l2_1R1, l2_2R1, l2_1R2, l2_2R2, l2_2R3, l2_1R3;
	private Piece s1, s2, s1R, s2R;
	private Piece square, squareR;

	protected void setUp() throws Exception {
		super.setUp();
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();

		//Stick with different constructors
		st1 = new Piece(Piece.STICK_STR);
		st2 = new Piece("0 0	0 1	 0 2  0 3");
		st3 = Piece.getPieces()[Piece.STICK];
		st1R = st1.computeNextRotation();
		st2R = st2.computeNextRotation();
		st3R = st3.computeNextRotation();

		//L1 with different constructors
		l1_1 = new Piece(Piece.L1_STR);
		l1_2 = new Piece("0 0	0 1	 0 2  1 0");
		l1_3 = Piece.getPieces()[Piece.L1];

		l1_1R1 = l1_1.computeNextRotation();
		l1_2R1 = l1_2.computeNextRotation();
		l1_3R1 = l1_3.computeNextRotation();

		l1_1R2 = l1_1R1.computeNextRotation();
		l1_2R2 = l1_2R1.computeNextRotation();
		l1_3R2 = l1_3R1.computeNextRotation();

		l1_1R3 = l1_1R2.computeNextRotation();
		l1_2R3 = l1_2R2.computeNextRotation();
		l1_3R3 = l1_3R2.computeNextRotation();

		//L2 with different constructors
		l2_1 = new Piece(Piece.L2_STR);
		l2_2 = new Piece("0 0	1 0 1 1	 1 2");

		l2_1R1 = l2_1.computeNextRotation();
		l2_2R1 = l2_2.computeNextRotation();
		l2_1R2 = l2_1R1.computeNextRotation();
		l2_2R2 = l2_2R1.computeNextRotation();
		l2_1R3 = l2_1R2.computeNextRotation();
		l2_2R3 = l2_2R2.computeNextRotation();

		s1 = new Piece(Piece.S1_STR);
		s2 = new Piece(Piece.S2_STR);
		s1R = s1.computeNextRotation();
		s2R = s2.computeNextRotation();

		square = new Piece(Piece.SQUARE_STR);
		squareR = square.computeNextRotation();
	}
	
	// Here are some sample tests to get you started
	
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());

		assertEquals(3, pyr3.getWidth());
		assertEquals(2, pyr3.getHeight());

		assertEquals(2, pyr4.getWidth());
		assertEquals(3, pyr4.getHeight());
		
		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
	}
	
	
	// Test the skirt returned by a few pieces
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));
	}

	public void testStickSize(){
		assertEquals(1, st1.getWidth());
		assertEquals(1, st2.getWidth());
		assertEquals(1, st3.getWidth());

		assertEquals(4, st1.getHeight());
		assertEquals(4, st2.getHeight());
		assertEquals(4, st3.getHeight());

		//One Rotation
		assertEquals(4, st1R.getWidth());
		assertEquals(4, st2R.getWidth());
		assertEquals(4, st3R.getWidth());

		assertEquals(1, st1R.getHeight());
		assertEquals(1, st2R.getHeight());
		assertEquals(1, st3R.getHeight());

		//Back to original
		assertEquals(1, (st1R.computeNextRotation()).getWidth());
		assertEquals(1, (st2R.computeNextRotation()).getWidth());
		assertEquals(1, (st2R.computeNextRotation()).getWidth());

		assertEquals(4, (st1R.computeNextRotation()).getHeight());
		assertEquals(4, (st2R.computeNextRotation()).getHeight());
		assertEquals(4, (st2R.computeNextRotation()).getHeight());
	}

	public void testL1Size(){
		//L1 with different constructors
		assertEquals(2, l1_1.getWidth());
		assertEquals(2, l1_2.getWidth());
		assertEquals(2, l1_3.getWidth());

		assertEquals(3, l1_1.getHeight());
		assertEquals(3, l1_2.getHeight());
		assertEquals(3, l1_3.getHeight());

		// One Rotation
		assertEquals(3, l1_1R1.getWidth());
		assertEquals(3, l1_2R1.getWidth());
		assertEquals(3, l1_3R1.getWidth());

		assertEquals(2, l1_1R1.getHeight());
		assertEquals(2, l1_2R1.getHeight());
		assertEquals(2, l1_3R1.getHeight());

		//Two Rotations
		assertEquals(2, l1_1R2.getWidth());
		assertEquals(2, l1_2R2.getWidth());
		assertEquals(2, l1_3R2.getWidth());

		assertEquals(3, l1_1R2.getHeight());
		assertEquals(3, l1_2R2.getHeight());
		assertEquals(3, l1_3R2.getHeight());

		//Three Rotations
		assertEquals(3, l1_1R3.getWidth());
		assertEquals(3, l1_2R3.getWidth());
		assertEquals(3, l1_3R3.getWidth());

		assertEquals(2, l1_1R3.getHeight());
		assertEquals(2, l1_2R3.getHeight());
		assertEquals(2, l1_3R3.getHeight());

		//Back to original
		assertEquals(2, (l1_1R3.computeNextRotation()).getWidth());
		assertEquals(2, (l1_2R3.computeNextRotation()).getWidth());
		assertEquals(2, (l1_3R3.computeNextRotation()).getWidth());

		assertEquals(3, l1_1R3.computeNextRotation().getHeight());
		assertEquals(3, l1_2R3.computeNextRotation().getHeight());
		assertEquals(3, l1_3R3.computeNextRotation().getHeight());
	}

	public void testL2Size(){
		assertEquals(2, l2_1.getWidth());
		assertEquals(2, l2_2.getWidth());

		assertEquals(3, l2_1.getHeight());
		assertEquals(3, l2_2.getHeight());

		// One Rotation
		assertEquals(3, l2_1R1.getWidth());
		assertEquals(3, l2_2R1.getWidth());

		assertEquals(2, l2_1R1.getHeight());
		assertEquals(2, l2_2R1.getHeight());

		//Two Rotations
		assertEquals(2, l2_1R2.getWidth());
		assertEquals(2, l2_2R2.getWidth());

		assertEquals(3, l2_1R2.getHeight());
		assertEquals(3, l2_2R2.getHeight());

		//Three Rotations
		assertEquals(3, l2_1R3.getWidth());
		assertEquals(3, l2_2R3.getWidth());

		assertEquals(2, l2_1R3.getHeight());
		assertEquals(2, l2_2R3.getHeight());

		//Back to original
		assertEquals(2, (l2_1R3.computeNextRotation()).getWidth());
		assertEquals(2, (l2_2R3.computeNextRotation()).getWidth());

		assertEquals(3, l2_1R3.computeNextRotation().getHeight());
		assertEquals(3, l2_2R3.computeNextRotation().getHeight());
	}

	public void testSSize(){
		assertEquals(3, s1.getWidth());
		assertEquals(3, s2.getWidth());

		assertEquals(2, s1.getHeight());
		assertEquals(2, s2.getHeight());

		// One Rotation
		assertEquals(2, s1R.getWidth());
		assertEquals(2, s2R.getWidth());

		assertEquals(3, s1R.getHeight());
		assertEquals(3, s2R.getHeight());

		//Back to original
		assertEquals(3, s1R.computeNextRotation().getWidth());
		assertEquals(3, s2R.computeNextRotation().getWidth());

		assertEquals(2, s1R.computeNextRotation().getHeight());
		assertEquals(2, s2R.computeNextRotation().getHeight());
	}

	public void testSquareSize(){
		assertEquals(2, square.getWidth());
		assertEquals(2, square.getHeight());

		assertEquals(2, squareR.getWidth());
		assertEquals(2, squareR.getHeight());
	}

	public void testSkirt(){
		//Stick
		assertTrue(Arrays.equals(new int[] {0}, st1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0, 0}, st1R.getSkirt()));

		//L1
		assertTrue(Arrays.equals(new int[] {0, 0}, l1_1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, l1_1R1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {2, 0}, l1_1R2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1, 1}, l1_1R3.getSkirt()));

		//L2
		assertTrue(Arrays.equals(new int[] {0, 0}, l2_1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 1, 0}, l2_1R1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 2}, l2_1R2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, l2_1R3.getSkirt()));

		//S1
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, s1R.getSkirt()));

		//S2
		assertTrue(Arrays.equals(new int[] {1, 0, 0}, s2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1}, s2R.getSkirt()));

		//Square
		assertTrue(Arrays.equals(new int[] {0, 0}, square.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0}, squareR.getSkirt()));

		//Pyramid
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, pyr2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1}, pyr4.getSkirt()));
	}

	public void testEquals(){
		assertTrue(st1.equals(st1));
		assertTrue(st1.equals(st2));
		assertTrue(st1.equals(st3));
		assertTrue(st1R.equals(st2R));
		assertTrue(st1R.equals(st3R));

		assertTrue(l1_1.equals(l1_1));
		assertTrue(l1_1.equals(l1_2));
		assertTrue(l1_1.equals(l1_3));
		assertTrue(l1_1R1.equals(l1_2R1));
		assertTrue(l1_1R1.equals(l1_3R1));
		assertTrue(l1_1R2.equals(l1_2R2));
		assertTrue(l1_1R3.equals(l1_3R3));
		assertTrue(l1_1.equals(l1_1R3.computeNextRotation()));
		assertTrue(l1_1.equals(l1_2R3.computeNextRotation()));

		assertTrue(l2_1.equals(l2_2));
		assertTrue(l2_1R1.equals(l2_2R1));
		assertTrue(l2_1R2.equals(l2_2R2));
		assertTrue(l2_1R3.equals(l2_2R3));
		assertTrue(l2_1.equals(l2_2R3.computeNextRotation()));

		assertTrue(s1.equals(s1R.computeNextRotation()));
		assertTrue(s2.equals(s2R.computeNextRotation()));

		assertTrue(square.equals(squareR));
		assertTrue(square.equals(squareR.computeNextRotation()));

		assertTrue(pyr1.equals(pyr4.computeNextRotation()));
	}

	public void testEqualsFalse(){
		assertTrue(!st1.equals(st1R));

		assertTrue(!l1_1.equals(square));
		assertTrue(!l1_1.equals(pyr1));
		assertTrue(!l1_1.equals(l1_2R3));

		assertTrue(!s1.equals(s2));
		assertTrue(!s1.equals(s2R));
		assertTrue(!s1.equals(s1R));

		assertTrue(!pyr1.equals(pyr3));

		//not instance of Piece
		assertTrue(!st1.equals(new Integer(5)));
		assertEquals(false, s2.equals(new String ("0 1	1 1  1 0  2 0")));
	}

	public void testConstructors(){
		//empty
		try{
			Piece p =  new Piece(new TPoint[]{});
		}catch(RuntimeException e){
			assertEquals( "Array should not be empty", e.getMessage());
		}
		try{
			Piece p = new Piece("");
		}catch(RuntimeException e){
			assertEquals( "Array should not be empty", e.getMessage());
		}

		assertTrue(s1.equals(new Piece("0 0	1 0	 1 1  2 1")));
		assertTrue(s1.equals(Piece.getPieces()[Piece.S1]));

		assertTrue(s2.equals(Piece.getPieces()[Piece.S2]));
		assertEquals(true, s2.equals(new Piece ("0 1	1 1  1 0  2 0")));
		assertTrue(square.equals(Piece.getPieces()[Piece.SQUARE]));

		assertTrue(pyr1.equals(Piece.getPieces()[Piece.PYRAMID]));
		assertTrue(pyr1.equals(new Piece(Piece.PYRAMID_STR)));

		String incorrectString = "0 1 a 3";
		try{
			Piece p = new Piece(incorrectString);
		}catch(RuntimeException e){
			assertEquals("Could not parse x,y string:" + incorrectString, e.getMessage());
		}

		String incorrectString1 = "1 2 0 1 4";
		try {
			Piece p1 = new Piece(incorrectString1);
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}


	public void testFastRotation(){
		Piece[] pieces = Piece.getPieces();
		assertEquals(st1R, pieces[Piece.STICK].fastRotation());
		assertEquals(st1, pieces[Piece.STICK].fastRotation().fastRotation());

		assertEquals(l1_1R1, pieces[Piece.L1].fastRotation());
		assertEquals(l1_1R2, pieces[Piece.L1].fastRotation().fastRotation());
		assertEquals(l1_1R3,  pieces[Piece.L1].fastRotation().fastRotation().fastRotation());
		assertEquals(l1_1, pieces[Piece.L1].fastRotation().fastRotation().fastRotation().fastRotation());

		assertEquals(l2_1R1, pieces[Piece.L2].fastRotation());
		assertEquals(l2_1R2, pieces[Piece.L2].fastRotation().fastRotation());
		assertEquals(l2_1R3, pieces[Piece.L2].fastRotation().fastRotation().fastRotation());
		assertEquals(l2_1, pieces[Piece.L2].fastRotation().fastRotation().fastRotation().fastRotation());

		assertEquals(s1R, pieces[Piece.S1].fastRotation());
		assertEquals(s1, pieces[Piece.S1].fastRotation().fastRotation());
		assertEquals(s2R, pieces[Piece.S2].fastRotation());
		assertEquals(s2, pieces[Piece.S2].fastRotation().fastRotation());

		assertEquals(squareR, pieces[Piece.SQUARE].fastRotation());

		assertEquals(pyr2, pieces[Piece.PYRAMID].fastRotation());
		assertEquals(pyr3, pieces[Piece.PYRAMID].fastRotation().fastRotation());
		assertEquals(pyr4, pieces[Piece.PYRAMID].fastRotation().fastRotation().fastRotation());
		assertEquals(pyr1, pieces[Piece.PYRAMID].fastRotation().fastRotation().fastRotation().fastRotation());

	}
}
