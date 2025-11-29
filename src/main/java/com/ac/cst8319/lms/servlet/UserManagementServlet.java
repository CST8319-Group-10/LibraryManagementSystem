package com.ac.cst8319.lms.servlet;

import com.ac.cst8319.lms.model.Librarian;
import com.ac.cst8319.lms.model.Manager;
import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/admin/users/*")
public class UserManagementServlet extends HttpServlet {
    
    private AuthenticationService authService;
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils
            .getRequiredWebApplicationContext(getServletContext());
        this.authService = context.getBean(AuthenticationService.class);
        System.out.println("UserManagementServlet: AuthenticationService initialized successfully");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"success\": false, \"message\": \"Access denied - Not logged in\"}");
            return;
        }
        
        UserAccount user = (UserAccount) session.getAttribute("user");
        if (!"MANAGER".equals(user.getRole().getName())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"success\": false, \"message\": \"Access denied - Manager role required\"}");
            return;
        }
        
        String pathInfo = request.getPathInfo();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            if ("/create-librarian".equals(pathInfo)) {
                handleCreateLibrarian(request, response, result);
            } else if ("/create-manager".equals(pathInfo)) {
                handleCreateManager(request, response, result);
            } else {
                result.put("success", false);
                result.put("message", "Invalid endpoint");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Server error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        
        objectMapper.writeValue(response.getWriter(), result);
    }
    
    private void handleCreateLibrarian(HttpServletRequest request, HttpServletResponse response,
                                     Map<String, Object> result) throws IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String education = request.getParameter("education");
        String experience = request.getParameter("experience");
        
        if (username == null || password == null || email == null || 
            firstName == null || lastName == null) {
            result.put("success", false);
            result.put("message", "All required fields must be filled");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        Librarian librarian = new Librarian();
        librarian.setUsername(username.trim());
        librarian.setEmail(email.trim());
        librarian.setFirstName(firstName.trim());
        librarian.setLastName(lastName.trim());
        librarian.setPhoneNumber(phoneNumber != null ? phoneNumber.trim() : null);
        librarian.setEducation(education != null ? education.trim() : null);
        librarian.setExperience(experience != null ? experience.trim() : null);
        
        AuthenticationService.RegistrationResult regResult = authService.register(librarian, password, "LIBRARIAN");
        
        if (regResult.isSuccess()) {
            result.put("success", true);
            result.put("message", "Librarian account created successfully");
        } else {
            result.put("success", false);
            result.put("message", regResult.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    private void handleCreateManager(HttpServletRequest request, HttpServletResponse response,
                                   Map<String, Object> result) throws IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String department = request.getParameter("department");
        
        if (username == null || password == null || email == null || 
            firstName == null || lastName == null) {
            result.put("success", false);
            result.put("message", "All required fields must be filled");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        Manager manager = new Manager();
        manager.setUsername(username.trim());
        manager.setEmail(email.trim());
        manager.setFirstName(firstName.trim());
        manager.setLastName(lastName.trim());
        manager.setPhoneNumber(phoneNumber != null ? phoneNumber.trim() : null);
        manager.setDepartment(department != null ? department.trim() : null);
        
        AuthenticationService.RegistrationResult regResult = authService.register(manager, password, "MANAGER");
        
        if (regResult.isSuccess()) {
            result.put("success", true);
            result.put("message", "Manager account created successfully");
        } else {
            result.put("success", false);
            result.put("message", regResult.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"success\": false, \"message\": \"Access denied\"}");
            return;
        }
        
        UserAccount user = (UserAccount) session.getAttribute("user");
        if (!"MANAGER".equals(user.getRole().getName())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"success\": false, \"message\": \"Manager access required\"}");
            return;
        }
        
        // 返回用户管理页面或用户列表
        response.setContentType("application/json");
        response.getWriter().write("{\"success\": true, \"message\": \"User management access granted\"}");
    }
}