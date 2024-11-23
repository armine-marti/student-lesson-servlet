package org.example.studentLessonServlet.servlet;

import org.example.studentLessonServlet.model.Lesson;
import org.example.studentLessonServlet.model.User;
import org.example.studentLessonServlet.service.LessonService;
import org.example.studentLessonServlet.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/myLessons")
public class MyLessonsServlet extends HttpServlet {

    private LessonService lessonService = new LessonService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        List<Lesson> myLessons = lessonService.getLessonsByUser(user);
        req.setAttribute("myLessons", myLessons);
        req.getRequestDispatcher("/WEB-INF/myLessons.jsp").forward(req, resp);

    }
}

