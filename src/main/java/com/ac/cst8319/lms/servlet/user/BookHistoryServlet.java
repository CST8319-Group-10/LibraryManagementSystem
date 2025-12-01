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
@WebServlet(name = "BookHistoryServlet", urlPatterns = {"/member/history"})
public class BookHistoryServlet extends HttpServlet {

    private final UserOperationsService userService;

    public BookHistoryServlet() {
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

        // Get book history for the member
        var bookHistory = userService.getBookHistory(currentUser.getUserId());

        request.setAttribute("bookHistory", bookHistory);

        request.getRequestDispatcher("/WEB-INF/jsp/member/book-history.jsp")
               .forward(request, response);
    }
}
