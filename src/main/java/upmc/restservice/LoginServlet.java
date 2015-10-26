package upmc.restservice;

import java.io.IOException;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;

import upmc.restservice.dao.HibernateUserDAO;
import upmc.restservice.dto.User;

public class LoginServlet extends HttpServlet {

    private HibernateUserDAO userDAO;

    public void init() throws ServletException {
        super.init();
        userDAO = new HibernateUserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect("/home");             // User is logged in, Go to some start page.
        } else {
            // Not logged in, show login page. You can eventually show the error page instead.
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
  
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userDAO.find(username, password);

        if (user != null) {
            request.getSession().setAttribute("user", user); // Put user in session.
            response.sendRedirect("/home"); // Go to some start page.
        } else {
            request.setAttribute("error", "Unknown login, try again"); // Set error msg for ${error}
            request.getRequestDispatcher("login.jsp").forward(request, response); // Go back to login page.
        }
    }
}