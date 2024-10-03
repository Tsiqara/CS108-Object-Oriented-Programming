package Store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;


public class ProductCatalogTest {
    private static ProductCatalog catalog;
    @BeforeAll
    public static void initialize(){
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/hw_5");
        dataSource.setUsername("root");
        dataSource.setPassword("123456789");
        dataSource.setMaxTotal(-1);

        catalog = new ProductCatalog(dataSource);
    }

    @Test
    public void testGetAll(){
        ArrayList<Product> res = catalog.getAll();
        assertEquals(14,res.size());
        //classic hoodie
        Product hoodie = res.get(0);
        assertEquals("HC", hoodie.getId());
        assertEquals("Classic Hoodie", hoodie.getName());
        assertEquals("Hoodie.jpg", hoodie.getImage());
        assertEquals(40, hoodie.getPrice().intValue());

        //Thermos
        Product therm = res.get(12);
        assertEquals("ATherm", therm.getId());
        assertEquals("Thermos", therm.getName());
        assertEquals("Thermos.jpg", therm.getImage());
        assertEquals(19.95, therm.getPrice().doubleValue());
    }

    @Test
    public void testGetProduct(){
        Product miniF = catalog.getProduct("AMinHm");
        assertEquals("AMinHm", miniF.getId());
        assertEquals("Mini Football Helmet", miniF.getName());
        assertEquals("MiniHelmet.jpg", miniF.getImage());
        assertEquals(29.95, miniF.getPrice().doubleValue());
    }

    @Test
    public void testGetProductWrong() {
        Product miniF = catalog.getProduct("AB");
        assertNull(miniF);
    }
}
