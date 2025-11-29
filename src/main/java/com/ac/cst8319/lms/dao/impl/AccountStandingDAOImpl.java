package com.ac.cst8319.lms.dao.impl;

import com.ac.cst8319.lms.dao.AccountStandingDAO;
import com.ac.cst8319.lms.model.AccountStanding;
import com.ac.cst8319.lms.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of AccountStandingDAO.
 */
public class AccountStandingDAOImpl implements AccountStandingDAO {

    @Override
    public Optional<AccountStanding> findById(int standingId) {
        String sql = "SELECT * FROM AccountStanding WHERE StandingID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, standingId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToStanding(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding standing by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<AccountStanding> findByName(String name) {
        String sql = "SELECT * FROM AccountStanding WHERE Name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToStanding(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding standing by name", e);
        }
        return Optional.empty();
    }

    @Override
    public List<AccountStanding> findAll() {
        String sql = "SELECT * FROM AccountStanding ORDER BY StandingID";
        List<AccountStanding> standings = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                standings.add(mapResultSetToStanding(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all standings", e);
        }
        return standings;
    }

    /**
     * Helper method to map ResultSet to AccountStanding object.
     */
    private AccountStanding mapResultSetToStanding(ResultSet rs) throws SQLException {
        AccountStanding standing = new AccountStanding();
        standing.setStandingId(rs.getInt("StandingID"));
        standing.setName(rs.getString("Name"));
        standing.setDescription(rs.getString("Description"));
        return standing;
    }
}
