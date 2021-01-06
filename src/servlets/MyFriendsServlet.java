package servlets;

import db.DBManager;
import models.Friend;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/my_friends")
public class MyFriendsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute("user") != null) {
            User user = (User) httpSession.getAttribute("user");

            List<Friend> friends = DBManager.getAllUserFriends(user);
            List<Long> friendRequestSendersIds = DBManager.getAllFriendRequestSendersOfUser(user);
            List<User> friendRequestSenders = new ArrayList<>();

            for (Long id : friendRequestSendersIds) {
                friendRequestSenders.add(DBManager.getUserById(id));
            }

            request.setAttribute("user", user);
            request.setAttribute("friends", friends);
            request.setAttribute("friend_request_senders", friendRequestSenders);

            request.getRequestDispatcher("/vendor/my_friends_page.jsp").forward(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }
}
