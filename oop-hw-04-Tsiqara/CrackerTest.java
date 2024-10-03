import nl.altindag.console.ConsoleCaptor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CrackerTest{
    //tests with coverage take about 10 mins without it 30 sec
    @Test
    public void testGenerate() {
        assertEquals("34800e15707fae815d7c90d49de44aca97e2d759", Cracker.hexToString(Cracker.generateHash("a!")));
        assertEquals("adeb6f2a18fe33af368d91b09587b68e3abcb9a7", Cracker.hexToString(Cracker.generateHash("fm")));
        assertEquals("4181eecbd7a755d19fdf73887c54837cbecf63fd", Cracker.hexToString(Cracker.generateHash("molly")));
    }

    private static ConsoleCaptor consoleCaptor;

    @BeforeAll
    public static void setupConsoleCaptor() {
        consoleCaptor = new ConsoleCaptor();
    }

    @AfterEach
    public void clearOutput() {
        consoleCaptor.clearOutput();
    }

    @AfterAll
    public static void closeConsoleCaptor() {
        consoleCaptor.close();
    }

    @Test
    public void testGenerate2() {
        Cracker.main(new String[]{"fm"});
        assertTrue(consoleCaptor.getStandardOutput().contains("adeb6f2a18fe33af368d91b09587b68e3abcb9a7"));
    }

    @Test
    public void testCrack1() {
        Cracker.main(new String[]{"4181eecbd7a755d19fdf73887c54837cbecf63fd", "5", "8"});
        assertTrue(consoleCaptor.getStandardOutput().contains("molly"));
        assertTrue(consoleCaptor.getStandardOutput().contains("All done"));
    }

    @Test
    public void testCrack2() {
        Cracker.main(new String[]{Cracker.hexToString(Cracker.generateHash("mary!")), "5", "10"});
        assertTrue(consoleCaptor.getStandardOutput().contains("mary!"));
        assertTrue(consoleCaptor.getStandardOutput().contains("All done"));
    }

}
