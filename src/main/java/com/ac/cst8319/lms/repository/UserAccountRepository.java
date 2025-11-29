package com.ac.cst8319.lms.repository;

import com.ac.cst8319.lms.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    
    Optional<UserAccount> findByUsername(String username);
    
    Optional<UserAccount> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM UserAccount u WHERE u.role.name = :roleName")
    List<UserAccount> findByRoleName(@Param("roleName") String roleName);
    
    @Query("SELECT u FROM UserAccount u WHERE u.accountLocked = true")
    List<UserAccount> findLockedAccounts();
    
    @Query("SELECT u FROM UserAccount u WHERE u.accountStanding.id = :standingId")
    List<UserAccount> findByAccountStanding(@Param("standingId") Long standingId);
}