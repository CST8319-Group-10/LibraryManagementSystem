package com.ac.cst8319.lms.dao;

import com.ac.cst8319.lms.model.BookStatus;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for BookStatus entity.
 */
public interface BookStatusDAO {

    /**
     * Find a book status by its ID.
     * @param statusId the status ID
     * @return Optional containing the status if found, empty otherwise
     */
    Optional<BookStatus> findById(int statusId);

    /**
     * Find all book statuses in the system.
     * @return List of all book statuses
     */
    List<BookStatus> findAll();

    /**
     * Insert a new book status into the database.
     * @param status the status to insert
     * @return the generated status ID
     */
    int insert(BookStatus status);

    /**
     * Update an existing book status's information.
     * @param status the status with updated information
     * @return true if update was successful, false otherwise
     */
    boolean update(BookStatus status);

    /**
     * Delete a book status from the database.
     * @param statusId the status ID
     * @return true if deletion was successful, false otherwise
     */
    boolean delete(int statusId);

    /**
     * Count total number of book statuses.
     * @return total status count
     */
    long count();
}
