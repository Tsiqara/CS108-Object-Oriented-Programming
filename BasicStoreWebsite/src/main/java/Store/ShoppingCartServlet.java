package Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

public class ShoppingCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        ProductCatalog catalog = (ProductCatalog) req.getServletContext().getAttribute("store");
        String id = req.getParameter("productID");
        if(id != null){
            cart.addProduct(catalog.getProduct(id), 1);
        }else{
            ShoppingCart updatedCart = new ShoppingCart();
            Enumeration<String> enumeration = req.getParameterNames();
            while (enumeration.hasMoreElements()){
                id = enumeration.nextElement();
                updatedCart.addProduct(catalog.getProduct(id), Integer.parseInt(req.getParameter(id)));
            }
            cart = updatedCart;
        }
        session.setAttribute("cart", cart);
        req.getRequestDispatcher("shoppingCart.jsp").forward(req, resp);
    }
}
