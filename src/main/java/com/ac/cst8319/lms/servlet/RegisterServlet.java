package com.ac.cst8319.lms.servlet;

import com.ac.cst8319.lms.model.Member;
import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.AuthenticationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    
    private AuthenticationService authService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils
            .getRequiredWebApplicationContext(getServletContext());
        this.authService = context.getBean(AuthenticationService.class);
        System.out.println("RegisterServlet: AuthenticationService initialized successfully");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("RegisterServlet: Serving register page via JSP");
        request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("RegisterServlet: Processing registration request");
        
        // Get form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String role = request.getParameter("role");
        
        System.out.println("Registration attempt - Username: " + username + ", Email: " + email);
        
        // Validate required fields
        if (username == null || password == null || email == null || 
            firstName == null || lastName == null) {
            request.setAttribute("errorMessage", "All required fields must be filled");
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
            return;
        }
        
        // Create Member user
        Member member = new Member();
        member.setUsername(username.trim());
        member.setEmail(email.trim());
        member.setFirstName(firstName.trim());
        member.setLastName(lastName.trim());
        member.setPhoneNumber(phoneNumber != null ? phoneNumber.trim() : null);
        member.setAddress(address != null ? address.trim() : null);
        
        // Register user
        AuthenticationService.RegistrationResult result = authService.register(member, password, "MEMBER");
        
        if (result.isSuccess()) {
            System.out.println("Registration successful for user: " + username);
            response.sendRedirect(request.getContextPath() + "/login?success=" + 
                java.net.URLEncoder.encode("Registration successful! Please login.", "UTF-8"));
        } else {
            System.out.println("Registration failed: " + result.getMessage());
            request.setAttribute("errorMessage", result.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
        }
    }
}