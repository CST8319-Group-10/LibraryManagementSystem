package com.ac.cst8319.lms.filter;

import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.util.AuditLogger;
import com.ac.cst8319.lms.util.RequestUtil;
import com.ac.cst8319.lms.util.RoleUtil;
import com.ac.cst8319.lms.util.SessionUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filter to enforce role-based access control.
 */
@WebFilter(urlPatterns = {"/admin/*"})
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Get current user from session
        UserAccount currentUser = SessionUtil.getCurrentUser(session);

        if (currentUser == null) {
            // This shouldn't happen if AuthenticationFilter is working
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        // Check if user has admin permission
        String requestUri = httpRequest.getRequestURI();

        if (requestUri.startsWith(httpRequest.getContextPath() + "/admin/")) {
            // Requires administrator role
            if (!RoleUtil.isAdministrator(currentUser)) {
                // Log unauthorized access attempt
                String ipAddress = RequestUtil.getClientIpAddress(httpRequest);
                String details = String.format("Unauthorized access attempt to: %s", requestUri);
                AuditLogger.log(currentUser.getUserId(), 0, details, ipAddress);

                // Return 403 Forbidden
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,
                                      "Access Denied. Administrator privileges required.");
                return;
            }
        }

        // User is authorized, continue
        chain.doFilter(request, response);
    }
}
