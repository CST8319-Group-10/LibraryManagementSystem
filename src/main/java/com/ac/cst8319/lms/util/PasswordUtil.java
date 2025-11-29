package com.ac.cst8319.lms.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Utility class for password hashing and verification using BCrypt.
 */
public class PasswordUtil {

    // BCrypt cost factor (number of hashing rounds: 2^12 = 4096 rounds)
    private static final int BCRYPT_COST = 12;

    /**
     * Hash a plain text password using BCrypt.
     * @param plainPassword the plain text password
     * @return BCrypt hashed password
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return BCrypt.withDefaults().hashToString(BCRYPT_COST, plainPassword.toCharArray());
    }

    /**
     * Verify a plain text password against a BCrypt hash.
     * @param plainPassword the plain text password to verify
     * @param hashedPassword the BCrypt hashed password
     * @return true if password matches, false otherwise
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        BCrypt.Result result = BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword);
        return result.verified;
    }
}
