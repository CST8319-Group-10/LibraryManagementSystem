package com.ac.cst8319.lms.dao;

import com.ac.cst8319.lms.model.UserAccount;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for UserAccount entity.
 */
public interface UserAccountDAO {

    /**
     * Find a user by their ID.
     * @param userId the user ID
     * @return Optional containing the user if found, empty otherwise
     */
    Optional<UserAccount> findById(long userId);

    /**
     * Find a user by their email address.
     * @param email the email address
     * @return Optional containing the user if found, empty otherwise
     */
    Optional<UserAccount> findByEmail(String email);

    /**
     * Find all users in the system.
     * @return List of all users
     */
    List<UserAccount> findAll();

    /**
     * Find all users with a specific role.
     * @param roleId the role ID
     * @return List of users with the specified role
     */
    List<UserAccount> findByRole(int roleId);

    /**
     * Find all users with a specific account standing.
     * @param standingId the standing ID
     * @return List of users with the specified standing
     */
    List<UserAccount> findByStanding(int standingId);

    /**
     * Insert a new user into the database.
     * @param user the user to insert
     * @return the generated user ID
     */
    long insert(UserAccount user);

    /**
     * Update an existing user's information.
     * @param user the user with updated information
     * @return true if update was successful, false otherwise
     */
    boolean update(UserAccount user);

    /**
     * Update a user's password hash.
     * @param userId the user ID
     * @param passwordHash the new password hash
     * @return true if update was successful, false otherwise
     */
    boolean updatePassword(long userId, String passwordHash);

    /**
     * Update a user's role.
     * @param userId the user ID
     * @param roleId the new role ID
     * @return true if update was successful, false otherwise
     */
    boolean updateRole(long userId, int roleId);

    /**
     * Update a user's account standing.
     * @param userId the user ID
     * @param standingId the new standing ID
     * @return true if update was successful, false otherwise
     */
    boolean updateStanding(long userId, int standingId);

    /**
     * Update a user's last login timestamp.
     * @param userId the user ID
     * @return true if update was successful, false otherwise
     */
    boolean updateLastLogin(long userId);

    /**
     * Delete a user from the database.
     * @param userId the user ID
     * @return true if deletion was successful, false otherwise
     */
    boolean delete(long userId);

    /**
     * Count the number of users with a specific role.
     * @param roleId the role ID
     * @return count of users with the role
     */
    long countByRole(int roleId);

    /**
     * Count total number of users.
     * @return total user count
     */
    long count();
}
