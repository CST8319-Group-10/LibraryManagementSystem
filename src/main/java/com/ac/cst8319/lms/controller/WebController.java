package com.ac.cst8319.lms.controller;

import com.ac.cst8319.lms.service.UserService;
import com.ac.cst8319.lms.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String home() {
        return "home";
    }
}