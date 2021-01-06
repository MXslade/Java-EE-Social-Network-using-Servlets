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

@WebServlet(value = "/profile")
public class ProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            Long profileId = Long.parseLong(request.getParameter("id"));
            User profileOwner = DBManager.getUserById(profileId);
            if (profileOwner == null) {
                request.getRequestDispatcher("/vendor/error.jsp").forward(request, response);
                return;
            }
            if (user.getId().equals(profileId)) {
                request.setAttribute("is_owner", true);
            } else {
                request.setAttribute("is_owner", false);
            }
            List<Post> posts = DBManager.getAllUserPostsById(profileOwner.getId());
            request.setAttribute("user", user);
            request.setAttribute("profile_owner", profileOwner);
            request.setAttribute("is_friends", DBManager.getAllUserFriends(user).stream().anyMatch(f -> f.getSecondary().getId().equals(profileOwner.getId())));
            request.setAttribute("posts", posts);
            request.getRequestDispatcher("/vendor/profile_page.jsp").forward(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }
}
