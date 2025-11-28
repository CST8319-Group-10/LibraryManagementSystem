package com.ac.cst8319.lms.filter;

import com.ac.cst8319.lms.util.SessionUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filter to ensure users are authenticated before accessing protected resources.
 */
@WebFilter(urlPatterns = {"/dashboard", "/dashboard/*", "/admin/*", "/user/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Check if user is authenticated
        if (!SessionUtil.isAuthenticated(session)) {
            // Store the originally requested URL for post-login redirect
            String requestedUrl = httpRequest.getRequestURI();
            String queryString = httpRequest.getQueryString();
            if (queryString != null) {
                requestedUrl += "?" + queryString;
            }

            // Store in session (create session if needed)
            HttpSession newSession = httpRequest.getSession(true);
            newSession.setAttribute("originalUrl", requestedUrl);

            // Redirect to login page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        // User is authenticated, continue
        chain.doFilter(request, response);
    }
}
