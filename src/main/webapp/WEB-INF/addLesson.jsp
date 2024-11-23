<%@ page import="org.example.studentLessonServlet.model.User" %><%--
  Created by IntelliJ IDEA.
  User: alexa
  Date: 16/11/2024
  Time: 18:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <% if (request.getAttribute("msg") != null) { %>
    <h3><%= request.getAttribute("msg") %>
    </h3>
    <% } %>
    <a href="/welcomeAdmin">Main page</a>
    <title>Add Lesson</title>
</head>
<body>
<h1>Add Lesson</h1>
<a href="/lessons">Lessons</a> | <a href="index.jsp">Main</a> <br>
<% User user = (User) request.getAttribute("user"); %>
<form action="/addLesson" method="post">
    Name: <input type="text" name="name"><br>
    Duration (minutes) : <input type="text" name="duration"><br>
    Lecturer name: <input type="text" name="lecturer_name"><br>
    Price: <input type="number" name="price" step="0.01" min="0"><br>

    <input type="submit" value="ADD">
</form>
</body>
</html>
