import junit.framework.TestCase;

public class SudokuTest extends TestCase {
    private static final int[][] completedGrid = Sudoku.stringsToGrid(
            "974236158",
            "638591742",
            "125487936",
            "316754289",
            "742918563",
            "589362417",
            "867125394",
            "253649871",
            "491873625");

    //This puzzle requires only a single value to be placed on the grid in order to reach the unique solution
    private static final int[][] oneEmptyGrid =
            Sudoku.textToGrid("256489173374615982981723456593274861712806549468591327635147298127958634849362715");

    //This puzzle can be solved using only ‘Naked Singles’—also called ‘sole candidates’—which occur when a square has only one possible value remaining.
    // The most obvious example of a Naked Single is the last square in any box, column, or row.
    private static final int[][] nakedSinglesGrid =
            Sudoku.textToGrid("305420810487901506029056374850793041613208957074065280241309065508670192096512408");

    //This puzzle requires ‘Hidden Singles’ to solve, which occur when a box, column, or row has only one possible square remaining for a given value.
    private static final int[][] hiddenSinglesGrid =
            Sudoku.textToGrid("002030008000008000031020000060050270010000050204060031000080605000000013005310400");

    private static final int[][] easyGrid = Sudoku.stringsToGrid(
            "1 6 4 0 0 0 0 0 2",
            "2 0 0 4 0 3 9 1 0",
            "0 0 5 0 8 0 4 0 7",
            "0 9 0 0 0 6 5 0 0",
            "5 0 0 1 0 2 0 0 8",
            "0 0 8 9 0 0 0 3 0",
            "8 0 9 0 4 0 2 0 0",
            "0 7 3 5 0 9 0 0 1",
            "4 0 0 0 0 0 6 7 9");

    private static final int[][] mediumGrid = Sudoku.stringsToGrid(
            "530070000",
            "600195000",
            "098000060",
            "800060003",
            "400803001",
            "700020006",
            "060000280",
            "000419005",
            "000080079");

    private static final int[][] hardGrid = Sudoku.stringsToGrid(
            "3 7 0 0 0 0 0 8 0",
            "0 0 1 0 9 3 0 0 0",
            "0 4 0 7 8 0 0 0 3",
            "0 9 3 8 0 0 0 1 2",
            "0 0 0 0 4 0 0 0 0",
            "5 2 0 0 0 6 7 9 0",
            "6 0 0 0 2 1 0 4 0",
            "0 0 0 5 3 0 9 0 0",
            "0 3 0 0 0 0 0 5 1");

    private static final int[][] emptyGrid = Sudoku.stringsToGrid(
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0");

    private static final int[][] numerousSolutionsGrid1 = Sudoku.stringsToGrid(
            "3 0 0 0 0 0 0 8 0",
            "0 0 1 0 9 3 0 0 0",
            "0 4 0 7 8 0 0 0 3",
            "0 9 3 8 0 0 0 1 2",
            "0 0 0 0 4 0 0 0 0",
            "5 2 0 0 0 6 7 9 0",
            "6 0 0 0 2 1 0 4 0",
            "0 0 0 5 3 0 9 0 0",
            "0 3 0 0 0 0 0 5 1");

    private static final String numerousSolutionsGrid2 = "590000048608000307000201000000040000075306980000090000000803000206000709340000065";
    private static final String expertGrid = "000316500800500100010897240901085020000901000040263001050000010100409002006108000";

    private static final String wrongGrid = "12313142000";


    public void testCompleteGrid(){
        Sudoku complete = new Sudoku(completedGrid);
        assertEquals(1, complete.solve());
    }

    public void testOneEmptyGrid(){
        Sudoku oneEmpty = new Sudoku(oneEmptyGrid);
        assertEquals(1, oneEmpty.solve());
    }

    public void testNakedSinglesGrid(){
        Sudoku nakedSingles = new Sudoku(nakedSinglesGrid);
        assertEquals(1, nakedSingles.solve());
    }

    public void testHiddenSinglesGrid(){
        Sudoku hiddenSingles = new Sudoku(hiddenSinglesGrid);
        assertEquals(1, hiddenSingles.solve());
    }

    public void testEasyGrid(){
        Sudoku easy = new Sudoku(easyGrid);
        assertEquals(1, easy.solve());
    }

    public void testMediumGrid(){
        Sudoku medium = new Sudoku(mediumGrid);
        assertEquals(1, medium.solve());
    }

    public void testHardGrid(){
        Sudoku hard = new Sudoku(hardGrid);
        assertEquals(1, hard.solve());
    }

    public void testEmptyGrid(){
        Sudoku empty = new Sudoku(emptyGrid);
        assertEquals(Sudoku.MAX_SOLUTIONS, empty.solve());
    }
    public void testNumerousSolutionsGrid1(){
        Sudoku numerous = new Sudoku(numerousSolutionsGrid1);
        assertEquals(6, numerous.solve());
    }

    public void testExpertGrid(){
        Sudoku expert = new Sudoku(expertGrid);
        assertEquals(Math.min(Sudoku.MAX_SOLUTIONS, 125), expert.solve());
    }

    public void testNumerousSolutionsGrid2(){
        Sudoku numerous = new Sudoku(numerousSolutionsGrid2);
        assertEquals(10, numerous.solve());
    }

    public void testException(){
        try {
            Sudoku wrong = new Sudoku(wrongGrid);
        }catch (Exception e){
            assertEquals(e.getMessage(), "Needed 81 numbers, but got:" + wrongGrid.length());
        }
    }
}
