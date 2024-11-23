package org.example.studentLessonServlet.servlet;

import lombok.SneakyThrows;

import org.example.studentLessonServlet.model.Lesson;
import org.example.studentLessonServlet.model.Student;
import org.example.studentLessonServlet.model.User;
import org.example.studentLessonServlet.model.UserType;
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

@WebServlet("/editStudent")
public class EditStudentServlet extends HttpServlet {
    private StudentService studentService = new StudentService();
    private LessonService lessonService = new LessonService();
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("user", user);

        List<Lesson> lessons = lessonService.getAllLessons();
        req.setAttribute("lessons", lessons);
        int studentId = Integer.parseInt(req.getParameter("id"));
        Student student = studentService.getStudentById(studentId);
        req.setAttribute("student", student);
        req.getRequestDispatcher("/WEB-INF/editStudent.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("user", user);
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        int birthYear = Integer.parseInt(req.getParameter("age"));
        int age = studentService.calculateAge(birthYear);
        String lessonId = req.getParameter("lesson_id");

        if (studentService.emailCheck(email)) {
            req.setAttribute("msg", "A student with this email already exists.");
            req.setAttribute("lessons", lessonService.getAllLessons());
            req.setAttribute("student", Student.builder()
                    .id(Integer.parseInt(id))
                    .name(name)
                    .surname(surname)
                    .email(email)
                    .age(age)
                    .lesson(lessonService.getLessonById(Integer.parseInt(lessonId)))
                    .user(user)
                    .build());
            req.getRequestDispatcher("/WEB-INF/editStudent.jsp").forward(req, resp);
            return;
        }
        Student student = Student.builder()
                .id(Integer.parseInt(id))
                .name(name)
                .surname(surname)
                .email(email)
                .age(age)
                .lesson(lessonService.getLessonById(Integer.parseInt(lessonId)))
                .user(user)
                .build();
        studentService.updateStudent(student);
        if (user.getUserType() == UserType.ADMIN) {
            resp.sendRedirect("/students");
        }
        if (user.getUserType() == UserType.USER) {
            resp.sendRedirect("/myStudents");
        }
    }
}
