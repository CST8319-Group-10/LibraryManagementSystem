package com.ac.cst8319.lms.servlet.librarian;

import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookStatus;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Book copy management servlet - manages copies for a specific book.
 */
@WebServlet(name = "BookCopyManageServlet", urlPatterns = {"/librarian/books/copies"})
public class BookCopyManageServlet extends HttpServlet {

    private final BookManagementService bookService;

    public BookCopyManageServlet() {
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
        String bookIdParam = request.getParameter("bookId");
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

            // Get book copies
            List<BookCopy> bookCopies = bookService.getBookCopiesByBookId(bookId);

            // Get all book statuses for dropdown
            List<BookStatus> bookStatuses = bookService.getAllBookStatuses();

            // Get success/error messages from session
            String successMessage = SessionUtil.getAndClearSuccessMessage(session);
            String errorMessage = SessionUtil.getAndClearErrorMessage(session);

            if (successMessage != null) {
                request.setAttribute("successMessage", successMessage);
            }
            if (errorMessage != null) {
                request.setAttribute("errorMessage", errorMessage);
            }

            request.setAttribute("book", bookOpt.get());
            request.setAttribute("bookCopies", bookCopies);
            request.setAttribute("bookStatuses", bookStatuses);

            request.getRequestDispatcher("/WEB-INF/jsp/librarian/book-copy-manage.jsp")
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

        String action = request.getParameter("action");
        String bookIdParam = request.getParameter("bookId");

        try {
            long bookId = Long.parseLong(bookIdParam);

            if ("add".equals(action)) {
                handleAddCopy(request, bookId, session);
            } else if ("delete".equals(action)) {
                handleDeleteCopy(request, session);
            }

            response.sendRedirect(request.getContextPath() + "/librarian/books/copies?bookId=" + bookId);

        } catch (NumberFormatException e) {
            SessionUtil.setErrorMessage(session, "Invalid input format");
            response.sendRedirect(request.getContextPath() + "/librarian/books");
        } catch (Exception e) {
            SessionUtil.setErrorMessage(session, "Error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/librarian/books");
        }
    }

    private void handleAddCopy(HttpServletRequest request, long bookId, HttpSession session) {
        try {
            String statusIdParam = request.getParameter("statusId");
            String location = request.getParameter("location");
            String acquisitionDateParam = request.getParameter("acquisitionDate");

            BookCopy bookCopy = new BookCopy();
            bookCopy.setBookId(bookId);
            bookCopy.setStatusId(Integer.parseInt(statusIdParam));
            bookCopy.setLocation(location);

            if (acquisitionDateParam != null && !acquisitionDateParam.trim().isEmpty()) {
                bookCopy.setAcquisitionDate(LocalDate.parse(acquisitionDateParam));
            } else {
                bookCopy.setAcquisitionDate(LocalDate.now());
            }

            bookService.createBookCopy(bookCopy);
            SessionUtil.setSuccessMessage(session, "Book copy added successfully");

        } catch (Exception e) {
            SessionUtil.setErrorMessage(session, "Error adding book copy: " + e.getMessage());
        }
    }

    private void handleDeleteCopy(HttpServletRequest request, HttpSession session) {
        try {
            String copyIdParam = request.getParameter("copyId");
            long copyId = Long.parseLong(copyIdParam);

            bookService.deleteBookCopy(copyId);
            SessionUtil.setSuccessMessage(session, "Book copy deleted successfully");

        } catch (Exception e) {
            SessionUtil.setErrorMessage(session, "Error deleting book copy: " + e.getMessage());
        }
    }
}
