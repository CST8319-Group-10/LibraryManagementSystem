package com.ac.cst8319.lms.dao.impl;

import com.ac.cst8319.lms.dao.GenreDAO;
import com.ac.cst8319.lms.model.Genre;
import com.ac.cst8319.lms.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of GenreDAO.
 */
public class GenreDAOImpl implements GenreDAO {

    @Override
    public Optional<Genre> findById(int genreId) {
        String sql = "SELECT * FROM Genre WHERE GenreID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, genreId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToGenre(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding genre by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Genre> findAll() {
        String sql = "SELECT * FROM Genre ORDER BY Name";
        List<Genre> genres = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                genres.add(mapResultSetToGenre(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all genres", e);
        }
        return genres;
    }

    @Override
    public int insert(Genre genre) {
        String sql = "INSERT INTO Genre (Name, Description) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, genre.getName());
            stmt.setString(2, genre.getDescription());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating genre failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating genre failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting genre", e);
        }
    }

    @Override
    public boolean update(Genre genre) {
        String sql = "UPDATE Genre SET Name = ?, Description = ? WHERE GenreID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, genre.getName());
            stmt.setString(2, genre.getDescription());
            stmt.setInt(3, genre.getGenreId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating genre", e);
        }
    }

    @Override
    public boolean delete(int genreId) {
        String sql = "DELETE FROM Genre WHERE GenreID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, genreId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting genre", e);
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Genre";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting genres", e);
        }
        return 0;
    }

    /**
     * Helper method to map ResultSet to Genre object.
     */
    private Genre mapResultSetToGenre(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setGenreId(rs.getInt("GenreID"));
        genre.setName(rs.getString("Name"));
        genre.setDescription(rs.getString("Description"));
        return genre;
    }
}
