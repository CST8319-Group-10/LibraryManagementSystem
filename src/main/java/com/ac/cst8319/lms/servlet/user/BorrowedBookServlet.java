package com.ac.cst8319.lms.servlet.user;

import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.LibraryOperationsService;
import com.ac.cst8319.lms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import com.ac.cst8319.lms.service.UserOperationsService;

/**
 * Borrowed Book list servlet - displays all books currently borrowed by the member.
 */
@WebServlet(name = "BorrowedBookServlet", urlPatterns = {"/member/borrowed"})
public class BorrowedBookServlet extends HttpServlet {

    private final UserOperationsService userService;

    public BorrowedBookServlet() {
        this.userService = new UserOperationsService();
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

        // Get currently borrowed books for the member
        var borrowedBooks = userService.getBorrowedBooks(currentUser.getUserId());
        
        long overdueCount = borrowedBooks.stream()
                            .filter(x -> x.checkout.getDueDate().isBefore(java.time.LocalDate.now()))
                            .count();

        request.setAttribute("borrowedBooks", borrowedBooks);
        request.setAttribute("overdueCount", overdueCount);

        request.getRequestDispatcher("/WEB-INF/jsp/member/borrow-list.jsp")
               .forward(request, response);
    }
}
