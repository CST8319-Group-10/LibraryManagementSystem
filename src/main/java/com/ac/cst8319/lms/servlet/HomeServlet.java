package com.ac.cst8319.lms.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("HomeServlet: Serving home page via JSP");
        try {
            request.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(request, response);
            System.out.println("HomeServlet: Forwarded successfully to home.jsp");
        } catch (Exception e) {
            System.out.println("HomeServlet ERROR: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Home page is temporarily unavailable");
        }
    }
}