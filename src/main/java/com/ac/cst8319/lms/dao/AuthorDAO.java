package com.ac.cst8319.lms.dao;

import com.ac.cst8319.lms.model.Author;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Author entity.
 */
public interface AuthorDAO {

    /**
     * Find an author by their ID.
     * @param authorId the author ID
     * @return Optional containing the author if found, empty otherwise
     */
    Optional<Author> findById(long authorId);

    /**
     * Find all authors in the system.
     * @return List of all authors
     */
    List<Author> findAll();

    /**
     * Search authors by name.
     * @param searchTerm the search term
     * @return List of matching authors
     */
    List<Author> searchByName(String searchTerm);

    /**
     * Insert a new author into the database.
     * @param author the author to insert
     * @return the generated author ID
     */
    long insert(Author author);

    /**
     * Update an existing author's information.
     * @param author the author with updated information
     * @return true if update was successful, false otherwise
     */
    boolean update(Author author);

    /**
     * Delete an author from the database.
     * @param authorId the author ID
     * @return true if deletion was successful, false otherwise
     */
    boolean delete(long authorId);

    /**
     * Count total number of authors.
     * @return total author count
     */
    long count();
}
