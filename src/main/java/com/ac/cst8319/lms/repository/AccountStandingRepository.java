package com.ac.cst8319.lms.repository;

import com.ac.cst8319.lms.model.AccountStanding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountStandingRepository extends JpaRepository<AccountStanding, Long> {
    
    Optional<AccountStanding> findByName(String name);
    
    boolean existsByName(String name);
    
    @Query("SELECT a FROM AccountStanding a WHERE a.name = 'ACTIVE'")
    Optional<AccountStanding> findActiveStanding();
    
    @Query("SELECT a FROM AccountStanding a WHERE a.name = 'SUSPENDED'")
    Optional<AccountStanding> findSuspendedStanding();
    
    @Query("SELECT a FROM AccountStanding a WHERE a.name = 'DISABLED'")
    Optional<AccountStanding> findDisabledStanding();
}