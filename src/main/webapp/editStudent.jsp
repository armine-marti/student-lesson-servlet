<%@ page import="org.example.studentLessonServlet.model.Student" %>
<%@ page import="org.example.studentLessonServlet.model.Lesson" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: alexa
  Date: 16/11/2024
  Time: 19:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<<!DOCTYPE html>
<html>
<head>
    <title>Edit Student</title>
</head>
<body>
<%
    List<Lesson> lessons = (List<Lesson>) request.getAttribute("lessons");
    Student student = (Student) request.getAttribute("student");
%>
<h1>Edit Student</h1>
<a href="/students">Students</a> | <a href="index.jsp">Main</a><br>

<form action="/editStudent" method="post">
    <input type="hidden" name="id" value="<%= student.getId() %>"><br>

    Name: <input type="text" name="name" value="<%= student.getName() %>"><br>
    Surname: <input type="text" name="surname" value="<%= student.getSurname() %>"><br>
    Email: <input type="text" name="email" value="<%= student.getEmail() %>"><br>
    Year of Birth: <input type="number" name="age" value="<%= student.getAge() %>"><br>
    LESSON: <select name="lesson_id">
    <% if (lessons != null && !lessons.isEmpty()) { %>
    <% for (Lesson lesson : lessons) { %>
    <option value = "<%=lesson.getId()%>"><%=lesson.getName() + " " %></option>
    <% } %>
    <% } else { %>
    <option disabled>No lessons </option>
    <% } %>
    </select><br>
    <input type="submit" value="UPDATE">
</form>
</body>
</html>