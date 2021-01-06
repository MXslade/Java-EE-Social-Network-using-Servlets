package servlets;

import db.DBManager;
import models.Friend;
import models.User;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(value = "/search_users")
public class SearchUsersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            String searchText = request.getParameter("search_text");

            if (searchText != null) {

                List<User> foundUsers = DBManager.findUsersByFullNames(searchText);
                List<Friend> friends = DBManager.getAllUserFriends(user);
                List<Long> friendRequestReceiversIds = DBManager.getAllFriendRequestReceiversOfUser(user);
                List<Long> friendRequestSendersIds = DBManager.getAllFriendRequestSendersOfUser(user);
                foundUsers = foundUsers.stream().filter(u -> !u.getId().equals(user.getId())).collect(Collectors.toList());
                foundUsers = foundUsers.stream().filter(u -> friends.stream().noneMatch(f -> f.getSecondary().getId().equals(u.getId()))).collect(Collectors.toList());
                foundUsers = foundUsers.stream().filter(u -> friendRequestSendersIds.stream().noneMatch(id -> u.getId().equals(id))).collect(Collectors.toList());

                for (User u : foundUsers) {
                    System.out.println(u.getFullName());
                }

                JSONArray foundUsersJsonArray = new JSONArray();
                for (User u : foundUsers) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", u.getId());
                    jsonObject.put("email", u.getEmail());
                    jsonObject.put("password", u.getPassword());
                    jsonObject.put("full_name", u.getFullName());
                    jsonObject.put("age", u.getAge());
                    jsonObject.put("picture_url", u.getPictureUrl());
                    jsonObject.put("request_sent", friendRequestReceiversIds.stream().anyMatch(id -> u.getId().equals(id)));
                    foundUsersJsonArray.put(jsonObject);
                }

                response.setContentType("application/json");
                response.getWriter().write(foundUsersJsonArray.toString());
            } else {
                List<Friend> friends = DBManager.getAllUserFriends(user);

                JSONArray friendsJsonArray = new JSONArray();
                for (Friend friend : friends) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", friend.getSecondary().getId());
                    jsonObject.put("email", friend.getSecondary().getEmail());
                    jsonObject.put("password", friend.getSecondary().getPassword());
                    jsonObject.put("full_name", friend.getSecondary().getFullName());
                    jsonObject.put("age", friend.getSecondary().getAge());
                    jsonObject.put("picture_url", friend.getSecondary().getPictureUrl());
                    jsonObject.put("friend", true);
                    friendsJsonArray.put(jsonObject);
                }

                response.setContentType("application/json");
                response.getWriter().write(friendsJsonArray.toString());
            }

        } else {
            response.sendRedirect("/login");
        }
    }
}
