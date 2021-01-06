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
import java.sql.Date;

@WebServlet(value = "/update_general_information")
public class UpdateGeneralInformationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");

            String email = request.getParameter("email");
            User profileUser = DBManager.getUserByEmail(email);
            if (user.getId().equals(profileUser.getId())) {
                String fullName = request.getParameter("full_name");
                Date birthdate  = Date.valueOf(request.getParameter("birthdate"));
                profileUser.setFullName(fullName);
                profileUser.setBirthdate(birthdate);
                System.out.println(DBManager.updateGeneralInformationOfUser(profileUser));

                response.sendRedirect("/profile?id=" + profileUser.getId());
            } else {
                request.getRequestDispatcher("/vendor/error.jsp").forward(request, response);
            }

        } else {
            request.getRequestDispatcher("/vendor/error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
