package upmc.restservice;

import java.io.IOException;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;

import upmc.restservice.dao.HibernateUserDAO;
import upmc.restservice.dto.User;

public class RegisterServlet extends HttpServlet {

    private HibernateUserDAO userDAO;

    public void init() throws ServletException {
        super.init();
        userDAO = new HibernateUserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
  
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        String mail = request.getParameter("email");

        User nu = new User();
        nu.setName(user);
        nu.setPassword(pass);
        nu.setEmail(mail);

        userDAO.addUser(nu);
        request.setAttribute("error", "Success");
    }
}