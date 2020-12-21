package ru.itis.javalab.servlets;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.javalab.models.User;
import ru.itis.javalab.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private UsersService usersService;
    private PasswordEncoder passwordEncoder;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        this.usersService = (UsersService) context.getAttribute("usersService");
        this.passwordEncoder = (PasswordEncoder) context.getAttribute("passwordEncoder");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            // old-code request.getRequestDispatcher("/jsp/registration.jsp").forward(request, response);
            request.getRequestDispatcher("/ftlh/registration.ftlh").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)  {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String hashPassword = passwordEncoder.encode(password);
        User user = User
                .builder()
                .userName(username)
                .UUID(UUID.randomUUID().toString())
                .password(hashPassword)
                .build();
        if (usersService.containsUser(username, hashPassword)) {
            try {
                // old-code request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
                request.getRequestDispatcher("/ftlh/login.ftlh").forward(request, response);
            } catch (ServletException | IOException e) {
                throw new IllegalStateException(e);
            }
        } else {
            usersService.addUser(user);
            request.getSession().setAttribute("Auth", "true");
            try {
                response.sendRedirect("/students");
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
