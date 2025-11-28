package com.ac.cst8319.lms.servlet.librarian;

import com.ac.cst8319.lms.model.Book;
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

/**
 * Book list servlet - displays all books with search functionality.
 */
@WebServlet(name = "BookListServlet", urlPatterns = {"/librarian/books"})
public class BookListServlet extends HttpServlet {

    private final BookManagementService bookService;

    public BookListServlet() {
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

        // Get success/error messages from session
        String successMessage = SessionUtil.getAndClearSuccessMessage(session);
        String errorMessage = SessionUtil.getAndClearErrorMessage(session);

        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
        }
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        }

        // Get search parameter
        String searchTerm = request.getParameter("search");

        // Get books
        List<Book> books;
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            books = bookService.searchBooks(searchTerm);
            request.setAttribute("searchTerm", searchTerm);
        } else {
            books = bookService.getAllBooks();
        }

        request.setAttribute("books", books);

        request.getRequestDispatcher("/WEB-INF/jsp/librarian/book-list.jsp")
               .forward(request, response);
    }
}
