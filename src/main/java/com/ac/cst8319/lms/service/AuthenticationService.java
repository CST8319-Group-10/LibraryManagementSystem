package com.ac.cst8319.lms.service;

import com.ac.cst8319.lms.model.AccountStanding;
import com.ac.cst8319.lms.model.Role;
import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.repository.AccountStandingRepository;
import com.ac.cst8319.lms.repository.RoleRepository;
import com.ac.cst8319.lms.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service class for authentication and authorization operations
 * Handles user registration, login, password management and account status
 */
@Service
@Transactional
public class AuthenticationService {
    
    @Autowired
    private UserAccountRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private AccountStandingRepository accountStandingRepository;
    
    @Autowired
    private PasswordUtil passwordUtil;
    
    /**
     * Authenticate user login - returns UserAccount object
     * @param username the username
     * @param password the password
     * @return authentication result with UserAccount
     */
   public AuthenticationResult authenticate(String username, String password) {
    System.out.println("AuthenticationService: Authenticating user - " + username);
    
    Optional<UserAccount> userOpt = userRepository.findByUsername(username);
    
    if (userOpt.isEmpty()) {
        System.out.println("AuthenticationService: User not found - " + username);
        return new AuthenticationResult(false, "Invalid username or password", null);
    }
    
    UserAccount user = userOpt.get();
    System.out.println("AuthenticationService: User found - " + user.getUsername());
    System.out.println("AuthenticationService: User role - " + (user.getRole() != null ? user.getRole().getName() : "NULL"));
    System.out.println("AuthenticationService: Account standing - " + (user.getAccountStanding() != null ? user.getAccountStanding().getName() : "NULL"));
    
    // Check if account is locked due to failed login attempts
    if (Boolean.TRUE.equals(user.getAccountLocked())) {
        return new AuthenticationResult(false, "Account is locked due to too many failed login attempts. Please contact administrator.", null);
    }
    
    // Check account standing
    AccountStanding standing = user.getAccountStanding();
    if (standing != null) {
        switch (standing.getName()) {
            case "SUSPENDED":
                return new AuthenticationResult(false, "Account is suspended. Please contact library staff.", null);
            case "DISABLED":
                return new AuthenticationResult(false, "Account is disabled. Please contact administrator.", null);
            case "PENDING_VERIFICATION":
                return new AuthenticationResult(false, "Account pending email verification. Please check your email.", null);
            case "ACTIVE":
                // Continue with login
                break;
            default:
                return new AuthenticationResult(false, "Account status is invalid. Please contact administrator.", null);
        }
    }
    
    // DEBUG: Add password verification details
    System.out.println("=== PASSWORD VERIFICATION DEBUG ===");
    System.out.println("AuthenticationService: Input password - '" + password + "'");
    System.out.println("AuthenticationService: Stored hash - " + user.getPassword());
    System.out.println("AuthenticationService: Hash length - " + (user.getPassword() != null ? user.getPassword().length() : "NULL"));
    
    // Verify password
    boolean passwordMatch = passwordUtil.verifyPassword(password, user.getPassword());
    System.out.println("AuthenticationService: Password match result - " + passwordMatch);
    System.out.println("=== END DEBUG ===");
    
    if (!passwordMatch) {
        // Increment failed login attempts - handle null values
        Integer currentAttempts = user.getFailedLoginAttempts();
        if (currentAttempts == null) {
            currentAttempts = 0;
        }
        user.setFailedLoginAttempts(currentAttempts + 1);
        
        // Lock account after 5 failed attempts
        if (user.getFailedLoginAttempts() >= 5) {
            user.setAccountLocked(true);
            userRepository.save(user);
            return new AuthenticationResult(false, "Account locked due to too many failed login attempts. Please contact administrator.", null);
        }
        
        userRepository.save(user);
        int attemptsLeft = 5 - user.getFailedLoginAttempts();
        return new AuthenticationResult(false, 
            String.format("Invalid username or password. %d attempts remaining.", attemptsLeft), null);
    }
    
    // Successful login - reset failed attempts and update last login
    user.setFailedLoginAttempts(0);
    user.setLastLogin(LocalDateTime.now());
    userRepository.save(user);
    
    System.out.println("AuthenticationService: Login successful for user - " + username);
    return new AuthenticationResult(true, "Login successful", user);
}
    
    /**
     * Maintain original login method for compatibility, but mark as deprecated
     * @deprecated Use {@link #authenticate(String, String)} instead
     */
    @Deprecated
    public AuthenticationResult login(String username, String password) {
        return authenticate(username, password);
    }
    
