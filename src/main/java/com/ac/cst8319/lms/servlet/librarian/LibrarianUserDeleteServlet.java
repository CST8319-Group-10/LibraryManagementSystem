package com.ac.cst8319.lms.servlet.librarian;

import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.UserManagementService;
import com.ac.cst8319.lms.util.RequestUtil;
import com.ac.cst8319.lms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

/**
 * Servlet for deleting a member user (librarian access).
 * Librarians can only delete regular members (roleId = 2).
 */
@WebServlet(name = "LibrarianUserDeleteServlet", urlPatterns = {"/librarian/users/delete"})
public class LibrarianUserDeleteServlet extends HttpServlet {

    private final UserManagementService userService;

    public LibrarianUserDeleteServlet() {
        this.userService = new UserManagementService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long userId = RequestUtil.getLongParameter(request, "userId", -1);
        HttpSession session = request.getSession();

        if (userId == -1) {
            SessionUtil.setErrorMessage(session, "Invalid user ID");
            response.sendRedirect(request.getContextPath() + "/librarian/users");
            return;
        }

        // Get user to verify it's a regular member
        Optional<UserAccount> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            SessionUtil.setErrorMessage(session, "User not found");
            response.sendRedirect(request.getContextPath() + "/librarian/users");
            return;
        }

        UserAccount user = userOpt.get();

        // Check if user is a regular member (roleId = 2)
        if (user.getRoleId() != 2) {
            SessionUtil.setErrorMessage(session, "You can only delete regular member accounts");
            response.sendRedirect(request.getContextPath() + "/librarian/users");
            return;
        }

        try {
            // Get current librarian user
            long deletedBy = SessionUtil.getCurrentUserId(session);
            String ipAddress = RequestUtil.getClientIpAddress(request);

            // Delete user
            boolean deleted = userService.deleteUser(userId, deletedBy, ipAddress);

            if (deleted) {
                SessionUtil.setSuccessMessage(session, "Member account deleted successfully!");
            } else {
                SessionUtil.setErrorMessage(session, "Failed to delete member account");
            }

        } catch (IllegalArgumentException e) {
            SessionUtil.setErrorMessage(session, e.getMessage());
        }

        // Redirect to user list
        response.sendRedirect(request.getContextPath() + "/librarian/users");
    }
}
