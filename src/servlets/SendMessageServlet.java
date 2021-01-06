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

@WebServlet(value = "/send_message")
public class SendMessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            String messageText = request.getParameter("message_text");
            Long receiverId = Long.parseLong(request.getParameter("receiver_id"));
            Long chatId = Long.parseLong(request.getParameter("chat_id"));

            User receiver = DBManager.getUserById(receiverId);
            Chat chat = DBManager.getChat(user, receiver);

            Message message = new Message(null, chat, receiver, user, messageText, false, null);

            System.out.println(DBManager.addMessage(message));

            chat.setLatestMessageText(messageText);
            System.out.println(DBManager.updateChatsLatestMessage(chat));

            response.sendRedirect("/chat?id=" + receiverId);
        } else {
            response.sendRedirect("/login");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
