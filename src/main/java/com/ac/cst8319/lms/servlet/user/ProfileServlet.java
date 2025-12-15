package com.ac.cst8319.lms.servlet.user;

import com.ac.cst8319.lms.model.UserAccount;
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
 * Servlet for users to edit their own profile information.
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/user/profile"})
public class ProfileServlet extends HttpServlet {

    private final UserManagementService userService;

    public ProfileServlet() {
        this.userService = new UserManagementService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserAccount currentUser = SessionUtil.getCurrentUser(session);

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get fresh user data from database
        Optional<UserAccount> userOpt = userService.getUserById(currentUser.getUserId());

        if (userOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            return;
        }

        // Set user data for the form
        request.setAttribute("user", userOpt.get());

        // Check for messages
        String successMessage = SessionUtil.getAndClearSuccessMessage(session);
        String errorMessage = SessionUtil.getAndClearErrorMessage(session);

        if (successMessage != null) {
            request.setAttribute("success", successMessage);
        }
        if (errorMessage != null) {
            request.setAttribute("error", errorMessage);
        }

        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/jsp/user/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserAccount currentUser = SessionUtil.getCurrentUser(session);

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get form parameters
        String firstName = RequestUtil.getStringParameter(request, "firstName");
        String lastName = RequestUtil.getStringParameter(request, "lastName");
        String phone = RequestUtil.getStringParameter(request, "phone");
        String address = RequestUtil.getStringParameter(request, "address");

        // Get current user data from database
        Optional<UserAccount> existingUserOpt = userService.getUserById(currentUser.getUserId());

        if (existingUserOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            return;
        }

        UserAccount user = existingUserOpt.get();

        // Update only editable fields
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setAddress(address);
        // Keep email, roleId, and accountStanding unchanged

        try {
            // Update user
            String ipAddress = RequestUtil.getClientIpAddress(request);
            userService.updateUser(user, currentUser.getUserId(), ipAddress);

            // Update session with new data
            session.setAttribute("currentUser", user);

            // Set success message
            SessionUtil.setSuccessMessage(session, "Profile updated successfully!");

            // Redirect to profile page
            response.sendRedirect(request.getContextPath() + "/user/profile");

        } catch (IllegalArgumentException e) {
            // Update failed
            request.setAttribute("error", e.getMessage());
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/jsp/user/profile.jsp").forward(request, response);
        }
    }
}
