package com.ac.cst8319.lms.service;

import com.ac.cst8319.lms.dao.*;
import com.ac.cst8319.lms.dao.impl.*;
import com.ac.cst8319.lms.model.*;

import java.util.List;
import java.util.Optional;

/**
 * Service for book and book copy management operations.
 */
public class BookManagementService {

    private final BookDAO bookDAO;
    private final BookCopyDAO bookCopyDAO;
    private final AuthorDAO authorDAO;
    private final GenreDAO genreDAO;
    private final BookStatusDAO bookStatusDAO;

    public BookManagementService() {
        this.bookDAO = new BookDAOImpl();
        this.bookCopyDAO = new BookCopyDAOImpl();
        this.authorDAO = new AuthorDAOImpl();
        this.genreDAO = new GenreDAOImpl();
        this.bookStatusDAO = new BookStatusDAOImpl();
    }

    // ========== Book Operations ==========

    /**
     * Get all books.
     * @return list of all books
     */
    public List<Book> getAllBooks() {
        return bookDAO.findAll();
    }

    /**
     * Get a book by ID.
     * @param bookId book ID
     * @return Optional containing the book if found
     */
    public Optional<Book> getBookById(long bookId) {
        return bookDAO.findById(bookId);
    }

    /**
     * Get a book by ISBN.
     * @param isbn ISBN
     * @return Optional containing the book if found
     */
    public Optional<Book> getBookByISBN(String isbn) {
        return bookDAO.findByISBN(isbn);
    }

