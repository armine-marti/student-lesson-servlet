package org.example.studentLessonServlet.servlet;

import lombok.SneakyThrows;
import org.example.studentLessonServlet.model.Lesson;
import org.example.studentLessonServlet.model.Student;
import org.example.studentLessonServlet.model.User;
import org.example.studentLessonServlet.model.UserType;
import org.example.studentLessonServlet.service.LessonService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editLesson")
public class EditLessonServlet extends HttpServlet {
    private LessonService lessonService = new LessonService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("user", user);
        int lessonId = Integer.parseInt(req.getParameter("id"));
        Lesson lesson = lessonService.getLessonById(lessonId);
        req.setAttribute("lesson", lesson);
        req.getRequestDispatcher("/WEB-INF/editLesson.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        int loggedId = user.getId();
        req.setAttribute("user", user);
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String duration = req.getParameter("duration");
        String lecturerName = req.getParameter("lecturer_name");
        String price = req.getParameter("price");
        if (lessonService.sameLessonCheckEdit(name, loggedId, Integer.parseInt(id))) {
            req.setAttribute("msg", "Same lesson by the same user already exists.");
            req.setAttribute("lessons", lessonService.getAllLessons());
            req.setAttribute("lesson", Lesson.builder()
                    .id(Integer.parseInt(id))
                    .name(name)
                    .duration(Integer.parseInt(duration))
                    .lecturerName(lecturerName)
                    .price(Double.parseDouble(price))
                    .user(user)
                    .build());
            req.getRequestDispatcher("/WEB-INF/editLesson.jsp").forward(req, resp);
            return;
        }
        Lesson lesson = Lesson.builder()
                .id(Integer.parseInt(id))
                .name(name)
                .duration(Integer.parseInt(duration))
                .lecturerName(lecturerName)
                .price(Double.parseDouble(price))
                .user(user)
                .build();
        lessonService.updateLesson(lesson);
        if (user.getUserType() == UserType.ADMIN) {
            resp.sendRedirect("/lessons");
        }
        if (user.getUserType() == UserType.USER) {
            resp.sendRedirect("/myLessons");
        }
    }
}
