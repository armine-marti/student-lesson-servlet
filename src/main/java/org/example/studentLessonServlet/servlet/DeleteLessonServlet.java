package org.example.studentLessonServlet.servlet;

import org.example.studentLessonServlet.model.User;
import org.example.studentLessonServlet.model.UserType;
import org.example.studentLessonServlet.service.LessonService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteLesson")
public class DeleteLessonServlet extends HttpServlet {

    private LessonService lessonService = new LessonService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("user", user);
        int id = Integer.parseInt(req.getParameter("id"));
        lessonService.deleteLesson(id);
        if (user.getUserType() == UserType.ADMIN) {
            resp.sendRedirect("/lessons");
        }
        if (user.getUserType() == UserType.USER) {
            resp.sendRedirect("/myLessons");
        }

    }
}
