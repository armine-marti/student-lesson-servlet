package org.example.studentLessonServlet.servlet;

import lombok.SneakyThrows;
import org.example.studentLessonServlet.model.Lesson;
import org.example.studentLessonServlet.model.Student;
import org.example.studentLessonServlet.model.User;
import org.example.studentLessonServlet.service.LessonService;
import org.example.studentLessonServlet.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addMyLesson")
public class AddMyLessonServlet extends HttpServlet {
    private LessonService lessonService = new LessonService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/addMyLesson.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("user", user);
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }
        int loggedId = user.getId();
        req.setAttribute("user", user);

        String name = req.getParameter("name");
        String duration = req.getParameter("duration");
        String lecturerName = req.getParameter("lecturerName");
        String price = req.getParameter("price");
        if (name == null || name.isEmpty() || duration == null || duration.isEmpty() ||
                lecturerName == null || lecturerName.isEmpty() || price == null || price.isEmpty()) {
            req.setAttribute("msg", "All fields are required.");
            req.getRequestDispatcher("/WEB-INF/addMyLesson.jsp").forward(req, resp);
            return;
        }
        if (lessonService.sameLessonCheck(name, loggedId)) {
            req.setAttribute("msg", "Same lesson by the same user already exists.");
            req.setAttribute("lessons", lessonService.getAllLessons());
            req.getRequestDispatcher("/WEB-INF/addMyLesson.jsp").forward(req, resp);
            return;
        }
        try {
            Lesson lesson = Lesson.builder()
                    .name(name)
                    .duration(Integer.parseInt(duration))
                    .lecturerName(lecturerName)
                    .price(Double.parseDouble(price))
                    .user(user)
                    .build();
            lessonService.add(lesson);
            resp.sendRedirect("/myLessons");
        } catch (NumberFormatException e) {
            req.setAttribute("msg", "Invalid number  for duration or price.");
            req.getRequestDispatcher("/WEB-INF/addMyLesson.jsp").forward(req, resp);
        }
    }
}

