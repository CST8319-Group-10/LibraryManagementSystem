package com.ac.cst8319.lms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * Manager entity extending UserAccount
 * Represents system administrators with full access
 */
@Entity
@Table(name = "manager")
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
public class Manager extends UserAccount {
    
    private String department;
    
    // Constructors
    public Manager() {}
    
    public Manager(String username, String password, String email, 
                  String firstName, String lastName, Role role, String department) {
        super(username, password, email, firstName, lastName, role);
        this.department = department;
    }
    
    // Getters and Setters
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}