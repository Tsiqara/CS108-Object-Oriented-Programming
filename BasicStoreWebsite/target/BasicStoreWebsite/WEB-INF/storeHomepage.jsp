<%@ page import="Store.Product" %>
<%@ page import="Store.ProductCatalog" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: mariamtsikarishvili
  Date: 11.06.23
  Time: 18:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Store</title>
</head>
<body>
    <h1>Student Store</h1><br>
    <p>Items available:</p>
    <ul>
        <%
            ProductCatalog store = (ProductCatalog) request.getAttribute("store");
            ArrayList<Product> products = store.getAll();
            for (Product product: products) {
                String id = product.getId();
                String name = product.getName();
                System.out.println("<li> <a href=\"show-product.jsp?id=" + id + "\">" + name + "</a></li>");
            }
        %>
    </ul>
</body>
</html>
