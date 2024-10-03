package userLogin;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class AccountManagerTest {
    private static AccountManager manager;
    @BeforeAll
    public static void initialize(){
        manager = new AccountManager();
    }
    @Test
    public void testAccountExists(){
        assertTrue(manager.accountExists("Patrick"));
        assertTrue(manager.accountExists("Molly"));
        assertFalse(manager.accountExists("Andy"));
    }

    @Test
    public void testPassword(){
        assertTrue(manager.isPassword("Patrick", "1234"));
        assertTrue(manager.isPassword("Molly", "FloPup"));
        assertFalse(manager.isPassword("Patrick", "123"));
        assertFalse(manager.isPassword("Molly", "1234"));
        //if account does not exist returns false
        assertFalse(manager.isPassword("Mariam", "1234"));
    }

    @Test
    public void testCreateAccount(){
        //returns false if account already exists
        assertFalse(manager.createAccount("Patrick", "123456"));

        assertTrue(manager.createAccount("Mariam", "0987"));
        assertTrue(manager.accountExists("Mariam"));
        assertTrue(manager.isPassword("Mariam","0987"));
        assertFalse(manager.isPassword("Mariam","1234"));
        assertFalse(manager.createAccount("Mariam", "123"));
        assertFalse(manager.isPassword("Mariam", "123"));

        assertTrue(manager.createAccount("Keso", "1234"));
        assertTrue(manager.accountExists("Keso"));
        assertTrue(manager.isPassword("Keso","1234"));
    }
}
