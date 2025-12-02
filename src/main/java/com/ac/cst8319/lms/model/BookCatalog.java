package com.ac.cst8319.lms.model;

import java.util.ArrayList;
import java.util.List;

import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.dao.BookDAO;

public class BookCatalog {
    // Association (One-to-Many): Manages a list of Book objects
    private List<Book> books;

    public BookCatalog() {
        this.books = new ArrayList<>();
        // implement association to BookServelet and/or BookDAO to create booklist
    }

    // Methods for inventory management
    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void deleteBook(int bookId) {
        this.books.removeIf(book -> book.getID() == bookId);
    }
    
    public Book findBookById(int bookId) {
        return this.books.stream()
            .filter(book -> book.getID() == bookId)
            .findFirst()
            .orElse(null);
    }

    public void updateBook(Book updatedBook) {
        Book existingBook = findBookById(updatedBook.getID());
        if (existingBook != null) {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setAvailable(updatedBook.isAvailable());
        }
    }

}


