package com.ac.cst8319.lms.service;

import com.ac.cst8319.lms.dao.UserAccountDAO;
import com.ac.cst8319.lms.dao.impl.UserAccountDAOImpl;
import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.util.AuditLogger;
import com.ac.cst8319.lms.util.PasswordUtil;
import com.ac.cst8319.lms.util.RoleUtil;
import com.ac.cst8319.lms.util.ValidationUtil;

import java.util.Optional;

/**
 * Service for user authentication operations.
 */
public class AuthenticationService {

    private final UserAccountDAO userDAO;

    public AuthenticationService() {
        this.userDAO = new UserAccountDAOImpl();
    }

    /**
     * Authenticate a user with email and password.
     * @param email user's email
     * @param password plain text password
     * @param ipAddress IP address of the request
     * @return Optional containing the authenticated user if successful, empty otherwise
     */
    public Optional<UserAccount> login(String email, String password, String ipAddress) {
        // Validate input
        if (!ValidationUtil.isValidEmail(email) || ValidationUtil.isEmpty(password)) {
            AuditLogger.logLoginFailed(email, ipAddress);
            return Optional.empty();
        }

        // Find user by email
        Optional<UserAccount> userOpt = userDAO.findByEmail(email);
        if (userOpt.isEmpty()) {
            AuditLogger.logLoginFailed(email, ipAddress);
            return Optional.empty();
        }

        UserAccount user = userOpt.get();

        // Verify password
        if (!PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            AuditLogger.logLoginFailed(email, ipAddress);
            return Optional.empty();
        }

        // Check if account is banned
        if (user.getAccountStanding() == 3) { // Banned
            AuditLogger.logLoginFailed(email + " (account banned)", ipAddress);
            return Optional.empty();
        }

        // Update last login timestamp
        userDAO.updateLastLogin(user.getUserId());

        // Log successful login
        AuditLogger.logUserLogin(user.getUserId(), ipAddress);

        return Optional.of(user);
    }

    /**
     * Register a new user.
     * @param user user account with details (password should be plain text)
     * @param plainPassword plain text password
     * @param ipAddress IP address of the request
     * @return the created user account
     * @throws IllegalArgumentException if validation fails
     */
    public UserAccount register(UserAccount user, String plainPassword, String ipAddress) {
        // Validate input
        validateUserRegistration(user, plainPassword);

        // Check if email already exists
        if (userDAO.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Hash password
        String passwordHash = PasswordUtil.hashPassword(plainPassword);
        user.setPasswordHash(passwordHash);

        // Set default values
        user.setRoleId(RoleUtil.ROLE_REGISTERED_USER); // Default role
        user.setAccountStanding(1); // Good Standing

        // Insert user
        long userId = userDAO.insert(user);
        user.setUserId(userId);

        // Log registration
        AuditLogger.logUserRegistration(userId, ipAddress);

        return user;
    }

    /**
     * Log out a user.
     * @param userId user ID
     * @param ipAddress IP address
     */
    public void logout(long userId, String ipAddress) {
        AuditLogger.logUserLogout(userId, ipAddress);
    }

    /**
     * Change user's password.
     * @param userId user ID
     * @param oldPassword current password
     * @param newPassword new password
     * @param ipAddress IP address
     * @return true if password was changed successfully, false otherwise
     */
    public boolean changePassword(long userId, String oldPassword, String newPassword, String ipAddress) {
        // Get user
        Optional<UserAccount> userOpt = userDAO.findById(userId);
        if (userOpt.isEmpty()) {
            return false;
        }

        UserAccount user = userOpt.get();

        // Verify old password
        if (!PasswordUtil.verifyPassword(oldPassword, user.getPasswordHash())) {
            return false;
        }

        // Validate new password
        if (!ValidationUtil.isValidPassword(newPassword)) {
            throw new IllegalArgumentException(ValidationUtil.getPasswordRequirements());
        }

        // Hash new password
        String newPasswordHash = PasswordUtil.hashPassword(newPassword);

        // Update password
        boolean updated = userDAO.updatePassword(userId, newPasswordHash);

        if (updated) {
            AuditLogger.logPasswordChange(userId, ipAddress);
        }

        return updated;
    }

    /**
     * Validate user registration data.
     * @param user user account
     * @param password plain text password
     * @throws IllegalArgumentException if validation fails
     */
    private void validateUserRegistration(UserAccount user, String password) {
        if (!ValidationUtil.isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
        }

        if (!ValidationUtil.isValidPassword(password)) {
            throw new IllegalArgumentException(ValidationUtil.getPasswordRequirements());
        }

        String error = ValidationUtil.validateStringLength(user.getFirstName(), 1, "First name");
        if (error != null) {
            throw new IllegalArgumentException(error);
        }

        error = ValidationUtil.validateStringLength(user.getLastName(), 1, "Last name");
        if (error != null) {
            throw new IllegalArgumentException(error);
        }

        if (user.getPhone() != null && !ValidationUtil.isValidPhoneNumber(user.getPhone())) {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }
}
