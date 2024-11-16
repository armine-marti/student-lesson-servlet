package org.example.studentLessonServlet.servlet;

import lombok.SneakyThrows;

import org.example.studentLessonServlet.model.Lesson;
import org.example.studentLessonServlet.model.Student;
import org.example.studentLessonServlet.service.LessonService;
import org.example.studentLessonServlet.service.StudentService;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Lesson> lessons =  lessonService.getAllLessons();
        req.setAttribute("lessons", lessons);
        int studentId = Integer.parseInt(req.getParameter("id"));
        Student student = studentService.getStudentById(studentId);
        req.setAttribute("student", student);
        req.getRequestDispatcher("/editStudent.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");

        int birthYear = Integer.parseInt(req.getParameter("age"));
        int age = studentService.calculateAge(birthYear);
        String lessonId = req.getParameter("lesson_id");

        Student student = Student.builder()
                .id(Integer.parseInt(id))
                .name(name)
                .surname(surname)
                .email(email)
                .age(age)
                .lesson(lessonService.getLessonById(Integer.parseInt(lessonId)))
                .build();
        studentService.updateStudent(student);
        resp.sendRedirect("/students");
    }
}
