package userLogin;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        AccountManager manager = new AccountManager();
        servletContextEvent.getServletContext().setAttribute("manager", manager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
