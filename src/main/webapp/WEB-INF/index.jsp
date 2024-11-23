<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1>
    Student Portal Application
</h1>
<h2>LOGIN</h2>
<form action="/login" method="post">
    email: <input type="text" name="email"><br>
    password: <input type="password" name="password"><br>
    <input type="submit">
</form>
<% if (request.getAttribute("msg") != null) { %>
<p style="color: red;"><%= request.getAttribute("msg") %>
</p>
<% } %>

<a href="/register">Register</a></br
<br/>
</body>
</html>