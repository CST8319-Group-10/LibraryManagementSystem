package com.ac.cst8319.lms.servlet.admin;

import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.AccountStandingService;
import com.ac.cst8319.lms.service.RoleService;
import com.ac.cst8319.lms.service.UserManagementService;
import com.ac.cst8319.lms.util.RequestUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

/**
 * Servlet for displaying user details (admin only).
 */
@WebServlet(name = "UserDetailServlet", urlPatterns = {"/admin/users/view"})
public class UserDetailServlet extends HttpServlet {

    private final UserManagementService userService;
    private final RoleService roleService;
    private final AccountStandingService standingService;

    public UserDetailServlet() {
        this.userService = new UserManagementService();
        this.roleService = new RoleService();
        this.standingService = new AccountStandingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get user ID parameter
        long userId = RequestUtil.getLongParameter(request, "userId", -1);

        if (userId == -1) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required");
            return;
        }

        // Get user
        Optional<UserAccount> userOpt = userService.getUserById(userId);

        if (userOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            return;
        }

        // Set user in request
        request.setAttribute("user", userOpt.get());

        // Get all roles and standings for dropdowns
        request.setAttribute("roles", roleService.getAllRoles());
        request.setAttribute("standings", standingService.getAllStandings());

        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/jsp/admin/user-detail.jsp").forward(request, response);
    }
}
