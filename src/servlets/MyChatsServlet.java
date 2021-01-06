package servlets;

import db.DBManager;
import models.Chat;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(value = "/my_chats")
public class MyChatsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            List<Chat> chats = DBManager.getAllUserChats(user);

            if (request.getParameter("search_text") != null) {
                String searchText = request.getParameter("search_text");
                chats = chats.stream().filter(c -> c.getUser().getFullName().contains(searchText) || c.getOpponentUser().getFullName().contains(searchText)).collect(Collectors.toList());
            }

            request.setAttribute("user", user);
            request.setAttribute("chats", chats);

            request.getRequestDispatcher("/vendor/my_chats.jsp").forward(request, response);
        } else {
            response.sendRedirect("/");
        }
    }
}