    /**
     * Register new user account
     * @param user the user account to register
     * @param plainPassword the plain text password
     * @param roleName the role name
     * @return registration result
     */
    public RegistrationResult register(UserAccount user, String plainPassword, String roleName) {
        System.out.println("AuthenticationService: Registering new user - " + user.getUsername());
        
        // Check if username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            return new RegistrationResult(false, "Username already exists");
        }
        
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            return new RegistrationResult(false, "Email already exists");
        }
        
        // Check password strength
        PasswordUtil.PasswordStrength strength = passwordUtil.checkPasswordStrength(plainPassword);
        if (strength == PasswordUtil.PasswordStrength.WEAK) {
            return new RegistrationResult(false, "Password is too weak. Please use a stronger password with at least 8 characters including uppercase, lowercase, numbers, and special characters.");
        }
        
        // Get role from database
        Optional<Role> roleOpt = roleRepository.findByName(roleName.toUpperCase());
        if (roleOpt.isEmpty()) {
            return new RegistrationResult(false, "Invalid role specified: " + roleName);
        }
        
        // Get default account standing (ACTIVE for now)
        Optional<AccountStanding> standingOpt = accountStandingRepository.findActiveStanding();
        if (standingOpt.isEmpty()) {
            return new RegistrationResult(false, "System error: Active account standing not found");
        }
        
        // Hash password and set role/standing
        user.setPassword(passwordUtil.hashPassword(plainPassword));
        user.setRole(roleOpt.get());
        user.setAccountStanding(standingOpt.get());
        
        try {
            userRepository.save(user);
            System.out.println("AuthenticationService: User registered successfully - " + user.getUsername());
            return new RegistrationResult(true, roleName + " account created successfully");
        } catch (Exception e) {
            System.out.println("AuthenticationService: Registration failed - " + e.getMessage());
            return new RegistrationResult(false, "Registration failed: " + e.getMessage());
        }
    }
    
    /**
     * Change user password
     * @param username the username
     * @param currentPassword the current password
     * @param newPassword the new password
     * @return true if password changed successfully, false otherwise
     */
    public boolean changePassword(String username, String currentPassword, String newPassword) {
        System.out.println("AuthenticationService: Changing password for user - " + username);
        
        Optional<UserAccount> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isEmpty()) {
            return false;
        }
        
        UserAccount user = userOpt.get();
        
        // Verify current password
        if (!passwordUtil.verifyPassword(currentPassword, user.getPassword())) {
            return false;
        }
        
        // Check new password strength
        PasswordUtil.PasswordStrength strength = passwordUtil.checkPasswordStrength(newPassword);
        if (strength == PasswordUtil.PasswordStrength.WEAK) {
            return false;
        }
        
        // Update password
        user.setPassword(passwordUtil.hashPassword(newPassword));
        userRepository.save(user);
        
        System.out.println("AuthenticationService: Password changed successfully for user - " + username);
        return true;
    }
    
    /**
     * Check if user has specific role
     * @param username the username
     * @param roleName the role name to check
     * @return true if user has the role, false otherwise
     */
    public boolean hasRole(String username, String roleName) {
        Optional<UserAccount> userOpt = userRepository.findByUsername(username);
        return userOpt.isPresent() && 
               userOpt.get().getRole() != null && 
               roleName.equalsIgnoreCase(userOpt.get().getRole().getName());
    }
    
    /**
     * Get default role (for Member registration)
     * @return the default MEMBER role
     */
    public Role getDefaultRole() {
        return roleRepository.findByName("MEMBER")
                .orElseThrow(() -> new RuntimeException("Default MEMBER role not found in database"));
    }
    
    /**
     * Get account standing for a user
     * @param username the username
     * @return the account standing or null if user not found
     */
    public AccountStanding getAccountStanding(String username) {
        Optional<UserAccount> userOpt = userRepository.findByUsername(username);
        return userOpt.map(UserAccount::getAccountStanding).orElse(null);
    }
    
    /**
     * Activate user account
     * @param userId the user ID
     * @return true if activated successfully, false otherwise
     */
    public boolean activateAccount(Long userId) {
        System.out.println("AuthenticationService: Activating account for user ID - " + userId);
        
        Optional<UserAccount> userOpt = userRepository.findById(userId);
        Optional<AccountStanding> activeOpt = accountStandingRepository.findByName("ACTIVE");
        
        if (userOpt.isEmpty() || activeOpt.isEmpty()) {
            return false;
        }
        
        UserAccount user = userOpt.get();
        user.setAccountStanding(activeOpt.get());
        user.setAccountLocked(false);
        user.setFailedLoginAttempts(0);
        userRepository.save(user);
        
        System.out.println("AuthenticationService: Account activated successfully for user - " + user.getUsername());
        return true;
    }
    
    /**
     * Suspend user account
     * @param userId the user ID
     * @param reason the reason for suspension
     * @return true if suspended successfully, false otherwise
     */
    public boolean suspendAccount(Long userId, String reason) {
        System.out.println("AuthenticationService: Suspending account for user ID - " + userId);
        
        Optional<UserAccount> userOpt = userRepository.findById(userId);
        Optional<AccountStanding> suspendedOpt = accountStandingRepository.findByName("SUSPENDED");
        
        if (userOpt.isEmpty() || suspendedOpt.isEmpty()) {
            return false;
        }
        
        UserAccount user = userOpt.get();
        user.setAccountStanding(suspendedOpt.get());
        userRepository.save(user);
        
        // In a real application, you would log this to an audit log
        System.out.println("Account suspended: " + user.getUsername() + " - Reason: " + reason);
        return true;
    }
    
    /**
     * Authentication result container - modified to include UserAccount
     */
    public static class AuthenticationResult {
        private final boolean success;
        private final String message;
        private final UserAccount userAccount;
        
        public AuthenticationResult(boolean success, String message, UserAccount userAccount) {
            this.success = success;
            this.message = message;
            this.userAccount = userAccount;
        }
        
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public UserAccount getUserAccount() { return userAccount; }
        
        // For backward compatibility, keep these methods but mark as deprecated
        @Deprecated
        public String getToken() { return null; }
        
        @Deprecated
        public String getRole() { 
            return userAccount != null && userAccount.getRole() != null ? 
                   userAccount.getRole().getName() : null; 
        }
    }
    
    /**
     * Registration result container - unchanged
     */
    public static class RegistrationResult {
        private final boolean success;
        private final String message;
        
        public RegistrationResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
    }
}