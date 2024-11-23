package org.example.studentLessonServlet.servlet;

import org.example.studentLessonServlet.model.Lesson;
import org.example.studentLessonServlet.model.Student;
import org.example.studentLessonServlet.model.User;
import org.example.studentLessonServlet.service.StudentService;
import org.example.studentLessonServlet.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/myStudents")
public class MyStudentsServlet extends HttpServlet {

    StudentService studentService = new StudentService();
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        int userid = user.getId();
        List<Student> myStudents = studentService.getStudentByUser(userid);
        req.setAttribute("myStudents", myStudents);
        req.getRequestDispatcher("/WEB-INF/myStudents.jsp").forward(req, resp);
    }
}