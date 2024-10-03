package Store;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class ContextListener implements ServletContextListener, HttpSessionListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/hw_5");
        dataSource.setUsername("root");
        dataSource.setPassword("123456789");
        dataSource.setMaxTotal(-1);

        ProductCatalog store = new ProductCatalog(dataSource);
        servletContextEvent.getServletContext().setAttribute("store", store);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        ShoppingCart cart = new ShoppingCart();
        httpSessionEvent.getSession().setAttribute("cart", cart);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().removeAttribute("cart");
    }
}
