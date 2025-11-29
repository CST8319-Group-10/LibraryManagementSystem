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
     * @return Optional containing the standing if found, empty otherwise
     */
    Optional<AccountStanding> findById(int standingId);

    /**
     * Find an account standing by its name.
     * @param name the standing name
     * @return Optional containing the standing if found, empty otherwise
     */
    Optional<AccountStanding> findByName(String name);

    /**
     * Find all account standings in the system.
     * @return List of all standings
     */
    List<AccountStanding> findAll();
}
