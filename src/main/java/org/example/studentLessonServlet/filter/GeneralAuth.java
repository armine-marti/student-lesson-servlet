package org.example.studentLessonServlet.filter;

import org.example.studentLessonServlet.model.User;
import org.example.studentLessonServlet.model.UserType;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/editLesson", "/deleteLesson", "/editStudent", "/deleteStudent"})
public class GeneralAuth extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        if (user != null || user.getUserType() == UserType.USER || user.getUserType() == UserType.ADMIN) {
            chain.doFilter(req, res);
        } else {
            res.sendRedirect("/WEB-INF/index.jsp");
        }
    }
}
