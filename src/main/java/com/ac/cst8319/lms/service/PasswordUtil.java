package com.ac.cst8319.lms.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {
    
    private final PasswordEncoder passwordEncoder;
    
    public PasswordUtil() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    public String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }
    
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
    
    public PasswordStrength checkPasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            return PasswordStrength.WEAK;
        }
        
        boolean hasUpper = !password.equals(password.toLowerCase());
        boolean hasLower = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
        
        int strengthPoints = 0;
        if (hasUpper) strengthPoints++;
        if (hasLower) strengthPoints++;
        if (hasDigit) strengthPoints++;
        if (hasSpecial) strengthPoints++;
        if (password.length() >= 12) strengthPoints++;
        
        if (strengthPoints >= 4) return PasswordStrength.STRONG;
        if (strengthPoints >= 3) return PasswordStrength.MEDIUM;
        return PasswordStrength.WEAK;
    }
    
    public enum PasswordStrength {
        WEAK, MEDIUM, STRONG
    }
}