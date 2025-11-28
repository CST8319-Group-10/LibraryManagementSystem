package com.ac.cst8319.lms.dao;

import com.ac.cst8319.lms.model.Book;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Book entity.
 */
public interface BookDAO {

    /**
     * Find a book by its ID.
     * @param bookId the book ID
     * @return Optional containing the book if found, empty otherwise
     */
    Optional<Book> findById(long bookId);

    /**
     * Find a book by its ISBN.
     * @param isbn the ISBN
     * @return Optional containing the book if found, empty otherwise
     */
    Optional<Book> findByISBN(String isbn);

    /**
     * Find all books in the system.
     * @return List of all books
     */
    List<Book> findAll();

    /**
     * Search books by title, author name, or ISBN.
     * @param searchTerm the search term
     * @return List of matching books
     */
    List<Book> search(String searchTerm);

    /**
     * Find books by author ID.
     * @param authorId the author ID
     * @return List of books by the author
     */
    List<Book> findByAuthor(long authorId);

    /**
     * Find books by genre ID.
     * @param genreId the genre ID
     * @return List of books in the genre
     */
    List<Book> findByGenre(int genreId);

    /**
     * Insert a new book into the database.
     * @param book the book to insert
     * @return the generated book ID
     */
    long insert(Book book);

    /**
     * Update an existing book's information.
     * @param book the book with updated information
     * @return true if update was successful, false otherwise
     */
    boolean update(Book book);

    /**
     * Delete a book from the database.
     * @param bookId the book ID
     * @return true if deletion was successful, false otherwise
     */
    boolean delete(long bookId);

    /**
     * Count total number of books.
     * @return total book count
     */
    long count();
}
