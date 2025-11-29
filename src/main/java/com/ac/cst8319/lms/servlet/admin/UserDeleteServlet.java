package com.ac.cst8319.lms.servlet.admin;

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

/**
 * Servlet for deleting a user (admin only).
 */
@WebServlet(name = "UserDeleteServlet", urlPatterns = {"/admin/users/delete"})
public class UserDeleteServlet extends HttpServlet {

    private final UserManagementService userService;

    public UserDeleteServlet() {
        this.userService = new UserManagementService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long userId = RequestUtil.getLongParameter(request, "userId", -1);
        HttpSession session = request.getSession();

        if (userId == -1) {
            SessionUtil.setErrorMessage(session, "Invalid user ID");
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        try {
            // Get current admin user
            long deletedBy = SessionUtil.getCurrentUserId(session);
            String ipAddress = RequestUtil.getClientIpAddress(request);

            // Delete user
            boolean deleted = userService.deleteUser(userId, deletedBy, ipAddress);

            if (deleted) {
                SessionUtil.setSuccessMessage(session, "User deleted successfully!");
            } else {
                SessionUtil.setErrorMessage(session, "Failed to delete user");
            }

        } catch (IllegalArgumentException e) {
            SessionUtil.setErrorMessage(session, e.getMessage());
        }

        // Redirect to user list
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}
