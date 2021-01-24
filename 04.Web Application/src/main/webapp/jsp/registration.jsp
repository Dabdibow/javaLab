<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 23.12.2020
  Time: 2:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Registration</h1>

<div>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <input name="email" placeholder="Your email">
        <input type="password" name="password" placeholder="Your password">
        <input type="submit" value="Send">
    </form>
</div>

</body>
</html>
