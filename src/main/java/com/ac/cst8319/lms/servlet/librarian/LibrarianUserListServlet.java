package com.ac.cst8319.lms.servlet.librarian;

import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.UserManagementService;
import com.ac.cst8319.lms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for displaying list of member users (librarian access).
 * Librarians can only view and manage regular members (roleId = 2).
 */
@WebServlet(name = "LibrarianUserListServlet", urlPatterns = {"/librarian/users"})
public class LibrarianUserListServlet extends HttpServlet {

    private final UserManagementService userService;

    public LibrarianUserListServlet() {
        this.userService = new UserManagementService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get only regular members (roleId = 2)
        List<UserAccount> members = userService.getUsersByRole(2);

        // Set members in request
        request.setAttribute("users", members);

        // Check for messages from session
        HttpSession session = request.getSession(false);
        String successMessage = SessionUtil.getAndClearSuccessMessage(session);
        String errorMessage = SessionUtil.getAndClearErrorMessage(session);

        if (successMessage != null) {
            request.setAttribute("success", successMessage);
        }
        if (errorMessage != null) {
            request.setAttribute("error", errorMessage);
        }

        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/user-list.jsp").forward(request, response);
    }
}
