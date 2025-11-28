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
 * Servlet for updating user role (admin only).
 */
@WebServlet(name = "UserRoleUpdateServlet", urlPatterns = {"/admin/users/role/update"})
public class UserRoleUpdateServlet extends HttpServlet {

    private final UserManagementService userService;

    public UserRoleUpdateServlet() {
        this.userService = new UserManagementService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long userId = RequestUtil.getLongParameter(request, "userId", -1);
        int roleId = RequestUtil.getIntParameter(request, "roleId", -1);
        HttpSession session = request.getSession();

        if (userId == -1 || roleId == -1) {
            SessionUtil.setErrorMessage(session, "Invalid parameters");
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        try {
            // Get current admin user
            long updatedBy = SessionUtil.getCurrentUserId(session);
            String ipAddress = RequestUtil.getClientIpAddress(request);

            // Update role
            boolean updated = userService.updateUserRole(userId, roleId, updatedBy, ipAddress);

            if (updated) {
                SessionUtil.setSuccessMessage(session, "User role updated successfully!");
            } else {
                SessionUtil.setErrorMessage(session, "Failed to update user role");
            }

        } catch (IllegalArgumentException e) {
            SessionUtil.setErrorMessage(session, e.getMessage());
        }

        // Redirect back to user list page
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}
