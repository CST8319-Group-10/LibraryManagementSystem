package com.ac.cst8319.lms.repository;

import com.ac.cst8319.lms.model.AccountStanding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountStandingRepository extends JpaRepository<AccountStanding, Integer> {

    // Find standing by name (e.g., "Good Standing", "Banned")
    Optional<AccountStanding> findByName(String name);
}