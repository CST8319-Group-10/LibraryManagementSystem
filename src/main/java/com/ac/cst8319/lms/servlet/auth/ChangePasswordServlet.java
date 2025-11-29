package com.ac.cst8319.lms.servlet.auth;

import com.ac.cst8319.lms.service.AuthenticationService;
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
 * Servlet for changing user password.
 */
@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/user/change-password"})
public class ChangePasswordServlet extends HttpServlet {

    private final AuthenticationService authService;

    public ChangePasswordServlet() {
        this.authService = new AuthenticationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Forward to change password page
        request.getRequestDispatcher("/WEB-INF/jsp/auth/change-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        long userId = SessionUtil.getCurrentUserId(session);

        // Get form parameters
        String oldPassword = RequestUtil.getStringParameter(request, "oldPassword");
        String newPassword = RequestUtil.getStringParameter(request, "newPassword");
        String confirmPassword = RequestUtil.getStringParameter(request, "confirmPassword");
        String ipAddress = RequestUtil.getClientIpAddress(request);

        // Validate passwords match
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "New passwords do not match");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/change-password.jsp").forward(request, response);
            return;
        }

        try {
            // Change password
            boolean changed = authService.changePassword(userId, oldPassword, newPassword, ipAddress);

            if (changed) {
                SessionUtil.setSuccessMessage(session, "Password changed successfully!");
                response.sendRedirect(request.getContextPath() + "/dashboard");
            } else {
                request.setAttribute("errorMessage", "Current password is incorrect");
                request.getRequestDispatcher("/WEB-INF/jsp/auth/change-password.jsp").forward(request, response);
            }

        } catch (IllegalArgumentException e) {
            // Validation error
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/auth/change-password.jsp").forward(request, response);
        }
    }
}
