package com.ac.cst8319.lms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * Member entity extending UserAccount
 * Represents library members with borrowing privileges
 */
@Entity
@Table(name = "member")
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
public class Member extends UserAccount {
    
    private String address;
    
    // Constructors
    public Member() {}
    
    public Member(String username, String password, String email, 
                 String firstName, String lastName, Role role, String address) {
        super(username, password, email, firstName, lastName, role);
        this.address = address;
    }
    
    // Getters and Setters
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}