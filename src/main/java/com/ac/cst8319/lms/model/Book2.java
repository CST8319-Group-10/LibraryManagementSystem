package com.ac.cst8319.lms.model;

public class Book2 {
    // Attributes
    private String Title;
    private String Author;
    private int ID;
    private boolean isAvailable; // Added an availability status attribute

    // Constructor added for easier object creation
    public Book2(int ID, String Title, String Author, boolean isAvailable) {
        this.ID = ID;
        this.Title = Title;
        this.Author = Author;
        this.isAvailable = isAvailable;
    }
    
    public Book2() {
    }

    // Methods (Getters and Setters are sufficient for JSP/Servlet interaction)
    public void reserveStatus() {
        // Implementation
    }

    public void reserveNom() {
        // Implementation
    }

    public void returnBy() {
        // Implementation
    }

    // Standard Getters and Setters for JSP access
    public String getTitle() { return Title; }
    public void setTitle(String Title) { this.Title = Title; }
    public String getAuthor() { return Author; }
    public void setAuthor(String Author) { this.Author = Author; }
    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }
}