package com.ac.cst8319.lms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * Librarian entity extending UserAccount
 * Represents library staff with operational privileges
 */
@Entity
@Table(name = "librarian")
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
public class Librarian extends UserAccount {
    
    private String education;
    private String experience;
    
    // Constructors
    public Librarian() {}
    
    public Librarian(String username, String password, String email, 
                    String firstName, String lastName, Role role, 
                    String education, String experience) {
        super(username, password, email, firstName, lastName, role);
        this.education = education;
        this.experience = experience;
    }
    
    // Getters and Setters
    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }
    
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
}