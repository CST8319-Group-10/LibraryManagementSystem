package com.ac.cst8319.lms.servlet.auth;

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
 * Servlet for user logout.
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    private final AuthenticationService authService;

    public LogoutServlet() {
        this.authService = new AuthenticationService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Get user ID before invalidating session
        long userId = SessionUtil.getCurrentUserId(session);

        if (userId != -1) {
            // Log logout event
            String ipAddress = RequestUtil.getClientIpAddress(request);
            authService.logout(userId, ipAddress);
        }

        // Invalidate session
        SessionUtil.invalidateSession(session);

        // Redirect to login page
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect GET requests to POST (for convenience)
        doPost(request, response);
    }
}
