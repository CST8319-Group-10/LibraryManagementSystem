package com.ac.cst8319.lms.util;

import com.ac.cst8319.lms.model.UserAccount;

/**
 * Utility class for role-based access control checks.
 */
public class RoleUtil {

    // Role IDs from database
    public static final int ROLE_GUEST = 1;
    public static final int ROLE_REGISTERED_USER = 2;
    public static final int ROLE_LIBRARIAN = 3;
    public static final int ROLE_ADMINISTRATOR = 4;

    // Role names
    public static final String ROLE_NAME_GUEST = "guest";
    public static final String ROLE_NAME_REGISTERED_USER = "registered_user";
    public static final String ROLE_NAME_LIBRARIAN = "librarian";
    public static final String ROLE_NAME_ADMINISTRATOR = "administrator";

    /**
     * Check if user is an administrator.
     * @param user the user account
     * @return true if administrator, false otherwise
     */
    public static boolean isAdministrator(UserAccount user) {
        return user != null && user.getRoleId() == ROLE_ADMINISTRATOR;
    }

    /**
     * Check if user is a librarian.
     * @param user the user account
     * @return true if librarian, false otherwise
     */
    public static boolean isLibrarian(UserAccount user) {
        return user != null && user.getRoleId() == ROLE_LIBRARIAN;
    }

    /**
     * Check if user is a registered user.
     * @param user the user account
     * @return true if registered user, false otherwise
     */
    public static boolean isRegisteredUser(UserAccount user) {
        return user != null && user.getRoleId() == ROLE_REGISTERED_USER;
    }

    /**
     * Check if user is a librarian or administrator.
     * @param user the user account
     * @return true if librarian or administrator, false otherwise
     */
    public static boolean isLibrarianOrAdmin(UserAccount user) {
        return isLibrarian(user) || isAdministrator(user);
    }

    /**
     * Check if user has a specific role by role ID.
     * @param user the user account
     * @param roleId the role ID to check
     * @return true if user has the role, false otherwise
     */
    public static boolean hasRole(UserAccount user, int roleId) {
        return user != null && user.getRoleId() == roleId;
    }

    /**
     * Get role name by role ID.
     * @param roleId the role ID
     * @return role name, or "unknown" if invalid role ID
     */
    public static String getRoleName(int roleId) {
        switch (roleId) {
            case ROLE_GUEST:
                return ROLE_NAME_GUEST;
            case ROLE_REGISTERED_USER:
                return ROLE_NAME_REGISTERED_USER;
            case ROLE_LIBRARIAN:
                return ROLE_NAME_LIBRARIAN;
            case ROLE_ADMINISTRATOR:
                return ROLE_NAME_ADMINISTRATOR;
            default:
                return "unknown";
        }
    }

    /**
     * Get role ID by role name.
     * @param roleName the role name
     * @return role ID, or -1 if invalid role name
     */
    public static int getRoleId(String roleName) {
        if (roleName == null) {
            return -1;
        }
        switch (roleName.toLowerCase()) {
            case ROLE_NAME_GUEST:
                return ROLE_GUEST;
            case ROLE_NAME_REGISTERED_USER:
                return ROLE_REGISTERED_USER;
            case ROLE_NAME_LIBRARIAN:
                return ROLE_LIBRARIAN;
            case ROLE_NAME_ADMINISTRATOR:
                return ROLE_ADMINISTRATOR;
            default:
                return -1;
        }
    }

    /**
     * Check if user has permission for admin-only operations.
     * @param user the user account
     * @return true if user has admin permission, false otherwise
     */
    public static boolean hasAdminPermission(UserAccount user) {
        return isAdministrator(user);
    }

    /**
     * Check if user has permission for librarian operations.
     * Librarians and administrators both have librarian permissions.
     *
     * @param user the user account
     * @return true if user has librarian permission, false otherwise
     */
    public static boolean hasLibrarianPermission(UserAccount user) {
        return isLibrarianOrAdmin(user);
    }
}
