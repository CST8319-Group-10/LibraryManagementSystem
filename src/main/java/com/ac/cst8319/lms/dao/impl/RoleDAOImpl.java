package com.ac.cst8319.lms.dao.impl;

import com.ac.cst8319.lms.dao.RoleDAO;
import com.ac.cst8319.lms.model.Role;
import com.ac.cst8319.lms.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of RoleDAO.
 */
public class RoleDAOImpl implements RoleDAO {

    @Override
    public Optional<Role> findById(int roleId) {
        String sql = "SELECT * FROM Role WHERE RoleID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToRole(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding role by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Role> findByName(String name) {
        String sql = "SELECT * FROM Role WHERE Name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToRole(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding role by name", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Role> findAll() {
        String sql = "SELECT * FROM Role ORDER BY RoleID";
        List<Role> roles = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                roles.add(mapResultSetToRole(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all roles", e);
        }
        return roles;
    }

    /**
     * Helper method to map ResultSet to Role object.
     */
    private Role mapResultSetToRole(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setRoleId(rs.getInt("RoleID"));
        role.setName(rs.getString("Name"));
        role.setDescription(rs.getString("Description"));
        return role;
    }
}
