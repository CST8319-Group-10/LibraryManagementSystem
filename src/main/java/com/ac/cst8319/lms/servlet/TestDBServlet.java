package com.ac.cst8319.lms.servlet;

import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.repository.UserAccountRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/test-db")
public class TestDBServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("=== DATABASE CONNECTION TEST ===");
        out.println();
        
        try {
            WebApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
            UserAccountRepository userRepository = context.getBean(UserAccountRepository.class);
            
            // 测试查询用户
            Optional<UserAccount> adminOpt = userRepository.findByUsername("admin");
            Optional<UserAccount> librarianOpt = userRepository.findByUsername("librarian1");
            Optional<UserAccount> memberOpt = userRepository.findByUsername("testmember");
            
            out.println("Admin user found: " + adminOpt.isPresent());
            if (adminOpt.isPresent()) {
                out.println("Admin password hash: " + adminOpt.get().getPassword());
                out.println("Admin hash length: " + adminOpt.get().getPassword().length());
            }
            
            out.println("Librarian user found: " + librarianOpt.isPresent());
            if (librarianOpt.isPresent()) {
                out.println("Librarian password hash: " + librarianOpt.get().getPassword());
            }
            
            out.println("Member user found: " + memberOpt.isPresent());
            if (memberOpt.isPresent()) {
                out.println("Member password hash: " + memberOpt.get().getPassword());
            }
            
        } catch (Exception e) {
            out.println("Database error: " + e.getMessage());
            e.printStackTrace(out);
        }
        
        out.println();
        out.println("=== DATABASE TEST COMPLETE ===");
    }
}