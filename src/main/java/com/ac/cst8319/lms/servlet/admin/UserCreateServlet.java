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

/**
 * Servlet for creating a new user (admin only).
 */
@WebServlet(name = "UserCreateServlet", urlPatterns = {"/admin/users/create"})
public class UserCreateServlet extends HttpServlet {

    private final UserManagementService userService;
    private final RoleService roleService;
    private final AccountStandingService standingService;

    public UserCreateServlet() {
        this.userService = new UserManagementService();
        this.roleService = new RoleService();
        this.standingService = new AccountStandingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Load roles and standings for dropdowns
        request.setAttribute("roles", roleService.getAllRoles());
        request.setAttribute("standings", standingService.getAllStandings());

        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/jsp/admin/user-create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters
        String email = RequestUtil.getStringParameter(request, "email");
        String password = RequestUtil.getStringParameter(request, "password");
        String firstName = RequestUtil.getStringParameter(request, "firstName");
        String lastName = RequestUtil.getStringParameter(request, "lastName");
        String phone = RequestUtil.getStringParameter(request, "phone");
        String address = RequestUtil.getStringParameter(request, "address");
        int roleId = RequestUtil.getIntParameter(request, "roleId", 2);
        int standingId = RequestUtil.getIntParameter(request, "standingId", 1);

        // Create user object
        UserAccount user = new UserAccount();
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
            long createdBy = SessionUtil.getCurrentUserId(session);
            String ipAddress = RequestUtil.getClientIpAddress(request);

            // Create user
            userService.createUser(user, password, createdBy, ipAddress);

            // Set success message
            SessionUtil.setSuccessMessage(session, "User created successfully!");

            // Redirect to user list
            response.sendRedirect(request.getContextPath() + "/admin/users");

        } catch (IllegalArgumentException e) {
            // Creation failed
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("roles", roleService.getAllRoles());
            request.setAttribute("standings", standingService.getAllStandings());
            request.getRequestDispatcher("/WEB-INF/jsp/admin/user-create.jsp").forward(request, response);
        }
    }
}
