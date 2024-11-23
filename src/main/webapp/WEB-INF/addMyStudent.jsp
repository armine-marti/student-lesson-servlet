<%@ page import="org.example.studentLessonServlet.model.User" %>
<%@ page import="org.example.studentLessonServlet.model.Lesson" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: alexa
  Date: 21/11/2024
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <% if (request.getAttribute("msg") != null) { %>
    <h3><%= request.getAttribute("msg") %>
    </h3>
    <% } %>
    <a href="/welcomeUser">Main page</a>
    <% User user = (User) request.getAttribute("user"); %>
    <% List<Lesson> lessons = (List<Lesson>) request.getAttribute("lessons"); %>
    <title>Add new student </title>
</head>
<body>
<h1>Add Student</h1>
<a href="/myStudent">My students</a>

<form action="/addMyStudent" method="post">
    Name: <input type="text" name="name" required><br>
    Surname: <input type="text" name="surname" required><br>
    Email: <input type="email" name="email" required><br>
    Year of Birth: <input type="number" name="birthYear" required><br>

    LESSON:
    <select name="lesson_id" required>
        <% if (lessons != null && !lessons.isEmpty()) { %>
        <% for (Lesson lesson : lessons) { %>
        <option value="<%=lesson.getId()%>"><%=lesson.getName()%>
        </option>
        <% } %>
        <% } else { %>
        <option disabled>No lessons available</option>
        <% } %>
    </select><br>


    <input type="hidden" name="user_id" value="<%= user.getId() %>">

    <input type="submit" value="ADD">
</form>
</body>
</html>
