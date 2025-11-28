package com.ac.cst8319.lms.dao.impl;

import com.ac.cst8319.lms.dao.BookDAO;
import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.util.DatabaseConnection;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of BookDAO.
 */
public class BookDAOImpl implements BookDAO {

    @Override
    public Optional<Book> findById(long bookId) {
        String sql = "SELECT * FROM Book WHERE BookID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToBook(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding book by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> findByISBN(String isbn) {
        String sql = "SELECT * FROM Book WHERE ISBN = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToBook(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding book by ISBN", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM Book ORDER BY Title";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all books", e);
        }
        return books;
    }

    @Override
    public List<Book> search(String searchTerm) {
        String sql = "SELECT DISTINCT b.* FROM Book b " +
                     "LEFT JOIN Author a ON b.AuthorID = a.AuthorID " +
                     "WHERE b.Title LIKE ? OR b.ISBN LIKE ? OR " +
                     "CONCAT(a.FirstName, ' ', a.LastName) LIKE ? " +
                     "ORDER BY b.Title";
        List<Book> books = new ArrayList<>();
        String searchPattern = "%" + searchTerm + "%";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(mapResultSetToBook(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching books", e);
        }
        return books;
    }

    @Override
    public List<Book> findByAuthor(long authorId) {
        String sql = "SELECT * FROM Book WHERE AuthorID = ? ORDER BY Title";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, authorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(mapResultSetToBook(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding books by author", e);
        }
        return books;
    }

    @Override
    public List<Book> findByGenre(int genreId) {
        String sql = "SELECT * FROM Book WHERE GenreID = ? ORDER BY Title";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, genreId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(mapResultSetToBook(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding books by genre", e);
        }
        return books;
    }

    @Override
    public long insert(Book book) {
        String sql = "INSERT INTO Book (ISBN, Title, AuthorID, GenreID, Publisher, " +
                     "PublicationYear, Description, CreatedAt, UpdatedAt, CreatedBy, UpdatedBy) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getTitle());
            stmt.setLong(3, book.getAuthorId());
            stmt.setInt(4, book.getGenreId());
            stmt.setString(5, book.getPublisher());
            stmt.setString(6, book.getPublicationYear());
            stmt.setString(7, book.getDescription());
            stmt.setObject(8, Instant.now());
            stmt.setObject(9, Instant.now());

            if (book.getCreatedBy() != null) {
                stmt.setLong(10, book.getCreatedBy());
            } else {
                stmt.setNull(10, Types.BIGINT);
            }

            if (book.getUpdatedBy() != null) {
                stmt.setLong(11, book.getUpdatedBy());
            } else {
                stmt.setNull(11, Types.BIGINT);
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting book", e);
        }
    }

    @Override
    public boolean update(Book book) {
        String sql = "UPDATE Book SET ISBN = ?, Title = ?, AuthorID = ?, GenreID = ?, " +
                     "Publisher = ?, PublicationYear = ?, Description = ?, " +
                     "UpdatedAt = ?, UpdatedBy = ? WHERE BookID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getTitle());
            stmt.setLong(3, book.getAuthorId());
            stmt.setInt(4, book.getGenreId());
            stmt.setString(5, book.getPublisher());
            stmt.setString(6, book.getPublicationYear());
            stmt.setString(7, book.getDescription());
            stmt.setObject(8, Instant.now());

            if (book.getUpdatedBy() != null) {
                stmt.setLong(9, book.getUpdatedBy());
            } else {
                stmt.setNull(9, Types.BIGINT);
            }

            stmt.setLong(10, book.getBookId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating book", e);
        }
    }

    @Override
    public boolean delete(long bookId) {
        String sql = "DELETE FROM Book WHERE BookID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, bookId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting book", e);
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Book";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting books", e);
        }
        return 0;
    }

    /**
     * Helper method to map ResultSet to Book object.
     */
    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getLong("BookID"));
        book.setIsbn(rs.getString("ISBN"));
        book.setTitle(rs.getString("Title"));
        book.setAuthorId(rs.getLong("AuthorID"));
        book.setGenreId(rs.getInt("GenreID"));
        book.setPublisher(rs.getString("Publisher"));
        book.setPublicationYear(rs.getString("PublicationYear"));
        book.setDescription(rs.getString("Description"));

        Timestamp createdAt = rs.getTimestamp("CreatedAt");
        if (createdAt != null) {
            book.setCreatedAt(createdAt.toInstant());
        }

        Timestamp updatedAt = rs.getTimestamp("UpdatedAt");
        if (updatedAt != null) {
            book.setUpdatedAt(updatedAt.toInstant());
        }

        long createdBy = rs.getLong("CreatedBy");
        if (!rs.wasNull()) {
            book.setCreatedBy(createdBy);
        }

        long updatedBy = rs.getLong("UpdatedBy");
        if (!rs.wasNull()) {
            book.setUpdatedBy(updatedBy);
        }

        return book;
    }
}
