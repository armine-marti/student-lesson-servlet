<%@ page import="org.example.studentLessonServlet.model.Lesson" %>
<%--
  Created by IntelliJ IDEA.
  User: alexa
  Date: 16/11/2024
  Time: 19:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Lesson</title>
</head>
<body>
<% Lesson lesson = (Lesson) request.getAttribute("lesson"); %>
<h1>Edit Lesson</h1>
<a href="/lessons">Lessons</a> | <a href="index.jsp">Main</a> <br>

<form action="/editLesson" method="post">
    <input type="hidden" name="id" value="<%=lesson.getId()%>"> <br>
    Name: <input type="text" name="name" value="<%=lesson.getName()%>"><br>
    Duration (minutes): <input type="text" name="duration" value="<%=lesson.getDuration()%>"><br>
    Lecturer Name: <input type="text" name="lecturer_name" value="<%=lesson.getLecturerName()%>"><br>
    Price: <input type="number" name="price" value="<%=lesson.getPrice()%>"><br>
    <input type="submit" value="UPDATE">

</form>

</body>
</html>
