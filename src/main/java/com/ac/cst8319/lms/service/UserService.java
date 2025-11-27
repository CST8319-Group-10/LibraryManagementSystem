package com.ac.cst8319.lms.service;

import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.model.AccountStanding;
import com.ac.cst8319.lms.repository.UserAccountRepository;
import com.ac.cst8319.lms.repository.AccountStandingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserAccountRepository userRepository;

    @Autowired
    private AccountStandingRepository standingRepository;

    @Autowired
    private AuditLogService auditLogService;

    // Get all users
    public List<UserAccount> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public Optional<UserAccount> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    // Get user by email
    public Optional<UserAccount> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Create new user
    public UserAccount createUser(UserAccount user) {
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        UserAccount savedUser = userRepository.save(user);

        // Log the action
        auditLogService.logAction(
                savedUser.getUserId(),
                1, // Assuming 1 = "User Created"
                "User account created: " + user.getEmail()
        );

        return savedUser;
    }

    // Update user account standing
    public UserAccount updateAccountStanding(Long userId, String standingName) {
        UserAccount user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AccountStanding standing = standingRepository.findByName(standingName)
                .orElseThrow(() -> new RuntimeException("Standing not found"));

        user.setAccountStanding(standing.getStandingId());
        user.setUpdatedAt(Instant.now());

        UserAccount updatedUser = userRepository.save(user);

        // Log the action
        auditLogService.logAction(
                userId,
                2, // Assuming 2 = "Account Standing Changed"
                "Account standing changed to: " + standingName
        );

        return updatedUser;
    }

    // Ban user
    public UserAccount banUser(Long userId) {
        return updateAccountStanding(userId, "Banned");
    }

    // Restore user to good standing
    public UserAccount restoreUser(Long userId) {
        return updateAccountStanding(userId, "Good Standing");
    }

    // Get all librarians (assuming roleId 3 = Librarian)
    public List<UserAccount> getAllLibrarians() {
        return userRepository.findByRoleId(3);
    }

    // Add librarian
    public UserAccount addLibrarian(UserAccount user) {
        user.setRoleId(3); // Set role to Librarian
        return createUser(user);
    }

    // Remove librarian (soft delete by changing role)
    public void removeLibrarian(Long userId) {
        UserAccount user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRoleId(2); // Change to regular user
        user.setUpdatedAt(Instant.now());

        userRepository.save(user);

        // Log the action
        auditLogService.logAction(
                userId,
                3, // Assuming 3 = "Role Changed"
                "Librarian privileges removed"
        );
    }

    // Delete user permanently
    public void deleteUser(Long userId) {
        auditLogService.logAction(
                userId,
                4, // Assuming 4 = "User Deleted"
                "User account deleted"
        );

        userRepository.deleteById(userId);
    }
}