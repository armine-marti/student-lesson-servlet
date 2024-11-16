<%@ page import="org.example.studentLessonServlet.model.Lesson" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: alexa
  Date: 16/11/2024
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Student</title>
</head>
<body>
<% List<Lesson> lessons = (List<Lesson>) request.getAttribute("lessons"); %>
<h1>Add Student</h1>
<a href="/students">Students</a> | <a href="index.jsp">Main</a> <br>

<form action="/addStudent" method="post">
    Name: <input type="text" name="name"><br>
    Surname: <input type="text" name="surname"><br>
    Email: <input type="text" name="email"><br>
    Year of Birth: <input type="number" name="age"><br>
    LESSON: <select name="lesson_id">
    <% if (lessons != null && !lessons.isEmpty()) { %>
    <% for (Lesson lesson : lessons) { %>
    <option value="<%=lesson.getId()%>"><%=lesson.getName()%></option>
    <% } %>
    <% } else { %>
    <option disabled>No lessons </option>
    <% } %>
</select><br>
    <input type="submit" value="ADD">
</form>
</body>
</html>
