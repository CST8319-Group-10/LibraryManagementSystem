package com.ac.cst8319.lms.servlet;

import com.ac.cst8319.lms.service.PasswordUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/debug-password")
public class DebugPasswordServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("=== PASSWORD DEBUG INFORMATION ===");
        out.println();
        
        // 测试哈希
        String testHash = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVwE.";
        
        out.println("Test Hash: " + testHash);
        out.println("Hash Length: " + testHash.length());
        out.println();
        
        // 测试1：直接使用BCryptPasswordEncoder
        out.println("--- DIRECT BCryptPasswordEncoder TEST ---");
        BCryptPasswordEncoder directEncoder = new BCryptPasswordEncoder();
        
        boolean test123Match = directEncoder.matches("test123", testHash);
        out.println("test123 match: " + test123Match);
        
        boolean admin123Match = directEncoder.matches("admin123", testHash);
        out.println("admin123 match: " + admin123Match);
        
        boolean lib123Match = directEncoder.matches("lib123", testHash);
        out.println("lib123 match: " + lib123Match);
        
        boolean member123Match = directEncoder.matches("member123", testHash);
        out.println("member123 match: " + member123Match);
        out.println();
        
        // 测试2：使用您的PasswordUtil
        out.println("--- PasswordUtil TEST ---");
        try {
            PasswordUtil passwordUtil = new PasswordUtil();
            
            boolean utilTest123 = passwordUtil.verifyPassword("test123", testHash);
            out.println("PasswordUtil test123 match: " + utilTest123);
            
            boolean utilAdmin123 = passwordUtil.verifyPassword("admin123", testHash);
            out.println("PasswordUtil admin123 match: " + utilAdmin123);
            
        } catch (Exception e) {
            out.println("PasswordUtil error: " + e.getMessage());
            e.printStackTrace(out);
        }
        out.println();
        
        // 测试3：生成新哈希并验证
        out.println("--- NEW HASH GENERATION TEST ---");
        String newHash = directEncoder.encode("test123");
        out.println("New generated hash: " + newHash);
        out.println("New hash verification: " + directEncoder.matches("test123", newHash));

        out.println("--- FIXED HASH TEST ---");
        String fixedHash = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVwE."; // 注意结尾的点号
        out.println("Fixed hash: " + fixedHash);
        out.println("Fixed hash length: " + fixedHash.length());
        out.println("Fixed hash test123 match: " + directEncoder.matches("test123", fixedHash));

        String workingHash = "$2a$10$36mNqLHXfoMPUHLoNrN87uAUb/AM26yBjSWnuKtYAhuNXcgKXzIxi";
        out.println("Working hash test123 match: " + directEncoder.matches("test123", workingHash));
        
        out.println();
        out.println("=== DEBUG COMPLETE ===");
    }
}