package com.ac.cst8319.lms.servlet.librarian;

import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.AccountStandingService;
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
 * Servlet for editing member user information (librarian access).
 * Librarians can only edit regular members (roleId = 2).
 */
@WebServlet(name = "LibrarianUserEditServlet", urlPatterns = {"/librarian/users/edit"})
public class LibrarianUserEditServlet extends HttpServlet {

    private final UserManagementService userService;
    private final AccountStandingService standingService;

    public LibrarianUserEditServlet() {
        this.userService = new UserManagementService();
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

        UserAccount user = userOpt.get();

        // Check if user is a regular member (roleId = 2)
        if (user.getRoleId() != 2) {
            HttpSession session = request.getSession();
            SessionUtil.setErrorMessage(session, "You can only edit regular member accounts");
            response.sendRedirect(request.getContextPath() + "/librarian/users");
            return;
        }

        // Set user and dropdown data
        request.setAttribute("user", user);
        request.setAttribute("standings", standingService.getAllStandings());

        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/user-edit.jsp").forward(request, response);
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

        UserAccount user = existingUserOpt.get();

        // Check if user is a regular member (roleId = 2)
        if (user.getRoleId() != 2) {
            HttpSession session = request.getSession();
            SessionUtil.setErrorMessage(session, "You can only edit regular member accounts");
            response.sendRedirect(request.getContextPath() + "/librarian/users");
            return;
        }

        // Update user object - maintain roleId as 2
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setAddress(address);
        user.setAccountStanding(standingId);
        // Keep roleId unchanged (already 2)

        try {
            // Get current librarian user
            HttpSession session = request.getSession();
            long updatedBy = SessionUtil.getCurrentUserId(session);
            String ipAddress = RequestUtil.getClientIpAddress(request);

            // Update user
            userService.updateUser(user, updatedBy, ipAddress);

            // Set success message
            SessionUtil.setSuccessMessage(session, "Member account updated successfully!");

            // Redirect to user list
            response.sendRedirect(request.getContextPath() + "/librarian/users");

        } catch (IllegalArgumentException e) {
            // Update failed
            request.setAttribute("error", e.getMessage());
            request.setAttribute("user", user);
            request.setAttribute("standings", standingService.getAllStandings());
            request.getRequestDispatcher("/WEB-INF/jsp/librarian/user-edit.jsp").forward(request, response);
        }
    }
}
