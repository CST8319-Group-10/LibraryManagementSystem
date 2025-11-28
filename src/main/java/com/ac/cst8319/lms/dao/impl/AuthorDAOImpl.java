package com.ac.cst8319.lms.dao.impl;

import com.ac.cst8319.lms.dao.AuthorDAO;
import com.ac.cst8319.lms.model.Author;
import com.ac.cst8319.lms.util.DatabaseConnection;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of AuthorDAO.
 */
public class AuthorDAOImpl implements AuthorDAO {

    @Override
    public Optional<Author> findById(long authorId) {
        String sql = "SELECT * FROM Author WHERE AuthorID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, authorId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToAuthor(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding author by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Author> findAll() {
        String sql = "SELECT * FROM Author ORDER BY LastName, FirstName";
        List<Author> authors = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                authors.add(mapResultSetToAuthor(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all authors", e);
        }
        return authors;
    }

    @Override
    public List<Author> searchByName(String searchTerm) {
        String sql = "SELECT * FROM Author WHERE " +
                     "CONCAT(FirstName, ' ', LastName) LIKE ? OR " +
                     "CONCAT(LastName, ' ', FirstName) LIKE ? " +
                     "ORDER BY LastName, FirstName";
        List<Author> authors = new ArrayList<>();
        String searchPattern = "%" + searchTerm + "%";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    authors.add(mapResultSetToAuthor(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching authors", e);
        }
        return authors;
    }

    @Override
    public long insert(Author author) {
        String sql = "INSERT INTO Author (FirstName, LastName, Biography, CreatedAt, UpdatedAt) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());
            stmt.setString(3, author.getBiography());
            stmt.setObject(4, Instant.now());
            stmt.setObject(5, Instant.now());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating author failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating author failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting author", e);
        }
    }

    @Override
    public boolean update(Author author) {
        String sql = "UPDATE Author SET FirstName = ?, LastName = ?, Biography = ?, " +
                     "UpdatedAt = ? WHERE AuthorID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());
            stmt.setString(3, author.getBiography());
            stmt.setObject(4, Instant.now());
            stmt.setLong(5, author.getAuthorId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating author", e);
        }
    }

    @Override
    public boolean delete(long authorId) {
        String sql = "DELETE FROM Author WHERE AuthorID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, authorId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting author", e);
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Author";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting authors", e);
        }
        return 0;
    }

    /**
     * Helper method to map ResultSet to Author object.
     */
    private Author mapResultSetToAuthor(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setAuthorId(rs.getLong("AuthorID"));
        author.setFirstName(rs.getString("FirstName"));
        author.setLastName(rs.getString("LastName"));
        author.setBiography(rs.getString("Biography"));

        Timestamp createdAt = rs.getTimestamp("CreatedAt");
        if (createdAt != null) {
            author.setCreatedAt(createdAt.toInstant());
        }

        Timestamp updatedAt = rs.getTimestamp("UpdatedAt");
        if (updatedAt != null) {
            author.setUpdatedAt(updatedAt.toInstant());
        }

        return author;
    }
}
