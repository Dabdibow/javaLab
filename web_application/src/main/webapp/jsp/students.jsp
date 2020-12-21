<%--
  Created by IntelliJ IDEA.
  User: bulat
  Date: 19.10.2020
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.util.List" %>
<%@ page import="ru.itis.javalab.models.Student" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Students</title>
</head>
<body>
<h1 style="color: ${cookie.get("color").value}">Students</h1>
<form action="/students" method="post">
    <select name="color">
        <option value="red">RED</option>
        <option value="green">GREEN</option>
        <option value="blue">BLUE</option>
    </select>
    <input type="submit" value="OK">
</form>
<table>
    <th>ID</th>
    <th>FIRST NAME</th>
    <th>LAST NAME</th>
    <th>AGE</th>
    <th>GROUP_NUMBER</th>
    <%
        List<Student> students = (List<Student>) request.getAttribute("studentsForJsp");
        for (int i = 0; i < students.size(); i++) {
    %>
    <tr>
        <td><%=students.get(i).getId()%>
        </td>
        <td><%=students.get(i).getFirstName()%>
        </td>
        <td><%=students.get(i).getLastName()%>
        </td>
        <td><%=students.get(i).getAge()%>
        </td>
        <td><%=students.get(i).getGroupNumber()%>
        </td>
    </tr>
    <%}%>
</table>
</body>
</html>