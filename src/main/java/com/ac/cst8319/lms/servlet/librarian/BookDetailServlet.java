package com.ac.cst8319.lms.servlet.librarian;

import com.ac.cst8319.lms.model.Author;
import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.Genre;
import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.BookManagementService;
import com.ac.cst8319.lms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Book detail servlet - displays detailed information about a book.
 */
@WebServlet(name = "BookDetailServlet", urlPatterns = {"/librarian/books/view"})
public class BookDetailServlet extends HttpServlet {

    private final BookManagementService bookService;

    public BookDetailServlet() {
        this.bookService = new BookManagementService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserAccount currentUser = SessionUtil.getCurrentUser(session);

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get book ID parameter
        String bookIdParam = request.getParameter("id");
        if (bookIdParam == null || bookIdParam.trim().isEmpty()) {
            SessionUtil.setErrorMessage(session, "Book ID is required");
            response.sendRedirect(request.getContextPath() + "/librarian/books");
            return;
        }

        try {
            long bookId = Long.parseLong(bookIdParam);

            // Get book
            Optional<Book> bookOpt = bookService.getBookById(bookId);
            if (!bookOpt.isPresent()) {
                SessionUtil.setErrorMessage(session, "Book not found");
                response.sendRedirect(request.getContextPath() + "/librarian/books");
                return;
            }

            Book book = bookOpt.get();

            // Get author
            Optional<Author> authorOpt = bookService.getAuthorById(book.getAuthorId());
            if (authorOpt.isPresent()) {
                request.setAttribute("author", authorOpt.get());
            }

            // Get genre
            Optional<Genre> genreOpt = bookService.getGenreById(book.getGenreId());
            if (genreOpt.isPresent()) {
                request.setAttribute("genre", genreOpt.get());
            }

            // Get book copies
            List<BookCopy> bookCopies = bookService.getBookCopiesByBookId(bookId);

            request.setAttribute("book", book);
            request.setAttribute("bookCopies", bookCopies);

            request.getRequestDispatcher("/WEB-INF/jsp/librarian/book-detail.jsp")
                   .forward(request, response);

        } catch (NumberFormatException e) {
            SessionUtil.setErrorMessage(session, "Invalid book ID");
            response.sendRedirect(request.getContextPath() + "/librarian/books");
        }
    }
}
