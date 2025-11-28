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
import java.util.Optional;

/**
 * Servlet for user login.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private final AuthenticationService authService;

    public LoginServlet() {
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

        // Forward to login page
        request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters
        String email = RequestUtil.getStringParameter(request, "email");
        String password = RequestUtil.getStringParameter(request, "password");
        String ipAddress = RequestUtil.getClientIpAddress(request);

        // Validate input
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("errorMessage", "Email and password are required");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
            return;
        }

        // Attempt login
        Optional<UserAccount> userOpt = authService.login(email, password, ipAddress);

        if (userOpt.isEmpty()) {
            // Login failed
            request.setAttribute("errorMessage", "Invalid email or password");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
            return;
        }

        // Login successful
        UserAccount user = userOpt.get();
        HttpSession session = request.getSession(true);
        SessionUtil.setCurrentUser(session, user);

        // Check if there was an original URL to redirect to
        String originalUrl = (String) session.getAttribute("originalUrl");
        if (originalUrl != null) {
            session.removeAttribute("originalUrl");
            response.sendRedirect(request.getContextPath() + originalUrl);
        } else {
            // Redirect to dashboard
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }
}
