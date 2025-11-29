package com.ac.cst8319.lms.util;

import com.ac.cst8319.lms.model.AuditLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;

/**
 * Utility class for audit logging.
 * Logs user actions to the AuditLog table.
 */
public class AuditLogger {

    // Audit Action IDs (must match AuditLogAction table)
    public static final int ACTION_USER_LOGIN = 1;
    public static final int ACTION_USER_LOGOUT = 2;
    public static final int ACTION_LOGIN_FAILED = 3;
    public static final int ACTION_USER_REGISTERED = 4;
    public static final int ACTION_PASSWORD_CHANGED = 5;
    public static final int ACTION_USER_CREATED = 10;
    public static final int ACTION_USER_UPDATED = 11;
    public static final int ACTION_USER_DELETED = 12;
    public static final int ACTION_ROLE_ASSIGNED = 20;
    public static final int ACTION_ROLE_REVOKED = 21;
    public static final int ACTION_STANDING_UPDATED = 30;
    public static final int ACTION_ACCOUNT_SUSPENDED = 31;
    public static final int ACTION_ACCOUNT_REINSTATED = 32;

    /**
     * Log an audit entry to the database.
     * @param userId the user who performed the action
     * @param actionId the action ID from AuditLogAction table
     * @param details additional details about the action (JSON or text)
     * @param ipAddress IP address of the user
     */
    public static void log(Long userId, int actionId, String details, String ipAddress) {
        String sql = "INSERT INTO AuditLog (UserID, ActionID, Details, IpAddress, CreatedAt) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (userId != null && userId > 0) {
                stmt.setLong(1, userId);
            } else {
                stmt.setNull(1, java.sql.Types.BIGINT);
            }
            stmt.setInt(2, actionId);
            stmt.setString(3, details);
            stmt.setString(4, ipAddress);
            stmt.setObject(5, Instant.now());

            stmt.executeUpdate();

        } catch (SQLException e) {
            // Log error but don't throw exception to prevent disrupting application flow
            System.err.println("Error logging audit entry: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Log a user login event.
     * @param userId the user who logged in
     * @param ipAddress IP address
     */
    public static void logUserLogin(long userId, String ipAddress) {
        log(userId, ACTION_USER_LOGIN, "User logged in successfully", ipAddress);
    }

    /**
     * Log a user logout event.
     * @param userId the user who logged out
     * @param ipAddress IP address
     */
    public static void logUserLogout(long userId, String ipAddress) {
        log(userId, ACTION_USER_LOGOUT, "User logged out", ipAddress);
    }

    /**
     * Log a failed login attempt.
     * @param email the email used in failed attempt
     * @param ipAddress IP address
     */
    public static void logLoginFailed(String email, String ipAddress) {
        log(null, ACTION_LOGIN_FAILED, "Failed login attempt for email: " + email, ipAddress);
    }

    /**
     * Log a new user registration.
     * @param userId the newly registered user ID
     * @param ipAddress IP address
     */
    public static void logUserRegistration(long userId, String ipAddress) {
        log(userId, ACTION_USER_REGISTERED, "New user registered", ipAddress);
    }

    /**
     * Log a password change.
     * @param userId the user whose password was changed
     * @param ipAddress IP address
     */
    public static void logPasswordChange(long userId, String ipAddress) {
        log(userId, ACTION_PASSWORD_CHANGED, "User changed password", ipAddress);
    }

    /**
     * Log user creation by admin.
     * @param performedBy the admin who created the user
     * @param targetUserId the newly created user ID
     * @param details additional details
     * @param ipAddress IP address
     */
    public static void logUserCreated(long performedBy, long targetUserId, String details, String ipAddress) {
        String fullDetails = String.format("Admin created user ID %d. %s", targetUserId, details);
        log(performedBy, ACTION_USER_CREATED, fullDetails, ipAddress);
    }

    /**
     * Log user update by admin.
     * @param performedBy the admin who updated the user
     * @param targetUserId the updated user ID
     * @param details what was updated
     * @param ipAddress IP address
     */
    public static void logUserUpdated(long performedBy, long targetUserId, String details, String ipAddress) {
        String fullDetails = String.format("Admin updated user ID %d. %s", targetUserId, details);
        log(performedBy, ACTION_USER_UPDATED, fullDetails, ipAddress);
    }

    /**
     * Log user deletion by admin.
     * @param performedBy the admin who deleted the user
     * @param targetUserId the deleted user ID
     * @param ipAddress IP address
     */
    public static void logUserDeleted(long performedBy, long targetUserId, String ipAddress) {
        String details = String.format("Admin deleted user ID %d", targetUserId);
        log(performedBy, ACTION_USER_DELETED, details, ipAddress);
    }

    /**
     * Log role assignment.
     * @param performedBy the admin who assigned the role
     * @param targetUserId the user who received the role
     * @param roleId the role ID assigned
     * @param roleName the role name
     * @param ipAddress IP address
     */
    public static void logRoleAssigned(long performedBy, long targetUserId, int roleId, String roleName, String ipAddress) {
        String details = String.format("Admin assigned role '%s' (ID %d) to user ID %d", roleName, roleId, targetUserId);
        log(performedBy, ACTION_ROLE_ASSIGNED, details, ipAddress);
    }

    /**
     * Log account standing update.
     * @param performedBy the admin/librarian who updated standing
     * @param targetUserId the user whose standing was updated
     * @param standingId the new standing ID
     * @param standingName the standing name
     * @param reason reason for the update
     * @param ipAddress IP address
     */
    public static void logStandingUpdated(long performedBy, long targetUserId, int standingId,
                                         String standingName, String reason, String ipAddress) {
        String details = String.format("Updated standing to '%s' (ID %d) for user ID %d. Reason: %s",
                                      standingName, standingId, targetUserId, reason);
        log(performedBy, ACTION_STANDING_UPDATED, details, ipAddress);
    }
}
