package org.example.studentLessonServlet.servlet;

import lombok.SneakyThrows;
import org.example.studentLessonServlet.model.Lesson;
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
        int lessonId = Integer.parseInt(req.getParameter("id"));
        Lesson lesson = lessonService.getLessonById(lessonId);
        req.setAttribute("lesson", lesson);
        req.getRequestDispatcher("/editLesson.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String duration = req.getParameter("duration");
        String lecturerName = req.getParameter("lecturer_name");
        String price = req.getParameter("price");

        Lesson lesson = Lesson.builder()
                .id(Integer.parseInt(id))
                .name(name)
                .duration(Integer.parseInt(duration))
                .lecturerName(lecturerName)
                .price(Double.parseDouble(price))
                .build();

        lessonService.updateLesson(lesson);
        resp.sendRedirect("/lessons");
    }
}