    /**
     * Search books by title, author, or ISBN.
     * @param searchTerm search term
     * @return list of matching books
     */
    public List<Book> searchBooks(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllBooks();
        }
        return bookDAO.search(searchTerm.trim());
    }

    /**
     * Get books by author.
     * @param authorId author ID
     * @return list of books by the author
     */
    public List<Book> getBooksByAuthor(long authorId) {
        return bookDAO.findByAuthor(authorId);
    }

    /**
     * Get books by genre.
     * @param genreId genre ID
     * @return list of books in the genre
     */
    public List<Book> getBooksByGenre(int genreId) {
        return bookDAO.findByGenre(genreId);
    }

    /**
     * Create a new book.
     * @param book book details
     * @return the created book
     * @throws IllegalArgumentException if validation fails
     */
    public Book createBook(Book book) {
        validateBook(book);

        // Check if ISBN already exists
        if (bookDAO.findByISBN(book.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("A book with this ISBN already exists");
        }

        long bookId = bookDAO.insert(book);
        book.setBookId(bookId);
        return book;
    }

    /**
     * Update a book.
     * @param book book with updated information
     * @return true if update was successful
     * @throws IllegalArgumentException if validation fails
     */
    public boolean updateBook(Book book) {
        validateBook(book);

        // Check if ISBN is used by another book
        Optional<Book> existingBook = bookDAO.findByISBN(book.getIsbn());
        if (existingBook.isPresent() && existingBook.get().getBookId() != book.getBookId()) {
            throw new IllegalArgumentException("Another book already uses this ISBN");
        }

        return bookDAO.update(book);
    }

    /**
     * Delete a book.
     * @param bookId book ID
     * @return true if deletion was successful
     */
    public boolean deleteBook(long bookId) {
        // Check if book has copies
        long copyCount = bookCopyDAO.countByBookId(bookId);
        if (copyCount > 0) {
            throw new IllegalArgumentException("Cannot delete book with existing copies. Delete copies first.");
        }

        return bookDAO.delete(bookId);
    }

    /**
     * Get total book count.
     * @return total number of books
     */
    public long getTotalBookCount() {
        return bookDAO.count();
    }

    // ========== Book Copy Operations ==========

    /**
     * Get all book copies.
     * @return list of all book copies
     */
    public List<BookCopy> getAllBookCopies() {
        return bookCopyDAO.findAll();
    }

    /**
     * Get a book copy by ID.
     * @param bookCopyId book copy ID
     * @return Optional containing the book copy if found
     */
    public Optional<BookCopy> getBookCopyById(long bookCopyId) {
        return bookCopyDAO.findById(bookCopyId);
    }

    /**
     * Get book copies for a specific book.
     * @param bookId book ID
     * @return list of book copies
     */
    public List<BookCopy> getBookCopiesByBookId(long bookId) {
        return bookCopyDAO.findByBookId(bookId);
    }

    /**
     * Get available book copies.
     * @return list of available book copies
     */
    public List<BookCopy> getAvailableBookCopies() {
        return bookCopyDAO.findAvailable();
    }

    /**
     * Get available copies for a specific book.
     * @param bookId book ID
     * @return list of available copies
     */
    public List<BookCopy> getAvailableBookCopiesByBookId(long bookId) {
        return bookCopyDAO.findAvailableByBookId(bookId);
    }

    /**
     * Create a new book copy.
     * @param bookCopy book copy details
     * @return the created book copy
     * @throws IllegalArgumentException if validation fails
     */
    public BookCopy createBookCopy(BookCopy bookCopy) {
        validateBookCopy(bookCopy);

        long bookCopyId = bookCopyDAO.insert(bookCopy);
        bookCopy.setBookCopyId(bookCopyId);
        return bookCopy;
    }

    /**
     * Update a book copy.
     * @param bookCopy book copy with updated information
     * @return true if update was successful
     * @throws IllegalArgumentException if validation fails
     */
    public boolean updateBookCopy(BookCopy bookCopy) {
        validateBookCopy(bookCopy);
        return bookCopyDAO.update(bookCopy);
    }

    /**
     * Delete a book copy.
     * @param bookCopyId book copy ID
     * @return true if deletion was successful
     */
    public boolean deleteBookCopy(long bookCopyId) {
        return bookCopyDAO.delete(bookCopyId);
    }

    /**
     * Get total book copy count.
     * @return total number of book copies
     */
    public long getTotalBookCopyCount() {
        return bookCopyDAO.count();
    }

    // ========== Author Operations ==========

    /**
     * Get all authors.
     * @return list of all authors
     */
    public List<Author> getAllAuthors() {
        return authorDAO.findAll();
    }

    /**
     * Get an author by ID.
     * @param authorId author ID
     * @return Optional containing the author if found
     */
    public Optional<Author> getAuthorById(long authorId) {
        return authorDAO.findById(authorId);
    }

    // ========== Genre Operations ==========

    /**
     * Get all genres.
     * @return list of all genres
     */
    public List<Genre> getAllGenres() {
        return genreDAO.findAll();
    }

    /**
     * Get a genre by ID.
     * @param genreId genre ID
     * @return Optional containing the genre if found
     */
    public Optional<Genre> getGenreById(int genreId) {
        return genreDAO.findById(genreId);
    }

    // ========== Book Status Operations ==========

    /**
     * Get all book statuses.
     * @return list of all book statuses
     */
    public List<BookStatus> getAllBookStatuses() {
        return bookStatusDAO.findAll();
    }

    /**
     * Get a book status by ID.
     * @param statusId status ID
     * @return Optional containing the status if found
     */
    public Optional<BookStatus> getBookStatusById(int statusId) {
        return bookStatusDAO.findById(statusId);
    }

    // ========== Validation ==========

    /**
     * Validate book data.
     */
    private void validateBook(Book book) {
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN is required");
        }
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (book.getAuthorId() <= 0) {
            throw new IllegalArgumentException("Author is required");
        }
        if (book.getGenreId() <= 0) {
            throw new IllegalArgumentException("Genre is required");
        }
    }

    /**
     * Validate book copy data.
     */
    private void validateBookCopy(BookCopy bookCopy) {
        if (bookCopy.getBookId() <= 0) {
            throw new IllegalArgumentException("Book ID is required");
        }
        if (bookCopy.getStatusId() <= 0) {
            throw new IllegalArgumentException("Status is required");
        }
        // Verify book exists
        if (!bookDAO.findById(bookCopy.getBookId()).isPresent()) {
            throw new IllegalArgumentException("Book not found");
        }
    }
}
