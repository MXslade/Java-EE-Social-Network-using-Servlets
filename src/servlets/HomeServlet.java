package servlets;

import db.DBManager;
import models.Post;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/home")
public class HomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user;
        if (session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");
            List<Post> posts = DBManager.getAllPosts();
            request.setAttribute("user", user);
            request.setAttribute("posts", posts);
            request.getRequestDispatcher("/vendor/home.jsp").forward(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }
}
