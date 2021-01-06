package servlets;

import db.DBManager;
import models.Post;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/my_posts")
public class MyPostsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            List<Post> posts = DBManager.getAllUserPostsById(user.getId());
            request.setAttribute("user", user);
            request.setAttribute("posts", posts);
            request.getRequestDispatcher("/vendor/my_posts.jsp").forward(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }
}
