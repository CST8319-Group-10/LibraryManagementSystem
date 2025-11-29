package com.ac.cst8319.lms.service;

import com.ac.cst8319.lms.dao.UserAccountDAO;
import com.ac.cst8319.lms.dao.impl.UserAccountDAOImpl;
import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.util.AuditLogger;
import com.ac.cst8319.lms.util.PasswordUtil;
import com.ac.cst8319.lms.util.RoleUtil;
import com.ac.cst8319.lms.util.ValidationUtil;

import java.util.List;
import java.util.Optional;

/**
 * Service for user management operations (admin functions).
 */
public class UserManagementService {

    private final UserAccountDAO userDAO;

    public UserManagementService() {
        this.userDAO = new UserAccountDAOImpl();
    }

    /**
     * Get all users in the system.
     * @return list of all users
     */
    public List<UserAccount> getAllUsers() {
        return userDAO.findAll();
    }

    /**
     * Get a user by ID.
     * @param userId user ID
     * @return Optional containing the user if found
     */
    public Optional<UserAccount> getUserById(long userId) {
        return userDAO.findById(userId);
    }

    /**
     * Get users by role.
     * @param roleId role ID
     * @return list of users with the specified role
     */
    public List<UserAccount> getUsersByRole(int roleId) {
        return userDAO.findByRole(roleId);
    }

    /**
     * Get users by account standing.
     * @param standingId standing ID
     * @return list of users with the specified standing
     */
    public List<UserAccount> getUsersByStanding(int standingId) {
        return userDAO.findByStanding(standingId);
    }

    /**
     * Create a new user (admin function).
     * @param user user account details
     * @param plainPassword plain text password
     * @param createdBy user ID of admin creating the user
     * @param ipAddress IP address
     * @return the created user
     * @throws IllegalArgumentException if validation fails
     */
    public UserAccount createUser(UserAccount user, String plainPassword, long createdBy, String ipAddress) {
        // Validate input
        validateUser(user, plainPassword);

        // Check if email already exists
        if (userDAO.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Hash password
        String passwordHash = PasswordUtil.hashPassword(plainPassword);
        user.setPasswordHash(passwordHash);

        // Ensure role and standing are set
        if (user.getRoleId() == 0) {
            user.setRoleId(RoleUtil.ROLE_REGISTERED_USER);
        }
        if (user.getAccountStanding() == 0) {
            user.setAccountStanding(1); // Good Standing
        }

        // Insert user
        long userId = userDAO.insert(user);
        user.setUserId(userId);

        // Log creation
        String details = String.format("Created user: %s %s (%s)",
                                      user.getFirstName(), user.getLastName(), user.getEmail());
        AuditLogger.logUserCreated(createdBy, userId, details, ipAddress);

        return user;
    }

    /**
     * Update user information.
     * @param user user with updated information
     * @param updatedBy user ID of admin updating the user
     * @param ipAddress IP address
     * @return true if update was successful
     * @throws IllegalArgumentException if validation fails
     */
    public boolean updateUser(UserAccount user, long updatedBy, String ipAddress) {
        // Validate input (without password check)
        validateUserUpdate(user);

        // Check if email is already used by another user
        Optional<UserAccount> existingUser = userDAO.findByEmail(user.getEmail());
        if (existingUser.isPresent() && existingUser.get().getUserId() != user.getUserId()) {
            throw new IllegalArgumentException("Email already used by another user");
        }

        // Update user
        boolean updated = userDAO.update(user);

        if (updated) {
            String details = String.format("Updated user: %s %s (%s)",
                                          user.getFirstName(), user.getLastName(), user.getEmail());
            AuditLogger.logUserUpdated(updatedBy, user.getUserId(), details, ipAddress);
        }

        return updated;
    }

    /**
     * Update user's role.
     * @param userId user ID
     * @param roleId new role ID
     * @param updatedBy user ID of admin making the change
     * @param ipAddress IP address
     * @return true if update was successful
     */
    public boolean updateUserRole(long userId, int roleId, long updatedBy, String ipAddress) {
        // Validate role ID
        if (roleId < 1 || roleId > 4) {
            throw new IllegalArgumentException("Invalid role ID");
        }

        // Update role
        boolean updated = userDAO.updateRole(userId, roleId);

        if (updated) {
            String roleName = RoleUtil.getRoleName(roleId);
            AuditLogger.logRoleAssigned(updatedBy, userId, roleId, roleName, ipAddress);
        }

        return updated;
    }

    /**
     * Delete a user.
     * @param userId user ID to delete
     * @param deletedBy user ID of admin deleting the user
     * @param ipAddress IP address
     * @return true if deletion was successful
     */
    public boolean deleteUser(long userId, long deletedBy, String ipAddress) {
        // Prevent deleting yourself
        if (userId == deletedBy) {
            throw new IllegalArgumentException("Cannot delete your own account");
        }

        // Delete user
        boolean deleted = userDAO.delete(userId);

        if (deleted) {
            AuditLogger.logUserDeleted(deletedBy, userId, ipAddress);
        }

        return deleted;
    }

    /**
     * Get total user count.
     * @return total number of users
     */
    public long getTotalUserCount() {
        return userDAO.count();
    }

    /**
     * Get count of users by role.
     * @param roleId role ID
     * @return count of users with the role
     */
    public long getUserCountByRole(int roleId) {
        return userDAO.countByRole(roleId);
    }

    /**
     * Validate user data (for creation).
     */
    private void validateUser(UserAccount user, String password) {
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

    /**
     * Validate user data (for update, no password check).
     */
    private void validateUserUpdate(UserAccount user) {
        if (!ValidationUtil.isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
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
