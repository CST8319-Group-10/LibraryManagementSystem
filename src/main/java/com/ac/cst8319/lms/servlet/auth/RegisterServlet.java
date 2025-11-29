package com.ac.cst8319.lms.servlet.auth;

import com.ac.cst8319.lms.model.UserAccount;
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
 * Servlet for user registration.
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private final AuthenticationService authService;

    public RegisterServlet() {
        this.authService = new AuthenticationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // If already logged in, redirect to dashboard
        if (SessionUtil.isAuthenticated(session)) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        // Forward to registration page
        request.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters
        String email = RequestUtil.getStringParameter(request, "email");
        String password = RequestUtil.getStringParameter(request, "password");
        String confirmPassword = RequestUtil.getStringParameter(request, "confirmPassword");
        String firstName = RequestUtil.getStringParameter(request, "firstName");
        String lastName = RequestUtil.getStringParameter(request, "lastName");
        String phone = RequestUtil.getStringParameter(request, "phone");
        String address = RequestUtil.getStringParameter(request, "address");
        String ipAddress = RequestUtil.getClientIpAddress(request);

        // Validate passwords match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(request, response);
            return;
        }

        // Create user object
        UserAccount user = new UserAccount();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setAddress(address);

        try {
            // Register user
            UserAccount registeredUser = authService.register(user, password, ipAddress);

            // Auto-login: set user in session
            HttpSession session = request.getSession(true);
            SessionUtil.setCurrentUser(session, registeredUser);

            // Set success message
            SessionUtil.setSuccessMessage(session, "Registration successful! Welcome to the Library Management System.");

            // Redirect to dashboard
            response.sendRedirect(request.getContextPath() + "/dashboard");

        } catch (IllegalArgumentException e) {
            // Registration failed due to validation error
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(request, response);
        }
    }
}
