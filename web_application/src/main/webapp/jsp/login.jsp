<%--
  Created by IntelliJ IDEA.
  User: bulat
  Date: 19.10.2020
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login</h1>
<form method="post" action="/login">
    <fieldset>
        <legend>Your credentials</legend>
        <label>Username <input name = "username" type="text" required></label>
        <label>Password <input name="password" type="password" required></label>
    </fieldset>
    <input type="submit" value="Enter">
</form>
</body>
</html>
