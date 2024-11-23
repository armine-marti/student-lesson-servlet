<%@ page import="org.example.studentLessonServlet.model.User" %>
<%@ page import="org.example.studentLessonServlet.model.UserType" %><%--
  Created by IntelliJ IDEA.
  User: alexa
  Date: 18/11/2024
  Time: 20:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>USER- Student Portal</title>
</head>
<body>
<h1>Student Portal Application! </h1>
<a href="/logout">To logout</a><br>
<%User user = (User) session.getAttribute("user");%>

<span> Welcome <%=user.getName() + " " + user.getSurname()%></span>
<br>

<div>
    <a href="/myLessons">My lessons</a><br>
    <a href="/myStudents">My students</a><br>
</div>
<br/>
</body>
</html>
