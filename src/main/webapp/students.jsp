

<%@ page import="java.util.List" %>
<%@ page import="org.example.studentLessonServlet.model.Student" %><%--
  Created by IntelliJ IDEA.
  User: alexa
  Date: 16/11/2024
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Students
    </title>
</head>
<body>
<h2>Students:</h2> <a href="/addStudent">Add Student</a>

<% List<Student> students = (List<Student>) request.getAttribute("students");%>

<table border="1">
    <tr>
        <th>id</th>
        <th>name</th>
        <th>surname</th>
        <th>email</th>
        <th>age</th>
        <th>lesson</th>
        <th>action</th>
    </tr>

    <% for (Student student : students) { %>
    <tr>
        <td><%= student.getId() %>
        </td>
        <td><%= student.getName() %>
        </td>
        <td><%= student.getSurname() %>
        </td>
        <td><%= student.getEmail() %>
        </td>
        <td><%= student.getAge() %>
        </td>
        <td><%= student.getLesson().getName()%>
        </td>
        <td><a href="/deleteStudent?id=<%= student.getId() %>">Delete</a> / <a
                href="/editStudent?id=<%= student.getId() %>">Edit</a></td>
    </tr>
    <% } %>
</table>
</body>
</html>
