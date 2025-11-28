package com.ac.cst8319.lms.servlet;

import com.ac.cst8319.lms.dao.AuditLogDAO;
import com.ac.cst8319.lms.dao.impl.AuditLogDAOImpl;
import com.ac.cst8319.lms.model.AuditLog;
import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.UserManagementService;
import com.ac.cst8319.lms.util.RoleUtil;
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
 * Dashboard servlet - displays role-specific dashboard.
 */
@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {

    private final UserManagementService userService;
    private final AuditLogDAO auditLogDAO;

    public DashboardServlet() {
        this.userService = new UserManagementService();
        this.auditLogDAO = new AuditLogDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserAccount currentUser = SessionUtil.getCurrentUser(session);

        if (currentUser == null) {
            // Shouldn't happen due to AuthenticationFilter, but handle it
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get success/error messages from session and clear them
        String successMessage = SessionUtil.getAndClearSuccessMessage(session);
        String errorMessage = SessionUtil.getAndClearErrorMessage(session);

        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
        }
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        }

        // Load role-specific data
        if (RoleUtil.isAdministrator(currentUser)) {
            // Admin dashboard
            loadAdminDashboardData(request);
            request.getRequestDispatcher("/WEB-INF/jsp/dashboard/admin-dashboard.jsp")
                   .forward(request, response);
        } else if (RoleUtil.isLibrarian(currentUser)) {
            // Librarian dashboard - redirect to librarian-specific dashboard
            response.sendRedirect(request.getContextPath() + "/librarian/dashboard");
        } else {
            // Regular user dashboard
            loadUserDashboardData(request, currentUser);
            request.getRequestDispatcher("/WEB-INF/jsp/dashboard/user-dashboard.jsp")
                   .forward(request, response);
        }
    }

    /**
     * Load data for admin dashboard.
     */
    private void loadAdminDashboardData(HttpServletRequest request) {
        // Get user counts
        long totalUsers = userService.getTotalUserCount();
        long adminCount = userService.getUserCountByRole(RoleUtil.ROLE_ADMINISTRATOR);
        long librarianCount = userService.getUserCountByRole(RoleUtil.ROLE_LIBRARIAN);
        long userCount = userService.getUserCountByRole(RoleUtil.ROLE_REGISTERED_USER);

        // Get recent audit logs
        List<AuditLog> recentLogs = auditLogDAO.findRecent(10);

        // Set attributes
        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("adminCount", adminCount);
        request.setAttribute("librarianCount", librarianCount);
        request.setAttribute("userCount", userCount);
        request.setAttribute("recentLogs", recentLogs);
    }

    /**
     * Load data for regular user dashboard.
     */
    private void loadUserDashboardData(HttpServletRequest request, UserAccount user) {
        // For now, just display user info
        // In the future, this would show checkouts, waitlist, etc.
        request.setAttribute("user", user);
    }
}
