<%@ page import="org.example.studentLessonServlet.model.Lesson" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.studentLessonServlet.model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.example.studentLessonServlet.model.User" %><%--
  Created by IntelliJ IDEA.
  User: alexa
  Date: 19/11/2024
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Your Lessons
    </title>
</head>
<body>
<% User user = (User) session.getAttribute("user"); %>
<% List<Lesson> lessons = (List<Lesson>) request.getAttribute("myLessons"); %>
<a href="/welcomeUser">Main page</a>
<h2>Lessons:</h2> <a href="/addMyLesson">Add new lesson</a>

<table border="1">
    <tr>
        <th>id</th>
        <th>name</th>
        <th>duration (minutes)</th>
        <th>lecturer name</th>
        <th>price (£)</th>
        <th>added by</th>
        <th>actions</th>
    </tr>

    <% for (Lesson lesson : lessons) { %>
    <tr>
        <td><%= lesson.getId() %>
        </td>
        <td><%= lesson.getName() %>
        </td>
        <td><%=lesson.getDuration() + " minutes"%>
        </td>
        <td><%= lesson.getLecturerName() %>
        </td>
        <td><%= lesson.getPrice() %>
        </td>
        <td><%= user.getName() %> <%= user.getSurname() %>
        </td>
        <td><a href="/deleteLesson?id=<%= lesson.getId() %>">Delete</a> / <a
                href="/editLesson?id=<%= lesson.getId() %>">Edit</a></td>
    </tr>
    <% } %>
</table>
</body>
</html>
