package com.ac.cst8319.lms.controller;

import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserAccount>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserAccount> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new user
    @PostMapping
    public ResponseEntity<UserAccount> createUser(@RequestBody UserAccount user) {
        UserAccount created = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Update user account standing
    @PutMapping("/{id}/standing")
    public ResponseEntity<UserAccount> updateAccountStanding(
            @PathVariable Long id,
            @RequestParam String standing) {
        try {
            UserAccount updated = userService.updateAccountStanding(id, standing);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Ban user
    @PostMapping("/{id}/ban")
    public ResponseEntity<UserAccount> banUser(@PathVariable Long id) {
        try {
            UserAccount banned = userService.banUser(id);
            return ResponseEntity.ok(banned);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Restore user
    @PostMapping("/{id}/restore")
    public ResponseEntity<UserAccount> restoreUser(@PathVariable Long id) {
        try {
            UserAccount restored = userService.restoreUser(id);
            return ResponseEntity.ok(restored);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}