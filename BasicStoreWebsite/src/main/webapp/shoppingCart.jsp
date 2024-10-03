<%@ page import="Store.ShoppingCart" %>
<%@ page import="Store.Product" %><%--
  Created by IntelliJ IDEA.
  User: mariamtsikarishvili
  Date: 11.06.23
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>
    <h1>Shopping Cart</h1>
    <form action="/cart" method="post">
        <ul>
            <%
                ShoppingCart sc = (ShoppingCart) session.getAttribute("cart");
                for (Product product: sc.cart.keySet()) {
                    out.println("<li> <input type = 'number' value='" + sc.cart.get(product) + "' name = '"
                            + product.getId() + "'> " + product.getName() + ", " + product.getPrice() + " </li>");
                }
            %>
        </ul>
        Total: $<%=sc.total%> <input type="submit" value="Update Cart">
    </form>
    <a href="storeHomepage.jsp"> Continue Shopping</a>
</body>
</html>
