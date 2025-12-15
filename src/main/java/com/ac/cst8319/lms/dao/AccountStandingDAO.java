package com.ac.cst8319.lms.dao;

import com.ac.cst8319.lms.model.AccountStanding;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for AccountStanding entity.
 */
public interface AccountStandingDAO {

    /**
     * Find an account standing by its ID.
     * @param standingId the standing ID
     * @throws RuntimeException If the standing could not be found
     * @return Optional containing the standing if found, empty otherwise
     */
    Optional<AccountStanding> findById(int standingId);

    /**
     * Find an account standing by its name.
     * @param name the standing name
     * @throws RuntimeException If the standing could not be found
     * @return Optional containing the standing if found, empty otherwise
     */
    Optional<AccountStanding> findByName(String name);

    /**
     * Retrieve all AccountStanding objects.
     * @throws RuntimeException If an error occurred retrieving all account standing objects.
     * @return List of all standing objects.
     */
    List<AccountStanding> findAll();
}
