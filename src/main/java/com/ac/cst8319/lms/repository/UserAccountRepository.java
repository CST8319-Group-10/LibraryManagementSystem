package com.ac.cst8319.lms.repository;

import com.ac.cst8319.lms.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    // Find user by email
    Optional<UserAccount> findByEmail(String email);

    // Check if email exists
    boolean existsByEmail(String email);

    // Find users by role
    List<UserAccount> findByRoleId(int roleId);

    // Find users by account standing
    List<UserAccount> findByAccountStanding(int standingId);
}