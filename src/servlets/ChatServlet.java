package servlets;

import db.DBManager;
import models.Chat;
import models.Message;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/chat")
public class ChatServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            Long id = Long.parseLong(request.getParameter("id"));
            User user2 = DBManager.getUserById(id);
            Chat chat = DBManager.getChat(user, user2);
            List<Message> messages = DBManager.getAllChatMessages(chat, user, user2);
            System.out.println(DBManager.markMessagesAsRead(user, chat));
            request.setAttribute("user", user);
            request.setAttribute("user2", user2);
            request.setAttribute("chat", chat);
            request.setAttribute("messages", messages);
            request.getRequestDispatcher("/vendor/chat.jsp").forward(request, response);
        } else {
            response.sendRedirect("/");
        }
    }
}
