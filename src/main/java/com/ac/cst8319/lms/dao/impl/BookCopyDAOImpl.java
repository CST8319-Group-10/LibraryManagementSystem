package com.ac.cst8319.lms.dao.impl;

import com.ac.cst8319.lms.dao.BookCopyDAO;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of BookCopyDAO.
 */
public class BookCopyDAOImpl implements BookCopyDAO {

    @Override
    public Optional<BookCopy> findById(long bookCopyId) {
        String sql = "SELECT * FROM BookCopy WHERE BookCopyID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, bookCopyId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToBookCopy(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding book copy by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<BookCopy> findByBookId(long bookId) {
        String sql = "SELECT * FROM BookCopy WHERE BookID = ? ORDER BY BookCopyID";
        List<BookCopy> bookCopies = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bookCopies.add(mapResultSetToBookCopy(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding book copies by book ID", e);
        }
        return bookCopies;
    }

    @Override
    public List<BookCopy> findAll() {
        String sql = "SELECT * FROM BookCopy ORDER BY BookCopyID";
        List<BookCopy> bookCopies = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bookCopies.add(mapResultSetToBookCopy(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all book copies", e);
        }
        return bookCopies;
    }

    @Override
    public List<BookCopy> findAvailable() {
        String sql = "SELECT * FROM BookCopy WHERE StatusID = 1 ORDER BY BookCopyID";
        List<BookCopy> bookCopies = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bookCopies.add(mapResultSetToBookCopy(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding available book copies", e);
        }
        return bookCopies;
    }

    @Override
    public List<BookCopy> findAvailableByBookId(long bookId) {
        String sql = "SELECT * FROM BookCopy WHERE BookID = ? AND StatusID = 1 ORDER BY BookCopyID";
        List<BookCopy> bookCopies = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bookCopies.add(mapResultSetToBookCopy(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding available book copies by book ID", e);
        }
        return bookCopies;
    }

    @Override
    public List<BookCopy> findByStatus(int statusId) {
        String sql = "SELECT * FROM BookCopy WHERE StatusID = ? ORDER BY BookCopyID";
        List<BookCopy> bookCopies = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, statusId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bookCopies.add(mapResultSetToBookCopy(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding book copies by status", e);
        }
        return bookCopies;
    }

    @Override
    public long insert(BookCopy bookCopy) {
        String sql = "INSERT INTO BookCopy (BookID, StatusID, Location, AcquisitionDate) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, bookCopy.getBookId());
            stmt.setInt(2, bookCopy.getStatusId());
            stmt.setString(3, bookCopy.getLocation());
            stmt.setDate(4, bookCopy.getAcquisitionDate() != null ?
                         Date.valueOf(bookCopy.getAcquisitionDate()) : null);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating book copy failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating book copy failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting book copy", e);
        }
    }

    @Override
    public boolean update(BookCopy bookCopy) {
        String sql = "UPDATE BookCopy SET BookID = ?, StatusID = ?, Location = ?, " +
                     "AcquisitionDate = ? WHERE BookCopyID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, bookCopy.getBookId());
            stmt.setInt(2, bookCopy.getStatusId());
            stmt.setString(3, bookCopy.getLocation());
            stmt.setDate(4, bookCopy.getAcquisitionDate() != null ?
                         Date.valueOf(bookCopy.getAcquisitionDate()) : null);
            stmt.setLong(5, bookCopy.getBookCopyId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating book copy", e);
        }
    }

    @Override
    public boolean updateStatus(long bookCopyId, int statusId) {
        String sql = "UPDATE BookCopy SET StatusID = ? WHERE BookCopyID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, statusId);
            stmt.setLong(2, bookCopyId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating book copy status", e);
        }
    }

    @Override
    public boolean delete(long bookCopyId) {
        String sql = "DELETE FROM BookCopy WHERE BookCopyID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, bookCopyId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting book copy", e);
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM BookCopy";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting book copies", e);
        }
        return 0;
    }

    @Override
    public long countByBookId(long bookId) {
        String sql = "SELECT COUNT(*) FROM BookCopy WHERE BookID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting book copies by book ID", e);
        }
        return 0;
    }

    /**
     * Helper method to map ResultSet to BookCopy object.
     */
    private BookCopy mapResultSetToBookCopy(ResultSet rs) throws SQLException {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setBookCopyId(rs.getLong("BookCopyID"));
        bookCopy.setBookId(rs.getLong("BookID"));
        bookCopy.setStatusId(rs.getInt("StatusID"));
        bookCopy.setLocation(rs.getString("Location"));

        Date acquisitionDate = rs.getDate("AcquisitionDate");
        if (acquisitionDate != null) {
            bookCopy.setAcquisitionDate(acquisitionDate.toLocalDate());
        }

        return bookCopy;
    }
}
