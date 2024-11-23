package org.example.studentLessonServlet.servlet;

import lombok.SneakyThrows;
import org.example.studentLessonServlet.model.Lesson;
import org.example.studentLessonServlet.model.Student;
import org.example.studentLessonServlet.service.LessonService;
import org.example.studentLessonServlet.service.StudentService;
import org.example.studentLessonServlet.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/addStudent")
public class AddStudentServlet extends HttpServlet {
    private StudentService studentService = new StudentService();
    private LessonService lessonService = new LessonService();
    private UserService userService = new UserService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Lesson> lessons = lessonService.getAllLessons();
        req.setAttribute("lessons", lessons);
        req.getRequestDispatcher("/WEB-INF/addStudent.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        int birthYear = Integer.parseInt(req.getParameter("age"));
        int age = studentService.calculateAge(birthYear);
        int lesson = Integer.parseInt(req.getParameter("lesson_id"));
        int userId = Integer.parseInt(req.getParameter("user_id"));
        if (studentService.emailCheck(email)) {
            req.setAttribute("msg", "A student with this email already exists.");
            req.setAttribute("lessons", lessonService.getAllLessons());
            req.setAttribute("student", Student.builder());
        }
        Student student = Student.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .age(age)
                .lesson(lessonService.getLessonById(lesson))
                .user(userService.getUserById(userId))
                .build();
        studentService.add(student);
        resp.sendRedirect("/WEB-INF/students");
    }
}
