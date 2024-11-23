package org.example.studentLessonServlet.servlet;

import org.example.studentLessonServlet.model.User;
import org.example.studentLessonServlet.model.UserType;
import org.example.studentLessonServlet.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
//    }
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = userService.getUserByEmailAndPassword(email, password);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            if (user.getUserType() == UserType.USER) {

                resp.sendRedirect(req.getContextPath() + "/welcomeUser");
            } else if (user.getUserType() == UserType.ADMIN) {
                resp.sendRedirect(req.getContextPath() + "/welcomeAdmin");
            }
        } else {
            req.setAttribute("msg", "Invalid email or password");
            req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
        }
    }
}




