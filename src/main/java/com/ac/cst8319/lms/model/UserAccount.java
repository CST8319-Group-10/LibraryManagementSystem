package com.ac.cst8319.lms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Base UserAccount entity for all registered users
 * Uses JOINED inheritance strategy for separate tables for different user types
 */
@Entity
@Table(name = "user_account")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserAccount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(unique = true, nullable = false)
    private String username;
    
    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    private String email;
    
    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_standing_id")
    private AccountStanding accountStanding;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    
    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts = 0;
    
    @Column(name = "account_locked")
    private Boolean accountLocked = false;
    
    /**
     * Set creation timestamp and initialize default values before persisting
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        
        // Ensure critical fields have default values
        if (failedLoginAttempts == null) {
            failedLoginAttempts = 0;
        }
        if (accountLocked == null) {
            accountLocked = false;
        }
        if (accountStanding == null) {
            // Create a default active account standing
            accountStanding = new AccountStanding();
            accountStanding.setId(1L); // Default to ACTIVE standing
            accountStanding.setName("ACTIVE");
        }
    }
    
    /**
     * Update timestamp before updating
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    
    /**
     * Default constructor
     */
    public UserAccount() {}
    
    /**
     * Parameterized constructor for basic user information
     * @param username the username
     * @param password the password
     * @param email the email address
     * @param firstName the first name
     * @param lastName the last name
     * @param role the user role
     */
    public UserAccount(String username, String password, String email, 
                      String firstName, String lastName, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
    
    // Getters and Setters
    
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getUsername() { 
        return username; 
    }
    
    public void setUsername(String username) { 
        this.username = username; 
    }
    
    public String getPassword() { 
        return password; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public String getFirstName() { 
        return firstName; 
    }
    
    public void setFirstName(String firstName) { 
        this.firstName = firstName; 
    }
    
    public String getLastName() { 
        return lastName; 
    }
    
    public void setLastName(String lastName) { 
        this.lastName = lastName; 
    }
    
    public String getPhoneNumber() { 
        return phoneNumber; 
    }
    
    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber; 
    }
    
    public Role getRole() { 
        return role; 
    }
    
    public void setRole(Role role) { 
        this.role = role; 
    }
    
    public AccountStanding getAccountStanding() { 
        return accountStanding; 
    }
    
    public void setAccountStanding(AccountStanding accountStanding) { 
        this.accountStanding = accountStanding; 
    }
    
    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
    
    public LocalDateTime getUpdatedAt() { 
        return updatedAt; 
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) { 
        this.updatedAt = updatedAt; 
    }
    
    public LocalDateTime getLastLogin() { 
        return lastLogin; 
    }
    
    public void setLastLogin(LocalDateTime lastLogin) { 
        this.lastLogin = lastLogin; 
    }
    
    public Integer getFailedLoginAttempts() { 
        return failedLoginAttempts; 
    }
    
    public void setFailedLoginAttempts(Integer failedLoginAttempts) { 
        this.failedLoginAttempts = failedLoginAttempts; 
    }
    
    public Boolean getAccountLocked() { 
        return accountLocked; 
    }
    
    public void setAccountLocked(Boolean accountLocked) { 
        this.accountLocked = accountLocked; 
    }
    
    /**
     * Utility method to get full name
     * @return concatenated first and last name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    /**
     * Check if account is active
     * @return true if account is not locked and has active standing
     */
    public boolean isActive() {
        return !Boolean.TRUE.equals(accountLocked) && 
               accountStanding != null && 
               "ACTIVE".equals(accountStanding.getName());
    }
    
    /**
     * Check if user has specific role
     * @param roleName the role name to check
     * @return true if user has the specified role
     */
    public boolean hasRole(String roleName) {
        return role != null && roleName.equalsIgnoreCase(role.getName());
    }
    
    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + (role != null ? role.getName() : "null") +
                ", accountStanding=" + (accountStanding != null ? accountStanding.getName() : "null") +
                ", accountLocked=" + accountLocked +
                ", failedLoginAttempts=" + failedLoginAttempts +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserAccount that = (UserAccount) o;
        
        return id != null ? id.equals(that.id) : that.id == null;
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}