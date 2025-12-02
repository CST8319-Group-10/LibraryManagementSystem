package com.ac.cst8319.lms.dao;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.math.BigDecimal;

import lombok.*;

import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.Checkout;

/**
 * Data Access Object interface for Checkout entity.
 */
public interface CheckoutDAO {

    /**
     * Find a checkout by its ID.
     * @param checkoutId the checkout ID
     * @return Optional containing the checkout if found, empty otherwise
     */
    Optional<Checkout> findById(long checkoutId);

    /**
     * Find all checkouts in the system.
     * @return List of all checkouts
     */
    List<Checkout> findAll();

    /**
     * Find active checkouts (not yet returned).
     * @return List of active checkouts
     */
    List<Checkout> findActive();

    /**
     * Find checkouts for a specific member.
     * @param userId the member's user ID
     * @return List of checkouts for the member
     */
    List<Checkout> findByMember(long userId);

    /**
     * Find active checkouts for a specific member.
     * @param userId the member's user ID
     * @return List of active checkouts for the member
     */
    List<Checkout> findActiveByMember(long userId);

    /**
     * Find overdue checkouts (due date passed and not returned).
     * @return List of overdue checkouts
     */
    List<Checkout> findOverdue();

    /**
     * Find overdue checkouts for a specific member.
     * @param userId the member's user ID
     * @return List of overdue checkouts for the member
     */
    List<Checkout> findOverdueByMember(long userId);

    /**
     * Find checkouts by book copy.
     * @param bookCopyId the book copy ID
     * @return List of checkouts for the book copy
     */
    List<Checkout> findByBookCopy(long bookCopyId);

    /**
     * Insert a new checkout into the database.
     * @param checkout the checkout to insert
     * @return the generated checkout ID
     */
    long insert(Checkout checkout);

    /**
     * Update an existing checkout's information.
     * @param checkout the checkout with updated information
     * @return true if update was successful, false otherwise
     */
    boolean update(Checkout checkout);

    /**
     * Count total number of checkouts.
     * @return total checkout count
     */
    long count();

    /**
     * Count active checkouts.
     * @return active checkout count
     */
    long countActive();

    /**
     * Count overdue checkouts.
     * @return overdue checkout count
     */
    long countOverdue();

    /**
     * Count overdue checkouts for a specific member.
     * @param userId the member's user ID
     * @return overdue checkout count for the member
     */
    long countOverdueByMember(long userId);

    /**
     * Finds checkouts with unpaid fees.
     * @return checkouts with unpaid fees
     */
    List<Checkout> findFeesOwed();

    /**
     * Finds checkouts with unpaid fees for a specific member.
     * @param userId the member's user ID
     * @return checkouts with unpaid fees for the member
     */
    List<Checkout> findFeesOwedByMember(long userId);


    /**
     * Calculates sum of fees owed by a specific member.
     * @param userId the member's user ID
     * @return sum of fees owed by the member.
     */
    BigDecimal calcTotalFeesOwedByMember(long userId);

    /**
     * Convience tuple for returning join'ed results.
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BorrowedBook {
        public Book book;
        public Checkout checkout;
    }

    /**
     * Finds currently borrowed books for a user.
     * @param userId the member's user ID
     * @return List of checkout/book pairs currently borrowed by the member.
     */
    List<BorrowedBook> findBorrowedBooksByMember(long userId);


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BookHistory {
        public Book book;
        public LocalDate latestCheckout;
    }
    /**
     * Finds a user's has borrowing history.
     * @param userId the member's user ID
     * @return List of books along with latest checkout.
     */
    List<BookHistory> findBookHistoryByMember(long userId);
}
