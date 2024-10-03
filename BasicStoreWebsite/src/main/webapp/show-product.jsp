<%@ page import="Store.ProductCatalog" %>
<%@ page import="Store.Product" %><%--
  Created by IntelliJ IDEA.
  User: mariamtsikarishvili
  Date: 11.06.23
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ProductCatalog store = (ProductCatalog) application.getAttribute("store");
    Product product = store.getProduct(request.getParameter("id"));
%>
<html>
<head>
    <title><%=product.getName()%></title>
</head>
<body>
    <h1><%=product.getName()%></h1>
    <img alt="<%=product.getName()%>" src="<%="store-images/" + product.getImage()%>">
    <form action="/cart" method="post">
        $<%=product.getPrice()%> <input name="productID" type="hidden" value="<%=product.getId()%>"/>
        <input type="submit" value="Add to Cart">
    </form>
</body>
</html>
