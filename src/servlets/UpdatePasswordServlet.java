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

@WebServlet(value = "/update_password")
public class UpdatePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");

            String oldPassword = request.getParameter("old_password");
            String newPassword = request.getParameter("new_password");

            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                System.out.println(DBManager.updatePasswordOfUser(user));
                response.sendRedirect("/profile?id=" + user.getId());
            }
        } else {
            request.getRequestDispatcher("/vendor/error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
