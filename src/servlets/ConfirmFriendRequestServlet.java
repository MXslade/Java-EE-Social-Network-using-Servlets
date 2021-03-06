package servlets;

import db.DBManager;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/confirm_friend_request")
public class ConfirmFriendRequestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            Long id = Long.parseLong(request.getParameter("id"));
            User almostFriend = DBManager.getUserById(id);

            System.out.println(DBManager.confirmFriendRequest(user, almostFriend));

            response.sendRedirect("/my_friends");
        } else {
            response.sendRedirect("/login");
        }
    }
}
