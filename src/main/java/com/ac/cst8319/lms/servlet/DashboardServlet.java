package com.ac.cst8319.lms.servlet;

import com.ac.cst8319.lms.model.UserAccount;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard", "/member/dashboard", "/librarian/dashboard", "/manager/dashboard"})
public class DashboardServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("DashboardServlet: No valid session, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        UserAccount user = (UserAccount) session.getAttribute("user");
        String role = user.getRole().getName();
        String username = user.getUsername();
        
        System.out.println("DashboardServlet: User '" + username + "' with role '" + role + "' accessing dashboard");
        
        String dashboardPage;
        switch (role.toUpperCase()) {
            case "MANAGER":
                dashboardPage = "/WEB-INF/jsp/manager-dashboard.jsp";
                System.out.println("DashboardServlet: Redirecting to manager dashboard");
                break;
            case "LIBRARIAN":
                dashboardPage = "/WEB-INF/jsp/librarian-dashboard.jsp";
                System.out.println("DashboardServlet: Redirecting to librarian dashboard");
                break;
            case "MEMBER":
                dashboardPage = "/WEB-INF/jsp/member-dashboard.jsp";
                System.out.println("DashboardServlet: Redirecting to member dashboard");
                break;
            default:
                dashboardPage = "/WEB-INF/jsp/member-dashboard.jsp";
                System.out.println("DashboardServlet: Unknown role, defaulting to member dashboard");
                break;
        }
        
        
        request.setAttribute("currentUser", user);
        request.setAttribute("userRole", role);
        
        try {
            request.getRequestDispatcher(dashboardPage).forward(request, response);
            System.out.println("DashboardServlet: Successfully forwarded to " + dashboardPage);
        } catch (Exception e) {
            System.out.println("DashboardServlet ERROR: Failed to forward to dashboard page - " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/login?error=dashboard_error");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        doGet(request, response);
    }
}