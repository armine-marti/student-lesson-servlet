package org.example.studentLessonServlet.servlet;

import lombok.SneakyThrows;
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

@WebServlet("/addLesson")
public class AddLessonServlet extends HttpServlet {

    private LessonService lessonService = new LessonService();
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/addLesson.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        int loggedid = user.getId();
        String name = req.getParameter("name");
        String duration = req.getParameter("duration");
        String lecturerName = req.getParameter("lecturer_name");
        String price = req.getParameter("price");
        if (lessonService.sameLessonCheck(name, loggedid)) {
            req.setAttribute("msg", "Same lesson by same user already exists.");
            req.getRequestDispatcher("/WEB-INF/addLesson.jsp").forward(req, resp);
            return;
        }
        Lesson lesson = Lesson.builder()
                .name(name)
                .duration(Integer.parseInt(duration))
                .lecturerName(lecturerName)
                .price(Double.parseDouble(price))
                .user(user)
                .build();
        lessonService.add(lesson);
        resp.sendRedirect("/lessons");
    }
}
