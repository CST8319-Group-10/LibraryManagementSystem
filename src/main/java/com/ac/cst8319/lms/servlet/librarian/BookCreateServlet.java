package com.ac.cst8319.lms.servlet.librarian;

import com.ac.cst8319.lms.model.Author;
import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.Genre;
import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.BookManagementService;
import com.ac.cst8319.lms.util.RequestUtil;
import com.ac.cst8319.lms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Book create servlet - handles book creation.
 */
@WebServlet(name = "BookCreateServlet", urlPatterns = {"/librarian/books/create"})
public class BookCreateServlet extends HttpServlet {

    private final BookManagementService bookService;

    public BookCreateServlet() {
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

        // Load authors and genres for dropdown
        List<Author> authors = bookService.getAllAuthors();
        List<Genre> genres = bookService.getAllGenres();

        request.setAttribute("authors", authors);
        request.setAttribute("genres", genres);

        request.getRequestDispatcher("/WEB-INF/jsp/librarian/book-form.jsp")
               .forward(request, response);
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
            String isbn = request.getParameter("isbn");
            String title = request.getParameter("title");
            String authorIdParam = request.getParameter("authorId");
            String genreIdParam = request.getParameter("genreId");
            String publisher = request.getParameter("publisher");
            String publicationYear = request.getParameter("publicationYear");
            String description = request.getParameter("description");

            // Create book object
            Book book = new Book();
            book.setIsbn(isbn);
            book.setTitle(title);
            book.setAuthorId(Long.parseLong(authorIdParam));
            book.setGenreId(Integer.parseInt(genreIdParam));
            book.setPublisher(publisher);
            book.setPublicationYear(publicationYear);
            book.setDescription(description);
            book.setCreatedBy(currentUser.getUserId());
            book.setUpdatedBy(currentUser.getUserId());

            // Create book
            bookService.createBook(book);

            SessionUtil.setSuccessMessage(session, "Book created successfully");
            response.sendRedirect(request.getContextPath() + "/librarian/books");

        } catch (NumberFormatException e) {
            SessionUtil.setErrorMessage(session, "Invalid input format");
            response.sendRedirect(request.getContextPath() + "/librarian/books/create");
        } catch (IllegalArgumentException e) {
            SessionUtil.setErrorMessage(session, e.getMessage());
            response.sendRedirect(request.getContextPath() + "/librarian/books/create");
        } catch (Exception e) {
            SessionUtil.setErrorMessage(session, "Error creating book: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/librarian/books/create");
        }
    }
}
