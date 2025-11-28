package com.ac.cst8319.lms.dao;

import com.ac.cst8319.lms.model.Genre;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Genre entity.
 */
public interface GenreDAO {

    /**
     * Find a genre by its ID.
     * @param genreId the genre ID
     * @return Optional containing the genre if found, empty otherwise
     */
    Optional<Genre> findById(int genreId);

    /**
     * Find all genres in the system.
     * @return List of all genres
     */
    List<Genre> findAll();

    /**
     * Insert a new genre into the database.
     * @param genre the genre to insert
     * @return the generated genre ID
     */
    int insert(Genre genre);

    /**
     * Update an existing genre's information.
     * @param genre the genre with updated information
     * @return true if update was successful, false otherwise
     */
    boolean update(Genre genre);

    /**
     * Delete a genre from the database.
     * @param genreId the genre ID
     * @return true if deletion was successful, false otherwise
     */
    boolean delete(int genreId);

    /**
     * Count total number of genres.
     * @return total genre count
     */
    long count();
}
