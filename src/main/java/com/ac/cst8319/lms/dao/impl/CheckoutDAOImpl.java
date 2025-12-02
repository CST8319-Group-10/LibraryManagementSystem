package com.ac.cst8319.lms.dao.impl;

import com.ac.cst8319.lms.dao.CheckoutDAO;
import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.Checkout;
import com.ac.cst8319.lms.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of CheckoutDAO.
 */
public class CheckoutDAOImpl implements CheckoutDAO {

    @Override
    public Optional<Checkout> findById(long checkoutId) {
        String sql = "SELECT * FROM Checkout WHERE CheckoutID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, checkoutId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCheckout(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding checkout by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Checkout> findAll() {
        String sql = "SELECT * FROM Checkout ORDER BY CheckoutDate DESC";
        List<Checkout> checkouts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                checkouts.add(mapResultSetToCheckout(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all checkouts", e);
        }
        return checkouts;
    }

    @Override
    public List<Checkout> findActive() {
        String sql = "SELECT * FROM Checkout WHERE ReturnDate IS NULL ORDER BY DueDate";
        List<Checkout> checkouts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                checkouts.add(mapResultSetToCheckout(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding active checkouts", e);
        }
        return checkouts;
    }

    @Override
    public List<Checkout> findByMember(long userId) {
        String sql = "SELECT * FROM Checkout WHERE LoanedTo = ? ORDER BY CheckoutDate DESC";
        List<Checkout> checkouts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    checkouts.add(mapResultSetToCheckout(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding checkouts by member", e);
        }
        return checkouts;
    }

    @Override
    public List<Checkout> findActiveByMember(long userId) {
        String sql = "SELECT * FROM Checkout WHERE LoanedTo = ? AND ReturnDate IS NULL ORDER BY DueDate";
        List<Checkout> checkouts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    checkouts.add(mapResultSetToCheckout(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding active checkouts by member", e);
        }
        return checkouts;
    }

    @Override
    public List<Checkout> findOverdue() {
        String sql = "SELECT * FROM Checkout WHERE ReturnDate IS NULL AND DueDate < CURDATE() " +
                     "ORDER BY DueDate";
        List<Checkout> checkouts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                checkouts.add(mapResultSetToCheckout(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding overdue checkouts", e);
        }
        return checkouts;
    }

    @Override
    public List<Checkout> findOverdueByMember(long userId) {
        String sql = "SELECT * FROM Checkout WHERE LoanedTo = ? AND ReturnDate IS NULL " +
                     "AND DueDate < CURDATE() ORDER BY DueDate";
        List<Checkout> checkouts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    checkouts.add(mapResultSetToCheckout(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding overdue checkouts by member", e);
        }
        return checkouts;
    }

    @Override
    public List<Checkout> findByBookCopy(long bookCopyId) {
        String sql = "SELECT * FROM Checkout WHERE BookCopyID = ? ORDER BY CheckoutDate DESC";
        List<Checkout> checkouts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, bookCopyId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    checkouts.add(mapResultSetToCheckout(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding checkouts by book copy", e);
        }
        return checkouts;
    }

    @Override
    public long insert(Checkout checkout) {
        String sql = "INSERT INTO Checkout (LoanedTo, BookCopyID, CheckoutDate, DueDate, " +
                     "ReturnDate, CheckedOutBy, ReturnedBy, LateFeeAssessed, LateFeePaid, CreatedAt) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, checkout.getLoanedTo());
            stmt.setLong(2, checkout.getBookCopyId());
            stmt.setDate(3, checkout.getCheckoutDate() != null ?
                         Date.valueOf(checkout.getCheckoutDate()) : null);
            stmt.setDate(4, checkout.getDueDate() != null ?
                         Date.valueOf(checkout.getDueDate()) : null);

            if (checkout.getReturnDate() != null) {
                stmt.setDate(5, Date.valueOf(checkout.getReturnDate()));
            } else {
                stmt.setNull(5, Types.DATE);
            }

            stmt.setLong(6, checkout.getCheckedOutBy());

            if (checkout.getReturnedBy() > 0) {
                stmt.setLong(7, checkout.getReturnedBy());
            } else {
                stmt.setNull(7, Types.BIGINT);
            }

            if (checkout.getLateFeeAssessed() != null) {
                stmt.setBigDecimal(8, checkout.getLateFeeAssessed());
            } else {
                stmt.setNull(8, Types.DECIMAL);
            }

            stmt.setBoolean(9, checkout.isLateFeePaid());
            stmt.setObject(10, Instant.now());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating checkout failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating checkout failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting checkout", e);
        }
    }

    @Override
    public boolean update(Checkout checkout) {
        String sql = "UPDATE Checkout SET LoanedTo = ?, BookCopyID = ?, CheckoutDate = ?, " +
                     "DueDate = ?, ReturnDate = ?, CheckedOutBy = ?, ReturnedBy = ?, " +
                     "LateFeeAssessed = ?, LateFeePaid = ? WHERE CheckoutID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, checkout.getLoanedTo());
            stmt.setLong(2, checkout.getBookCopyId());
            stmt.setDate(3, checkout.getCheckoutDate() != null ?
                         Date.valueOf(checkout.getCheckoutDate()) : null);
            stmt.setDate(4, checkout.getDueDate() != null ?
                         Date.valueOf(checkout.getDueDate()) : null);

            if (checkout.getReturnDate() != null) {
                stmt.setDate(5, Date.valueOf(checkout.getReturnDate()));
            } else {
                stmt.setNull(5, Types.DATE);
            }

            stmt.setLong(6, checkout.getCheckedOutBy());

            if (checkout.getReturnedBy() > 0) {
                stmt.setLong(7, checkout.getReturnedBy());
            } else {
                stmt.setNull(7, Types.BIGINT);
            }

            if (checkout.getLateFeeAssessed() != null) {
                stmt.setBigDecimal(8, checkout.getLateFeeAssessed());
            } else {
                stmt.setNull(8, Types.DECIMAL);
            }

            stmt.setBoolean(9, checkout.isLateFeePaid());
            stmt.setLong(10, checkout.getCheckoutId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating checkout", e);
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Checkout";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting checkouts", e);
        }
        return 0;
    }

    @Override
    public long countActive() {
        String sql = "SELECT COUNT(*) FROM Checkout WHERE ReturnDate IS NULL";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting active checkouts", e);
        }
        return 0;
    }

    @Override
    public long countOverdue() {
        String sql = "SELECT COUNT(*) FROM Checkout WHERE ReturnDate IS NULL AND DueDate < CURDATE()";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting overdue checkouts", e);
        }
        return 0;
    }

    @Override
    public long countOverdueByMember(long userId) {
        String sql = "SELECT COUNT(*) FROM Checkout WHERE LoanedTo = ? AND ReturnDate IS NULL " +
                     "AND DueDate < CURDATE()";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting overdue checkouts by member", e);
        }
        return 0;
    }

    @Override
    public List<Checkout> findFeesOwed() {
        String sql = "SELECT * FROM Checkout "
                    + " WHERE LateFeeAssessed IS NOT NULL "
                    + " AND LateFeeAssessed <> 0 AND LateFeePaid <> 1 "
                    + " ORDER BY DueDate DESC";
        List<Checkout> checkouts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    checkouts.add(mapResultSetToCheckout(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding checkouts with fees owed", e);
        }
        return checkouts;
    }

    @Override
    public List<Checkout> findFeesOwedByMember(long userId) {
        String sql = "SELECT * FROM Checkout WHERE LoanedTo = ? "
                    + " AND LateFeeAssessed IS NOT NULL "
                    + " AND LateFeeAssessed <> 0 AND LateFeePaid <> 1 "
                    + " ORDER BY DueDate ASC";
        List<Checkout> checkouts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    checkouts.add(mapResultSetToCheckout(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding fees owed by member", e);
        }
        return checkouts;
    }


    @Override
    public BigDecimal calcTotalFeesOwedByMember(long userId) {
        String sql = "SELECT SUM(LateFeeAssessed) FROM Checkout WHERE LoanedTo = ? "
                    + " AND LateFeeAssessed IS NOT NULL "
                    + " AND LateFeeAssessed <> 0 AND LateFeePaid <> 1 ";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating sum owed by member", e);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public List<CheckoutDAO.BorrowedBook> findBorrowedBooksByMember(long userId) {
        String sql = "SELECT B.*, C.* "
                    + " FROM Checkout C "
                    + " JOIN BookCopy BC ON BC.BookCopyID = C.BookCopyID "
                    + " JOIN Book B ON BC.BookID = B.BookID "
                    + " WHERE C.LoanedTo = ? "
                    + " ORDER BY C.CheckoutDate DESC ";
        List<CheckoutDAO.BorrowedBook> borrowedBooks = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    borrowedBooks.add(mapResultSetToBorrowedBook(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error find books borrowed by member", e);
        }

        return borrowedBooks;
    }

    @Override
    public List<CheckoutDAO.BookHistory> findBookHistoryByMember(long userId) {
        String sql =
            "SELECT * FROM (" +
            "  SELECT B.*, C.CheckoutDate, " +
            "         ROW_NUMBER() OVER (PARTITION BY B.BookID ORDER BY C.CheckoutDate DESC) AS rnk " +
            "  FROM Checkout C " +
            "  JOIN BookCopy BC ON BC.BookCopyID = C.BookCopyID " +
            "  JOIN Book B ON BC.BookID = B.BookID " +
            "  WHERE C.LoanedTo = ? " +
            ") Ranked WHERE rnk = 1 ORDER BY CheckoutDate DESC";
        List<CheckoutDAO.BookHistory> bookHistory = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bookHistory.add(mapResultSetToBookHistory(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding member book history", e);
        }

        return bookHistory;
    }

    /**
     * Helper method to map ResultSet to BookHistory object.
     */
    private CheckoutDAO.BookHistory mapResultSetToBookHistory(ResultSet rs) throws SQLException {
        Book book = mapResultSetToBook(rs, "");
        LocalDate latestCheckout = rs.getDate("CheckoutDate").toLocalDate();

        return new CheckoutDAO.BookHistory(book, latestCheckout);
    }

    /**
     * Helper method to map ResultSet to BorrowedBook object.
     */
    private CheckoutDAO.BorrowedBook mapResultSetToBorrowedBook(ResultSet rs) throws SQLException {
        Book book = mapResultSetToBook(rs, "B.");
        Checkout checkout = mapResultSetToCheckout(rs, "C.");
        CheckoutDAO.BorrowedBook borrowedBook = new CheckoutDAO.BorrowedBook(book, checkout);

        return borrowedBook;
    }

    /**
     * Helper method to map ResultSet to Book object.
     */
    private Book mapResultSetToBook(ResultSet rs, String prefix) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getLong(prefix + "BookID"));
        book.setIsbn(rs.getString(prefix + "ISBN"));
        book.setTitle(rs.getString(prefix + "Title"));
        book.setAuthorId(rs.getLong(prefix + "AuthorID"));
        book.setGenreId(rs.getInt(prefix + "GenreID"));
        book.setPublisher(rs.getString(prefix + "Publisher"));
        book.setPublicationYear(rs.getString(prefix + "PublicationYear"));
        book.setDescription(rs.getString(prefix + "Description"));

        Timestamp createdAt = rs.getTimestamp(prefix + "CreatedAt");
        if (createdAt != null) {
            book.setCreatedAt(createdAt.toInstant());
        }

        Timestamp updatedAt = rs.getTimestamp(prefix + "UpdatedAt");
        if (updatedAt != null) {
            book.setUpdatedAt(updatedAt.toInstant());
        }

        long createdBy = rs.getLong(prefix + "CreatedBy");
        if (!rs.wasNull()) {
            book.setCreatedBy(createdBy);
        }

        long updatedBy = rs.getLong(prefix + "UpdatedBy");
        if (!rs.wasNull()) {
            book.setUpdatedBy(updatedBy);
        }

        return book;
    }

    /**
     * Helper method to map ResultSet to Checkout object.
     */
    private Checkout mapResultSetToCheckout(ResultSet rs) throws SQLException {
        return mapResultSetToCheckout(rs, "");
    }

    /**
     * Helper method to map ResultSet to Checkout object.
     */
    private Checkout mapResultSetToCheckout(ResultSet rs, String prefix) throws SQLException {
        Checkout checkout = new Checkout();
        checkout.setCheckoutId(rs.getLong(prefix + "CheckoutID"));
        checkout.setLoanedTo(rs.getLong(prefix + "LoanedTo"));
        checkout.setBookCopyId(rs.getLong(prefix + "BookCopyID"));

        Date checkoutDate = rs.getDate(prefix + "CheckoutDate");
        if (checkoutDate != null) {
            checkout.setCheckoutDate(checkoutDate.toLocalDate());
        }

        Date dueDate = rs.getDate(prefix + "DueDate");
        if (dueDate != null) {
            checkout.setDueDate(dueDate.toLocalDate());
        }

        Date returnDate = rs.getDate(prefix + "ReturnDate");
        if (returnDate != null) {
            checkout.setReturnDate(returnDate.toLocalDate());
        }

        checkout.setCheckedOutBy(rs.getLong(prefix + "CheckedOutBy"));

        long returnedBy = rs.getLong(prefix + "ReturnedBy");
        if (!rs.wasNull()) {
            checkout.setReturnedBy(returnedBy);
        }

        checkout.setLateFeeAssessed(rs.getBigDecimal(prefix + "LateFeeAssessed"));
        checkout.setLateFeePaid(rs.getBoolean(prefix + "LateFeePaid"));

        Timestamp createdAt = rs.getTimestamp(prefix + "CreatedAt");
        if (createdAt != null) {
            checkout.setCreatedAt(createdAt.toInstant());
        }

        return checkout;
    }
}
