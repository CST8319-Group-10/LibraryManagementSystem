package com.ac.cst8319.lms.servlet.librarian;

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

/**
 * Book delete servlet - handles book deletion.
 */
@WebServlet(name = "BookDeleteServlet", urlPatterns = {"/librarian/books/delete"})
public class BookDeleteServlet extends HttpServlet {

    private final BookManagementService bookService;

    public BookDeleteServlet() {
        this.bookService = new BookManagementService();
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

        // Get book ID parameter
        String bookIdParam = request.getParameter("id");
        if (bookIdParam == null || bookIdParam.trim().isEmpty()) {
            SessionUtil.setErrorMessage(session, "Book ID is required");
            response.sendRedirect(request.getContextPath() + "/librarian/books");
            return;
        }

        try {
            long bookId = Long.parseLong(bookIdParam);

            // Delete book
            bookService.deleteBook(bookId);

            SessionUtil.setSuccessMessage(session, "Book deleted successfully");
            response.sendRedirect(request.getContextPath() + "/librarian/books");

        } catch (NumberFormatException e) {
            SessionUtil.setErrorMessage(session, "Invalid book ID");
            response.sendRedirect(request.getContextPath() + "/librarian/books");
        } catch (IllegalArgumentException e) {
            SessionUtil.setErrorMessage(session, e.getMessage());
            response.sendRedirect(request.getContextPath() + "/librarian/books");
        } catch (Exception e) {
            SessionUtil.setErrorMessage(session, "Error deleting book: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/librarian/books");
        }
    }
}
