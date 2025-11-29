package com.ac.cst8319.lms.servlet.admin;

import com.ac.cst8319.lms.service.AccountStandingService;
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
 * Servlet for updating user account standing (admin only).
 */
@WebServlet(name = "UserStandingUpdateServlet", urlPatterns = {"/admin/users/standing/update"})
public class UserStandingUpdateServlet extends HttpServlet {

    private final AccountStandingService standingService;

    public UserStandingUpdateServlet() {
        this.standingService = new AccountStandingService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long userId = RequestUtil.getLongParameter(request, "userId", -1);
        int standingId = RequestUtil.getIntParameter(request, "standingId", -1);
        String reason = RequestUtil.getStringParameter(request, "reason");
        HttpSession session = request.getSession();

        if (userId == -1 || standingId == -1) {
            SessionUtil.setErrorMessage(session, "Invalid parameters");
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        if (reason == null || reason.isEmpty()) {
            reason = "Standing updated by administrator";
        }

        try {
            // Get current admin user
            long updatedBy = SessionUtil.getCurrentUserId(session);
            String ipAddress = RequestUtil.getClientIpAddress(request);

            // Update standing
            boolean updated = standingService.updateStanding(userId, standingId, updatedBy, reason, ipAddress);

            if (updated) {
                SessionUtil.setSuccessMessage(session, "Account standing updated successfully!");
            } else {
                SessionUtil.setErrorMessage(session, "Failed to update account standing");
            }

        } catch (IllegalArgumentException e) {
            SessionUtil.setErrorMessage(session, e.getMessage());
        }

        // Redirect back to user detail page
        response.sendRedirect(request.getContextPath() + "/admin/users/view?userId=" + userId);
    }
}
