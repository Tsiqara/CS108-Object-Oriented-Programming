<html lang="java">
<head>
    <title>Create Account</title>
</head>
<body>
    <h1>Create New Account</h1>
    <p>Please enter username and password.</p>
    <form action="/account" method="post">
        <label>Username: <input type="text" name="username"></label><br><br>
        <label>Password: <input type="text" name="password"></label>
        <input type="submit" value="Login">
    </form>
</body>
</html>