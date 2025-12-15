package com.ac.cst8319.lms.model;

import java.util.ArrayList;
import java.util.List;

import com.ac.cst8319.lms.model.Book2;
import com.ac.cst8319.lms.dao.BookDAO2;

/**
 * Book Inventory management class.
 */
public class BookCatalog {
    // Association (One-to-Many): Manages a list of Book objects
    private List<Book2> books;

    public BookCatalog() {
        this.books = new ArrayList<>();
        // Seed some initial data for demonstration purposes
        this.books.add(new Book2(101, "The Hitchhiker's Guide", "Douglas Adams", true));
        this.books.add(new Book2(102, "1984", "George Orwell", false));
    }

    /**
     * Retrieves all the books in the catalog.
     * @return List of book object.
     */
    public List<Book2> getBooks() {
        return books;
    }

    /**
     * Adds a book to the catalog.
     * @param book The book to add.
     */
    public void addBook(Book2 book) {
        this.books.add(book);
    }

    /**
     * Delete a book from the catalog.
     * @param bookId The book ID of the book to delete.
     */
    public void deleteBook(int bookId) {
        this.books.removeIf(book -> book.getID() == bookId);
    }

    /**
     * Retrieves a book by ID from the catalog.
     * @param bookId The ID of the book
     * @return The book object if found, null otherwise.
     */
    public Book2 findBookById(int bookId) {
        return this.books.stream()
            .filter(book -> book.getID() == bookId)
            .findFirst()
            .orElse(null);
    }

    /**
     * Updates a book in the catalog.
     * @param updatedBook The book to update.
     */
    public void updateBook(Book2 updatedBook) {
        Book2 existingBook = findBookById(updatedBook.getID());
        if (existingBook != null) {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setAvailable(updatedBook.isAvailable());
        }
    }
}