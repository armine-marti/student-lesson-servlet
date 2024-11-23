package org.example.studentLessonServlet.servlet;

import org.example.studentLessonServlet.model.Lesson;
import org.example.studentLessonServlet.model.User;
import org.example.studentLessonServlet.model.UserType;
import org.example.studentLessonServlet.service.LessonService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/lessons")
public class LessonsServlet extends HttpServlet {
    private LessonService lessonService = new LessonService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Lesson> allLessons = lessonService.getAllLessons();
        req.setAttribute("lessons", allLessons);
        req.getRequestDispatcher("/WEB-INF/lessons.jsp").forward(req, resp);

    }
}
