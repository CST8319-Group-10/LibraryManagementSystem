package com.ac.cst8319.lms.controller;

import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/librarians")
public class LibrarianController {

    @Autowired
    private UserService userService;

    // Get all librarians
    @GetMapping
    public ResponseEntity<List<UserAccount>> getAllLibrarians() {
        return ResponseEntity.ok(userService.getAllLibrarians());
    }

    // Add new librarian
    @PostMapping
    public ResponseEntity<UserAccount> addLibrarian(@RequestBody UserAccount user) {
        UserAccount librarian = userService.addLibrarian(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(librarian);
    }

    // Remove librarian (change role to regular user)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeLibrarian(@PathVariable Long id) {
        try {
            userService.removeLibrarian(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}