<%--
  Created by IntelliJ IDEA.
  User: alexa
  Date: 17/11/2024
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<% if (request.getAttribute("msg") != null) { %>
<h3><%= request.getAttribute("msg") %>
</h3>
<% } %>
<form action="/login" method="post">
    email: <input type="text" name="email" required><br>
    password: <input type="password" name="password" required><br>
    <input type="submit" value="Login">
</form>
</body>
</html>
