package Store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {
    private static ShoppingCart sc;
    @BeforeEach
    public void initialize(){
        sc = new ShoppingCart();
    }

    @Test
    public void testIsEmpty(){
        assertTrue(sc.cart.isEmpty());
        assertEquals(sc.total.intValue(), 0);
    }

    @Test
    public void addProductTest(){
        Product pr = new Product("AB", "Aba", "", new BigDecimal(10));
        sc.addProduct(pr, 1);
        assertEquals(sc.cart.size(), 1);
        assertEquals(sc.total.intValue(), 10);
        assertEquals(sc.cart.get(pr), 1);

        sc.addProduct(pr, 2);
        assertEquals(sc.cart.size(), 1);
        assertEquals(sc.total.intValue(), 30);
        assertEquals(sc.cart.get(pr), 3);

        pr = new Product("BC", "bcd", "", new BigDecimal(15));
        sc.addProduct(pr, 2);
        assertEquals(sc.cart.size(), 2);
        assertEquals(sc.total.intValue(), 60);
        assertEquals(sc.cart.get(pr), 2);
    }

    @Test
    public void addZeroProductTest(){
        Product pr = new Product("AB", "Aba", "", new BigDecimal(10));
        sc.addProduct(pr, 1);
        assertEquals(sc.cart.size(), 1);
        assertEquals(sc.total.intValue(), 10);
        assertEquals(sc.cart.get(pr), 1);

        pr = new Product("BC", "bcd", "", new BigDecimal(15));
        sc.addProduct(pr, 2);
        assertEquals(2, sc.cart.size());
        assertEquals(40, sc.total.intValue());
        assertEquals(sc.cart.get(pr), 2);

        sc.addProduct(pr, 0);
        assertEquals(1, sc.cart.size());
        assertEquals(10, sc.total.intValue());
        assertTrue(!sc.cart.containsKey(pr));
    }
}
