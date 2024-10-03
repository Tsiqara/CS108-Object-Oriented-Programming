<html lang="java">
<head>
    <title>Welcome</title>
</head>
<body>
    <h1>Welcome to Homework 5</h1>
    <p>Please log in.</p>
    <form action="/login" method="post">
        <label>Username: <input type="text" name="username"></label><br><br>
        <label>Password: <input type="text" name="password"></label>
        <input type="submit" value="Login">
    </form>
    <p><a href="createAccount.jsp">Create New Account</a></p>
</body>
</html>