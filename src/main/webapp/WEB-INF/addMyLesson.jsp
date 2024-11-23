<%@ page import="org.example.studentLessonServlet.model.Lesson" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.studentLessonServlet.model.UserType" %>
<%@ page import="org.example.studentLessonServlet.model.User" %><%--
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
    <title>Add new lesson</title>
</head>
<body>
<h1>Add new lesson</h1>
<a href="/myLessons">My lessons</a> <br>

<%
    User user = (User) request.getAttribute("user");
    if (user == null) {
%>
<p>Error: User is not logged in.</p>
<%
} else {
%>
<form action="/addMyLesson" method="post">
    Name: <input type="text" name="name"><br>
    Duration (minutes): <input type="text" name="duration"><br>
    Lecturer name: <input type="text" name="lecturerName"><br>
    Price: <input type="number" name="price" step="0.01" min="0"><br>
    User: <input name="user" value="<%=user.getSurname()%>" readonly="readonly"><br><br>
    <input type="submit" value="ADD">
</form>
<%
    }
%>

</body>
</html>
