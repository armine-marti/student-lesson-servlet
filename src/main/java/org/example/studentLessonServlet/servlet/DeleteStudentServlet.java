package org.example.studentLessonServlet.servlet;

import org.example.studentLessonServlet.model.User;
import org.example.studentLessonServlet.model.UserType;
import org.example.studentLessonServlet.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteStudent")
public class DeleteStudentServlet extends HttpServlet {

    private StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        int id = Integer.parseInt(req.getParameter("id"));
        studentService.deleteStudent(id);
        if (user.getUserType() == UserType.ADMIN) {
            resp.sendRedirect("/students");
        }
        if (user.getUserType() == UserType.USER) {
            resp.sendRedirect("/myStudents");
        }
    }
}
