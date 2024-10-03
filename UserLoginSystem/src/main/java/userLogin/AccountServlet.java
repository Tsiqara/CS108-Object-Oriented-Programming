package userLogin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountManager manager = (AccountManager) getServletContext().getAttribute("manager");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if(manager.accountExists(username)){
            req.getRequestDispatcher("accountInUse.jsp").forward(req, resp);
        }else{
            manager.createAccount(username, password);
            req.getRequestDispatcher("welcome.jsp").forward(req, resp);
        }
    }
}
