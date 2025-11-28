package com.ac.cst8319.lms.util;

import com.ac.cst8319.lms.model.UserAccount;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for HTTP session management.
 */
public class SessionUtil {

    // Session attribute names
    private static final String USER_SESSION_KEY = "currentUser";
    private static final String USER_ROLE_KEY = "userRole";
    private static final String USER_ID_KEY = "userId";

    /**
     * Get the currently logged-in user from session.
     * @param session the HTTP session
     * @return UserAccount object if logged in, null otherwise
     */
    public static UserAccount getCurrentUser(HttpSession session) {
        if (session == null) {
            return null;
        }
        return (UserAccount) session.getAttribute(USER_SESSION_KEY);
    }

    /**
     * Store the logged-in user in session.
     * Also stores user ID and role for quick access.
     *
     * @param session the HTTP session
     * @param user the user to store in session
     */
    public static void setCurrentUser(HttpSession session, UserAccount user) {
        if (session == null || user == null) {
            return;
        }
        session.setAttribute(USER_SESSION_KEY, user);
        session.setAttribute(USER_ID_KEY, user.getUserId());
        // Store role ID for quick access
        session.setAttribute(USER_ROLE_KEY, user.getRoleId());
    }

    /**
     * Get the current user's ID from session.
     * @param session the HTTP session
     * @return user ID, or -1 if not logged in
     */
    public static long getCurrentUserId(HttpSession session) {
        if (session == null) {
            return -1;
        }
        Long userId = (Long) session.getAttribute(USER_ID_KEY);
        return (userId != null) ? userId : -1;
    }

    /**
     * Get the current user's role ID from session.
     * @param session the HTTP session
     * @return role ID, or -1 if not logged in
     */
    public static int getCurrentUserRoleId(HttpSession session) {
        if (session == null) {
            return -1;
        }
        Integer roleId = (Integer) session.getAttribute(USER_ROLE_KEY);
        return (roleId != null) ? roleId : -1;
    }

    /**
     * Check if a user is currently authenticated (logged in).
     * @param session the HTTP session
     * @return true if authenticated, false otherwise
     */
    public static boolean isAuthenticated(HttpSession session) {
        return getCurrentUser(session) != null;
    }

    /**
     * Invalidate the session and clear all user data.
     * @param session the HTTP session
     */
    public static void invalidateSession(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * Clear user data from session without invalidating the session.
     * @param session the HTTP session
     */
    public static void clearUserData(HttpSession session) {
        if (session != null) {
            session.removeAttribute(USER_SESSION_KEY);
            session.removeAttribute(USER_ID_KEY);
            session.removeAttribute(USER_ROLE_KEY);
        }
    }

    /**
     * Set a success message in session.
     * @param session the HTTP session
     * @param message the success message
     */
    public static void setSuccessMessage(HttpSession session, String message) {
        if (session != null) {
            session.setAttribute("successMessage", message);
        }
    }

    /**
     * Set an error message in session.
     * @param session the HTTP session
     * @param message the error message
     */
    public static void setErrorMessage(HttpSession session, String message) {
        if (session != null) {
            session.setAttribute("errorMessage", message);
        }
    }

    /**
     * Get and remove success message from session (consume it).
     * @param session the HTTP session
     * @return success message, or null if none exists
     */
    public static String getAndClearSuccessMessage(HttpSession session) {
        if (session == null) {
            return null;
        }
        String message = (String) session.getAttribute("successMessage");
        session.removeAttribute("successMessage");
        return message;
    }

    /**
     * Get and remove error message from session (consume it).
     * @param session the HTTP session
     * @return error message, or null if none exists
     */
    public static String getAndClearErrorMessage(HttpSession session) {
        if (session == null) {
            return null;
        }
        String message = (String) session.getAttribute("errorMessage");
        session.removeAttribute("errorMessage");
        return message;
    }
}
