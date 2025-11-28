package com.ac.cst8319.lms.util;

import java.util.regex.Pattern;

/**
 * Utility class for input validation.
 */
public class ValidationUtil {

    // Email regex pattern (RFC 5322 simplified)
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    // Password must be at least 8 characters, contain uppercase, lowercase, and digit
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$"
    );

    // Phone number pattern (flexible format)
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[\\d\\s\\-\\(\\)\\+]{10,20}$"
    );

    /**
     * Validate email address format.
     * @param email the email address to validate
     * @return true if valid email format, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validate password strength.
     * Password must be at least 8 characters and contain:
     * - At least one uppercase letter
     * - At least one lowercase letter
     * - At least one digit
     *
     * @param password the password to validate
     * @return true if password meets strength requirements, false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * Get password validation error message.
     * @return error message describing password requirements
     */
    public static String getPasswordRequirements() {
        return "Password must be at least 8 characters and contain uppercase, lowercase, and a number.";
    }

    /**
     * Validate phone number format.
     * @param phone the phone number to validate
     * @return true if valid phone format, false otherwise
     */
    public static boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return true; // Phone is optional
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    /**
     * Sanitize user input to prevent XSS attacks.
     * Escapes HTML special characters.
     *
     * @param input the user input to sanitize
     * @return sanitized input
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        return input
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#x27;")
            .replace("/", "&#x2F;");
    }

    /**
     * Check if a string is null or empty after trimming.
     * @param str the string to check
     * @return true if string is null or empty, false otherwise
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Validate that a string is not empty and meets minimum length.
     * @param str the string to validate
     * @param minLength minimum length required
     * @param fieldName name of the field (for error messages)
     * @return error message if invalid, null if valid
     */
    public static String validateStringLength(String str, int minLength, String fieldName) {
        if (isEmpty(str)) {
            return fieldName + " is required.";
        }
        if (str.trim().length() < minLength) {
            return fieldName + " must be at least " + minLength + " characters.";
        }
        return null; // Valid
    }
}
