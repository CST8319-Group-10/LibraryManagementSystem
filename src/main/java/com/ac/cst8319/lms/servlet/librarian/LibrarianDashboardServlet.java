package com.ac.cst8319.lms.servlet.librarian;

import com.ac.cst8319.lms.model.Checkout;
import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.BookManagementService;
import com.ac.cst8319.lms.service.LibraryOperationsService;
import com.ac.cst8319.lms.service.MemberManagementService;
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
 * Librarian dashboard servlet - displays statistics and quick actions.
 */
@WebServlet(name = "LibrarianDashboardServlet", urlPatterns = {"/librarian/dashboard"})
public class LibrarianDashboardServlet extends HttpServlet {

    private final BookManagementService bookService;
    private final LibraryOperationsService libraryService;
    private final MemberManagementService memberService;

    public LibrarianDashboardServlet() {
        this.bookService = new BookManagementService();
        this.libraryService = new LibraryOperationsService();
        this.memberService = new MemberManagementService();
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

        // Get statistics
        long totalBooks = bookService.getTotalBookCount();
        long totalCopies = bookService.getTotalBookCopyCount();
        long totalMembers = memberService.getTotalMemberCount();

        long[] checkoutStats = libraryService.getCheckoutStatistics();
        long totalCheckouts = checkoutStats[0];
        long activeCheckouts = checkoutStats[1];
        long overdueCheckouts = checkoutStats[2];

        // Get recent checkouts
        List<Checkout> recentCheckouts = libraryService.getRecentCheckouts(10);

        // Set attributes
        request.setAttribute("totalBooks", totalBooks);
        request.setAttribute("totalCopies", totalCopies);
        request.setAttribute("totalMembers", totalMembers);
        request.setAttribute("totalCheckouts", totalCheckouts);
        request.setAttribute("activeCheckouts", activeCheckouts);
        request.setAttribute("overdueCheckouts", overdueCheckouts);
        request.setAttribute("recentCheckouts", recentCheckouts);

        request.getRequestDispatcher("/WEB-INF/jsp/librarian/dashboard.jsp")
               .forward(request, response);
    }
}
