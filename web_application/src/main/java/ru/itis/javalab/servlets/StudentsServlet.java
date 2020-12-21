package ru.itis.javalab.servlets;


import ru.itis.javalab.services.StudentService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/students")
public class StudentsServlet extends HttpServlet {

    private StudentService studentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.studentService = (StudentService) servletContext.getAttribute("studentService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        Cookie [] cookies = request.getCookies();
        Cookie cookie = null;
        for (Cookie c : cookies) {
            if (c.getName().equals("color")) {
                cookie = c;
            }
        }
        request.setAttribute("color", cookie);
        request.setAttribute("studentsForJsp", studentService.findAll());
        try {
            // old-code request.getRequestDispatcher("/jsp/students.jsp").forward(request, response);
            request.getRequestDispatcher("/ftlh/students.ftlh").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)  {
        String color = request.getParameter("color");
        Cookie cookie = new Cookie("color", color);
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
        try {
            response.sendRedirect("/students");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
