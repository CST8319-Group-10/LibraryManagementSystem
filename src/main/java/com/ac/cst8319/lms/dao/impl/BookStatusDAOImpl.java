package com.ac.cst8319.lms.dao.impl;

import com.ac.cst8319.lms.dao.BookStatusDAO;
import com.ac.cst8319.lms.model.BookStatus;
import com.ac.cst8319.lms.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of BookStatusDAO.
 */
public class BookStatusDAOImpl implements BookStatusDAO {

    @Override
    public Optional<BookStatus> findById(int statusId) {
        String sql = "SELECT * FROM BookStatus WHERE StatusID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, statusId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToBookStatus(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding book status by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<BookStatus> findAll() {
        String sql = "SELECT * FROM BookStatus ORDER BY Name";
        List<BookStatus> statuses = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                statuses.add(mapResultSetToBookStatus(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all book statuses", e);
        }
        return statuses;
    }

    @Override
    public int insert(BookStatus status) {
        String sql = "INSERT INTO BookStatus (Name, Description) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, status.getName());
            stmt.setString(2, status.getDescription());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating book status failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating book status failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting book status", e);
        }
    }

    @Override
    public boolean update(BookStatus status) {
        String sql = "UPDATE BookStatus SET Name = ?, Description = ? WHERE StatusID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.getName());
            stmt.setString(2, status.getDescription());
            stmt.setInt(3, status.getStatusId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating book status", e);
        }
    }

    @Override
    public boolean delete(int statusId) {
        String sql = "DELETE FROM BookStatus WHERE StatusID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, statusId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting book status", e);
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM BookStatus";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting book statuses", e);
        }
        return 0;
    }

    /**
     * Helper method to map ResultSet to BookStatus object.
     */
    private BookStatus mapResultSetToBookStatus(ResultSet rs) throws SQLException {
        BookStatus status = new BookStatus();
        status.setStatusId(rs.getInt("StatusID"));
        status.setName(rs.getString("Name"));
        status.setDescription(rs.getString("Description"));
        return status;
    }
}
