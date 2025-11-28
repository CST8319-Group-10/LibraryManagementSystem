package com.ac.cst8319.lms.servlet.admin;

import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.AccountStandingService;
import com.ac.cst8319.lms.service.RoleService;
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
 * Servlet for editing user information (admin only).
 */
@WebServlet(name = "UserEditServlet", urlPatterns = {"/admin/users/edit"})
public class UserEditServlet extends HttpServlet {

    private final UserManagementService userService;
    private final RoleService roleService;
    private final AccountStandingService standingService;

    public UserEditServlet() {
        this.userService = new UserManagementService();
        this.roleService = new RoleService();
        this.standingService = new AccountStandingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

        // Set user and dropdown data
        request.setAttribute("user", userOpt.get());
        request.setAttribute("roles", roleService.getAllRoles());
        request.setAttribute("standings", standingService.getAllStandings());

        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/jsp/admin/user-edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters
        long userId = RequestUtil.getLongParameter(request, "userId", -1);
        String email = RequestUtil.getStringParameter(request, "email");
        String firstName = RequestUtil.getStringParameter(request, "firstName");
        String lastName = RequestUtil.getStringParameter(request, "lastName");
        String phone = RequestUtil.getStringParameter(request, "phone");
        String address = RequestUtil.getStringParameter(request, "address");
        int roleId = RequestUtil.getIntParameter(request, "roleId", 2);
        int standingId = RequestUtil.getIntParameter(request, "standingId", 1);

        if (userId == -1) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required");
            return;
        }

        // Get existing user
        Optional<UserAccount> existingUserOpt = userService.getUserById(userId);
        if (existingUserOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            return;
        }

        // Update user object
        UserAccount user = existingUserOpt.get();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setAddress(address);
        user.setRoleId(roleId);
        user.setAccountStanding(standingId);

        try {
            // Get current admin user
            HttpSession session = request.getSession();
            long updatedBy = SessionUtil.getCurrentUserId(session);
            String ipAddress = RequestUtil.getClientIpAddress(request);

            // Update user
            userService.updateUser(user, updatedBy, ipAddress);

            // Set success message
            SessionUtil.setSuccessMessage(session, "User updated successfully!");

            // Redirect to user detail page
            response.sendRedirect(request.getContextPath() + "/admin/users/view?userId=" + userId);

        } catch (IllegalArgumentException e) {
            // Update failed
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("user", user);
            request.setAttribute("roles", roleService.getAllRoles());
            request.setAttribute("standings", standingService.getAllStandings());
            request.getRequestDispatcher("/WEB-INF/jsp/admin/user-edit.jsp").forward(request, response);
        }
    }
}
