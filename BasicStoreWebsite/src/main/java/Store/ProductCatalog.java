package Store;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductCatalog {
    private BasicDataSource dataSource;

    public ProductCatalog(BasicDataSource source){
        dataSource = source;
    }

    public ArrayList<Product> getAll(){
        ArrayList<Product> products = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from products;");

            while (result.next()) {
                Product product = new Product(
                        result.getString("productid"),
                        result.getString("name"),
                        result.getString("imagefile"),
                        result.getBigDecimal("price")
                );
                products.add(product);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public Product getProduct(String id){
        Product product = null;
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from products where productid = \"" + id + "\";");

            if (result.next()) {
                product = new Product(
                        result.getString("productid"),
                        result.getString("name"),
                        result.getString("imagefile"),
                        result.getBigDecimal("price")
                );
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

}
