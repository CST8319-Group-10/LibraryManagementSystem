package com.ac.cst8319.lms.servlet.librarian;

import com.ac.cst8319.lms.model.Author;
import com.ac.cst8319.lms.model.Book;
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
 * Book edit servlet - handles book editing.
 */
@WebServlet(name = "BookEditServlet", urlPatterns = {"/librarian/books/edit"})
public class BookEditServlet extends HttpServlet {

    private final BookManagementService bookService;

    public BookEditServlet() {
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

            // Load authors and genres for dropdown
            List<Author> authors = bookService.getAllAuthors();
            List<Genre> genres = bookService.getAllGenres();

            request.setAttribute("book", bookOpt.get());
            request.setAttribute("authors", authors);
            request.setAttribute("genres", genres);
            request.setAttribute("isEdit", true);

            request.getRequestDispatcher("/WEB-INF/jsp/librarian/book-form.jsp")
                   .forward(request, response);

        } catch (NumberFormatException e) {
            SessionUtil.setErrorMessage(session, "Invalid book ID");
            response.sendRedirect(request.getContextPath() + "/librarian/books");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserAccount currentUser = SessionUtil.getCurrentUser(session);

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Get form parameters
            String bookIdParam = request.getParameter("bookId");
            String isbn = request.getParameter("isbn");
            String title = request.getParameter("title");
            String authorIdParam = request.getParameter("authorId");
            String genreIdParam = request.getParameter("genreId");
            String publisher = request.getParameter("publisher");
            String publicationYear = request.getParameter("publicationYear");
            String description = request.getParameter("description");

            // Create book object
            Book book = new Book();
            book.setBookId(Long.parseLong(bookIdParam));
            book.setIsbn(isbn);
            book.setTitle(title);
            book.setAuthorId(Long.parseLong(authorIdParam));
            book.setGenreId(Integer.parseInt(genreIdParam));
            book.setPublisher(publisher);
            book.setPublicationYear(publicationYear);
            book.setDescription(description);
            book.setUpdatedBy(currentUser.getUserId());

            // Update book
            bookService.updateBook(book);

            SessionUtil.setSuccessMessage(session, "Book updated successfully");
            response.sendRedirect(request.getContextPath() + "/librarian/books/view?id=" + book.getBookId());

        } catch (NumberFormatException e) {
            SessionUtil.setErrorMessage(session, "Invalid input format");
            response.sendRedirect(request.getContextPath() + "/librarian/books");
        } catch (IllegalArgumentException e) {
            SessionUtil.setErrorMessage(session, e.getMessage());
            response.sendRedirect(request.getContextPath() + "/librarian/books");
        } catch (Exception e) {
            SessionUtil.setErrorMessage(session, "Error updating book: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/librarian/books");
        }
    }
}
