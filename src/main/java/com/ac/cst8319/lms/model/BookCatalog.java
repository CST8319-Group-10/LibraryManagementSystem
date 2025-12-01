package com.ac.cst8319.lms.model;

import java.util.ArrayList;
import java.util.List;

import com.ac.cst8319.lms.model.Book2;
import com.ac.cst8319.lms.dao.BookDAO2;

public class BookCatalog {
    // Association (One-to-Many): Manages a list of Book objects
    private List<Book2> books;

    public BookCatalog() {
        this.books = new ArrayList<>();
        // Seed some initial data for demonstration purposes
        this.books.add(new Book2(101, "The Hitchhiker's Guide", "Douglas Adams", true));
        this.books.add(new Book2(102, "1984", "George Orwell", false));
    }

    // Methods for inventory management
    public List<Book2> getBooks() {
        return books;
    }

    public void addBook(Book2 book) {
        this.books.add(book);
    }

    public void deleteBook(int bookId) {
        this.books.removeIf(book -> book.getID() == bookId);
    }
    
    public Book2 findBookById(int bookId) {
        return this.books.stream()
            .filter(book -> book.getID() == bookId)
            .findFirst()
            .orElse(null);
    }

    public void updateBook(Book2 updatedBook) {
        Book2 existingBook = findBookById(updatedBook.getID());
        if (existingBook != null) {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setAvailable(updatedBook.isAvailable());
        }
    }
}