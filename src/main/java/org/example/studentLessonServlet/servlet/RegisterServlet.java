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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserType userType = UserType.valueOf(req.getParameter("user_type"));
        HttpSession session = req.getSession();
        if (userService.getUserByEmail(email) != null) {
            session.setAttribute("msg", "User with that email already exists");
        } else {
            User user = User.builder()
                    .name(name)
                    .surname(surname)
                    .email(email)
                    .password(password)
                    .userType(userType)
                    .build();
            userService.add(user);
            session.setAttribute("msg", "You successfully registered!");
            if (user.getUserType() == UserType.USER) {
                session.setAttribute("user", user);
                req.getRequestDispatcher("/WEB-INF/welcomeUser.jsp").forward(req, resp);
            } else if (user.getUserType() == UserType.ADMIN) {
                session.setAttribute("user", user);
                req.getRequestDispatcher("/WEB-INF/welcomeAdmin.jsp").forward(req, resp);
            } else resp.sendRedirect("/register");
        }
    }
}
