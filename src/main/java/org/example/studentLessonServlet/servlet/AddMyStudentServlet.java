package org.example.studentLessonServlet.servlet;

import org.example.studentLessonServlet.model.Lesson;
import org.example.studentLessonServlet.model.Student;
import org.example.studentLessonServlet.model.User;
import org.example.studentLessonServlet.service.LessonService;
import org.example.studentLessonServlet.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/addMyStudent")
public class AddMyStudentServlet extends HttpServlet {
    private StudentService studentService = new StudentService();
    LessonService lessonService = new LessonService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("user", user);
        List<Lesson> lessons = lessonService.getAllLessons();
        req.setAttribute("lessons", lessons);
        req.getRequestDispatcher("/WEB-INF/addMyStudent.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("user", user);
        try {
            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            String email = req.getParameter("email");
            String birthYear = req.getParameter("birthYear");
            int age = studentService.calculateAge(Integer.parseInt(birthYear));
            String lesson = req.getParameter("lesson_id");

            if (name == null || name.isEmpty() || surname == null || surname.isEmpty() ||
                    email == null || email.isEmpty() || birthYear == null || birthYear.isEmpty() ||
                    lesson == null || lesson.isEmpty()) {
                req.setAttribute("error", "Please complete the form");
                req.getRequestDispatcher("/WEB-INF/addMyStudent.jsp").forward(req, resp);
                return;
            }
            if (studentService.emailCheck(email)) {
                req.setAttribute("msg", "A student with this email already exists.");
                req.setAttribute("lessons", lessonService.getAllLessons());
                req.setAttribute("student", Student.builder()
                        .name(name)
                        .surname(surname)
                        .email(email)
                        .build());
                req.getRequestDispatcher("/WEB-INF/addMyStudent.jsp").forward(req, resp);
                return;
            }
            try {
                age = studentService.calculateAge(Integer.parseInt(birthYear));
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Please input a valid birth year.");
                req.setAttribute("lessons", lessonService.getAllLessons());
                req.setAttribute("student", Student.builder()
                        .name(name)
                        .surname(surname)
                        .email(email)
                        .age(age)
                        .build());
                req.getRequestDispatcher("/WEB-INF/addMyStudent.jsp").forward(req, resp);
                return;
            }
            Student student = Student.builder()
                    .name(name)
                    .surname(surname)
                    .email(email)
                    .age(age)
                    .lesson(lessonService.getLessonById(Integer.parseInt(lesson)))
                    .user(user)
                    .build();

            studentService.addStudentForUser(student, user.getId());
            resp.sendRedirect("/myStudents");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "An error occurred. Please try again.");
            req.setAttribute("lessons", lessonService.getAllLessons());
            req.getRequestDispatcher("/WEB-INF/addMyStudent.jsp").forward(req, resp);
        }
    }
}
