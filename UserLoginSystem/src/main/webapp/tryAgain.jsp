<html lang="java">
<head>
    <title>Information Incorrect</title>
</head>
<body>
    <h1>Please Try Again</h1>
    <p>Either your username or password is incorrect. Please try again.</p>
    <form action="/login" method="post">
        <label>Username: <input type="text" name="username"></label><br><br>
        <label>Password: <input type="text" name="password"></label>
        <input type="submit" value="Login">
    </form>
    <p><a href="createAccount.jsp">Create New Account</a></p>
</body>
</html>