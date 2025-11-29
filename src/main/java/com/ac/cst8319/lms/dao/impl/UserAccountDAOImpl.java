package com.ac.cst8319.lms.dao.impl;

import com.ac.cst8319.lms.dao.UserAccountDAO;
import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.util.DatabaseConnection;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of UserAccountDAO.
 */
public class UserAccountDAOImpl implements UserAccountDAO {

    @Override
    public Optional<UserAccount> findById(long userId) {
        String sql = "SELECT * FROM UserAccount WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        String sql = "SELECT * FROM UserAccount WHERE Email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by email", e);
        }
        return Optional.empty();
    }

    @Override
    public List<UserAccount> findAll() {
        String sql = "SELECT * FROM UserAccount ORDER BY UserID";
        List<UserAccount> users = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all users", e);
        }
        return users;
    }

    @Override
    public List<UserAccount> findByRole(int roleId) {
        String sql = "SELECT * FROM UserAccount WHERE RoleID = ? ORDER BY UserID";
        List<UserAccount> users = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roleId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding users by role", e);
        }
        return users;
    }

    @Override
    public List<UserAccount> findByStanding(int standingId) {
        String sql = "SELECT * FROM UserAccount WHERE AccountStanding = ? ORDER BY UserID";
        List<UserAccount> users = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, standingId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding users by standing", e);
        }
        return users;
    }

    @Override
    public long insert(UserAccount user) {
        String sql = "INSERT INTO UserAccount (Email, PasswordHash, FirstName, LastName, Phone, Address, " +
                     "RoleID, AccountStanding, CreatedAt, UpdatedAt) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getAddress());
            stmt.setInt(7, user.getRoleId());
            stmt.setInt(8, user.getAccountStanding());
            stmt.setObject(9, Instant.now());
            stmt.setObject(10, Instant.now());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting user", e);
        }
    }

    @Override
    public boolean update(UserAccount user) {
        String sql = "UPDATE UserAccount SET Email = ?, FirstName = ?, LastName = ?, Phone = ?, " +
                     "Address = ?, RoleID = ?, AccountStanding = ?, UpdatedAt = ? WHERE UserID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setInt(6, user.getRoleId());
            stmt.setInt(7, user.getAccountStanding());
            stmt.setObject(8, Instant.now());
            stmt.setLong(9, user.getUserId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    @Override
    public boolean updatePassword(long userId, String passwordHash) {
        String sql = "UPDATE UserAccount SET PasswordHash = ?, UpdatedAt = ? WHERE UserID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, passwordHash);
            stmt.setObject(2, Instant.now());
            stmt.setLong(3, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating password", e);
        }
    }

    @Override
    public boolean updateRole(long userId, int roleId) {
        String sql = "UPDATE UserAccount SET RoleID = ?, UpdatedAt = ? WHERE UserID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roleId);
            stmt.setObject(2, Instant.now());
            stmt.setLong(3, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user role", e);
        }
    }

    @Override
    public boolean updateStanding(long userId, int standingId) {
        String sql = "UPDATE UserAccount SET AccountStanding = ?, UpdatedAt = ? WHERE UserID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, standingId);
            stmt.setObject(2, Instant.now());
            stmt.setLong(3, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating account standing", e);
        }
    }

    @Override
    public boolean updateLastLogin(long userId) {
        String sql = "UPDATE UserAccount SET LastLoginAt = ? WHERE UserID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, Instant.now());
            stmt.setLong(2, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating last login", e);
        }
    }

    @Override
    public boolean delete(long userId) {
        String sql = "DELETE FROM UserAccount WHERE UserID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }

    @Override
    public long countByRole(int roleId) {
        String sql = "SELECT COUNT(*) FROM UserAccount WHERE RoleID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting users by role", e);
        }
        return 0;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM UserAccount";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting users", e);
        }
        return 0;
    }

    /**
     * Helper method to map ResultSet to UserAccount object.
     */
    private UserAccount mapResultSetToUser(ResultSet rs) throws SQLException {
        UserAccount user = new UserAccount();
        user.setUserId(rs.getLong("UserID"));
        user.setEmail(rs.getString("Email"));
        user.setPasswordHash(rs.getString("PasswordHash"));
        user.setFirstName(rs.getString("FirstName"));
        user.setLastName(rs.getString("LastName"));
        user.setPhone(rs.getString("Phone"));
        user.setAddress(rs.getString("Address"));
        user.setRoleId(rs.getInt("RoleID"));
        user.setAccountStanding(rs.getInt("AccountStanding"));

        // Handle timestamps (may be null)
        Timestamp createdAt = rs.getTimestamp("CreatedAt");
        if (createdAt != null) {
            user.setCreatedAt(createdAt.toInstant());
        }

        Timestamp updatedAt = rs.getTimestamp("UpdatedAt");
        if (updatedAt != null) {
            user.setUpdatedAt(updatedAt.toInstant());
        }

        Timestamp lastLoginAt = rs.getTimestamp("LastLoginAt");
        if (lastLoginAt != null) {
            user.setLastLoginAt(lastLoginAt.toInstant());
        }

        Timestamp lastReminderSentAt = rs.getTimestamp("LastReminderSentAt");
        if (lastReminderSentAt != null) {
            user.setLastReminderSentAt(lastReminderSentAt.toInstant());
        }

        return user;
    }
}
