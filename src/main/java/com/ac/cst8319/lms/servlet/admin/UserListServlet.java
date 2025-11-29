package com.ac.cst8319.lms.servlet.admin;

import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.RoleService;
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
 * Servlet for displaying list of all users (admin only).
 */
@WebServlet(name = "UserListServlet", urlPatterns = {"/admin/users"})
public class UserListServlet extends HttpServlet {

    private final UserManagementService userService;
    private final RoleService roleService;

    public UserListServlet() {
        this.userService = new UserManagementService();
        this.roleService = new RoleService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get all users
        List<UserAccount> users = userService.getAllUsers();

        // Get roles for filter dropdown
        request.setAttribute("roles", roleService.getAllRoles());

        // Set users in request
        request.setAttribute("users", users);

        // Check for messages from session
        HttpSession session = request.getSession(false);
        String successMessage = SessionUtil.getAndClearSuccessMessage(session);
        String errorMessage = SessionUtil.getAndClearErrorMessage(session);

        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
        }
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        }

        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/jsp/admin/user-list.jsp").forward(request, response);
    }
}
