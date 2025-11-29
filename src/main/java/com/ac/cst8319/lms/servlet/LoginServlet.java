package com.ac.cst8319.lms.servlet;

import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.AuthenticationService;
import com.ac.cst8319.lms.service.AuthenticationService.AuthenticationResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    
    private AuthenticationService authService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils
            .getRequiredWebApplicationContext(getServletContext());
        this.authService = context.getBean(AuthenticationService.class);
        System.out.println("LoginServlet: AuthenticationService initialized successfully");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("LoginServlet: Serving login page via JSP");
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        System.out.println("Login attempt - Username: " + username);
        
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Username and password are required");
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }
        
        AuthenticationResult authResult = authService.authenticate(username, password);
        
        if (authResult.isSuccess()) {
            HttpSession session = request.getSession();
            session.setAttribute("user", authResult.getUserAccount());
            session.setAttribute("username", username);
            session.setAttribute("role", authResult.getUserAccount().getRole().getName());
            session.setMaxInactiveInterval(30 * 60); // 30分钟
            
            System.out.println("Login successful - Role: " + authResult.getUserAccount().getRole().getName());
            
            String role = authResult.getUserAccount().getRole().getName();
            String redirectUrl;
            switch(role.toUpperCase()) {
                case "MANAGER":
                    redirectUrl = "/manager/dashboard";
                    break;
                case "LIBRARIAN":
                    redirectUrl = "/librarian/dashboard";
                    break;
                case "MEMBER":
                    redirectUrl = "/member/dashboard";
                    break;
                default:
                    redirectUrl = "/dashboard";
            }
            response.sendRedirect(request.getContextPath() + redirectUrl);
        } else {
            System.out.println("Login failed: " + authResult.getMessage());
            request.setAttribute("errorMessage", authResult.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        }
    }
}