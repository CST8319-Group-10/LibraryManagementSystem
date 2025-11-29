package com.ac.cst8319.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Main Spring Boot application class for Library Management System
 */
@SpringBootApplication
@ServletComponentScan // This enables scanning for @WebServlet, @WebFilter, etc.
public class LibraryManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
    }
}