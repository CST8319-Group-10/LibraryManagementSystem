package com.ac.cst8319.lms.servlet.admin;

import com.ac.cst8319.lms.model.AuditLogEntry;
import com.ac.cst8319.lms.service.AuditLogService;
import com.ac.cst8319.lms.service.RoleService;
import com.ac.cst8319.lms.service.UserManagementService;
import com.ac.cst8319.lms.util.RequestUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet for viewing and filtering audit logs (admin only).
 */
@WebServlet(name = "AuditLogServlet", urlPatterns = {"/admin/audit-logs"})
public class AuditLogServlet extends HttpServlet {

    private final AuditLogService auditLogService;
    private final RoleService roleService;
    private final UserManagementService userService;

    public AuditLogServlet() {
        this.auditLogService = new AuditLogService();
        this.roleService = new RoleService();
        this.userService = new UserManagementService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get filter parameters
        String userIdStr = request.getParameter("userId");
        String roleIdStr = request.getParameter("roleId");
        String actionIdStr = request.getParameter("actionId");

        Long userId = (userIdStr != null && !userIdStr.isEmpty()) ? Long.parseLong(userIdStr) : null;
        Integer roleId = (roleIdStr != null && !roleIdStr.isEmpty()) ? Integer.parseInt(roleIdStr) : null;
        Integer actionId = (actionIdStr != null && !actionIdStr.isEmpty()) ? Integer.parseInt(actionIdStr) : null;

        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        LocalDate startDate = null;
        LocalDate endDate = null;

        if (startDateStr != null && !startDateStr.isEmpty()) {
            try {
                startDate = LocalDate.parse(startDateStr);
            } catch (Exception e) {
                // Invalid date format, ignore
            }
        }

        if (endDateStr != null && !endDateStr.isEmpty()) {
            try {
                endDate = LocalDate.parse(endDateStr);
            } catch (Exception e) {
                // Invalid date format, ignore
            }
        }

        // Pagination
        int page = RequestUtil.getIntParameter(request, "page", 1);
        int pageSize = 50;
        int offset = (page - 1) * pageSize;

        // Get filtered logs
        List<AuditLogEntry> logs = auditLogService.getFilteredLogs(
                userId, roleId, actionId, startDate, endDate, pageSize, offset);

        long totalCount = auditLogService.countFilteredLogs(
                userId, roleId, actionId, startDate, endDate);

        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // Get filter options
        request.setAttribute("roles", roleService.getAllRoles());
        request.setAttribute("actions", getAuditActions());
        request.setAttribute("users", userService.getAllUsers());

        // Set attributes for JSP
        request.setAttribute("logs", logs);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalCount", totalCount);

        // Preserve filter values
        request.setAttribute("filterUserId", userId);
        request.setAttribute("filterRoleId", roleId);
        request.setAttribute("filterActionId", actionId);
        request.setAttribute("filterStartDate", startDateStr);
        request.setAttribute("filterEndDate", endDateStr);

        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/jsp/admin/audit-log.jsp").forward(request, response);
    }

    /**
     * Get all audit log actions for filter dropdown.
     */
    private List<Map<String, Object>> getAuditActions() {
        List<Map<String, Object>> actions = new ArrayList<>();
        String sql = "SELECT AuditActionID, Action, Description FROM AuditLogAction ORDER BY Action";

        try (Connection conn = com.ac.cst8319.lms.util.DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> action = new HashMap<>();
                action.put("actionId", rs.getInt("AuditActionID"));
                action.put("action", rs.getString("Action"));
                action.put("description", rs.getString("Description"));
                actions.add(action);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting audit actions", e);
        }
        return actions;
    }
}
