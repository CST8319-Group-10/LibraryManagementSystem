package com.ac.cst8319.lms.dao;

import com.ac.cst8319.lms.model.BookCopy;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for BookCopy entity.
 */
public interface BookCopyDAO {

    /**
     * Find a book copy by its ID.
     * @param bookCopyId the book copy ID
     * @return Optional containing the book copy if found, empty otherwise
     */
    Optional<BookCopy> findById(long bookCopyId);

    /**
     * Find all book copies for a specific book.
     * @param bookId the book ID
     * @return List of book copies for the book
     */
    List<BookCopy> findByBookId(long bookId);

    /**
     * Find all book copies in the system.
     * @return List of all book copies
     */
    List<BookCopy> findAll();

    /**
     * Find available book copies (status = Available).
     * @return List of available book copies
     */
    List<BookCopy> findAvailable();

    /**
     * Find available copies for a specific book.
     * @param bookId the book ID
     * @return List of available copies for the book
     */
    List<BookCopy> findAvailableByBookId(long bookId);

    /**
     * Find book copies by status.
     * @param statusId the status ID
     * @return List of book copies with the specified status
     */
    List<BookCopy> findByStatus(int statusId);

    /**
     * Insert a new book copy into the database.
     * @param bookCopy the book copy to insert
     * @return the generated book copy ID
     */
    long insert(BookCopy bookCopy);

    /**
     * Update an existing book copy's information.
     * @param bookCopy the book copy with updated information
     * @return true if update was successful, false otherwise
     */
    boolean update(BookCopy bookCopy);

    /**
     * Update a book copy's status.
     * @param bookCopyId the book copy ID
     * @param statusId the new status ID
     * @return true if update was successful, false otherwise
     */
    boolean updateStatus(long bookCopyId, int statusId);

    /**
     * Delete a book copy from the database.
     * @param bookCopyId the book copy ID
     * @return true if deletion was successful, false otherwise
     */
    boolean delete(long bookCopyId);

    /**
     * Count total number of book copies.
     * @return total book copy count
     */
    long count();

    /**
     * Count book copies for a specific book.
     * @param bookId the book ID
     * @return count of copies for the book
     */
    long countByBookId(long bookId);
}
