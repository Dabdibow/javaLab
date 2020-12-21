package ru.itis.javalab.servlets;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.javalab.models.User;
import ru.itis.javalab.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UsersService usersService;
    private PasswordEncoder passwordEncoder;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.usersService = (UsersService) servletContext.getAttribute("usersService");
        this.passwordEncoder = (PasswordEncoder) servletContext.getAttribute("passwordEncoder");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
           // old-code  request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            request.getRequestDispatcher("/ftlh/login.ftlh").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = usersService.getUserByUserName(username);
        String dbPassword = "";
        if (user != null) {
            dbPassword = user.getPassword();
        }
        if (passwordEncoder.matches(password, dbPassword)) {
            request.getSession().setAttribute("Auth", "true");
            try {
                response.sendRedirect("/students");
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        } else {
            response.setContentType("text/html");
            PrintWriter out;
            try {
                out = response.getWriter();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
            out.println("Invalid username or password, please try again");
            try {
                request.getRequestDispatcher("/html/registration_redirect.html").forward(request, response);
            } catch (ServletException | IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
