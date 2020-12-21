<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 10.11.2020
  Time: 10:05
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
