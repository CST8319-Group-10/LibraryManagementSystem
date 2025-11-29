package com.ac.cst8319.lms.servlet.librarian;

import com.ac.cst8319.lms.model.BookCopy;
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
 * Checkout process servlet - handles book checkout operations.
 */
@WebServlet(name = "CheckoutProcessServlet", urlPatterns = {"/librarian/checkout"})
public class CheckoutProcessServlet extends HttpServlet {

    private final LibraryOperationsService libraryService;
    private final BookManagementService bookService;
    private final MemberManagementService memberService;

    public CheckoutProcessServlet() {
        this.libraryService = new LibraryOperationsService();
        this.bookService = new BookManagementService();
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

        // Get available book copies
        List<BookCopy> availableCopies = bookService.getAvailableBookCopies();

        // Get all members
        List<UserAccount> members = memberService.getAllMembers();

        request.setAttribute("availableCopies", availableCopies);
        request.setAttribute("members", members);

        request.getRequestDispatcher("/WEB-INF/jsp/librarian/checkout-form.jsp")
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
            String memberIdParam = request.getParameter("memberId");
            String bookCopyIdParam = request.getParameter("bookCopyId");

            long memberId = Long.parseLong(memberIdParam);
            long bookCopyId = Long.parseLong(bookCopyIdParam);

            // Process checkout
            Checkout checkout = libraryService.checkoutBook(memberId, bookCopyId, currentUser.getUserId());

            SessionUtil.setSuccessMessage(session,
                "Book checked out successfully. Due date: " + checkout.getDueDate());
            response.sendRedirect(request.getContextPath() + "/librarian/dashboard");

        } catch (NumberFormatException e) {
            SessionUtil.setErrorMessage(session, "Invalid input format");
            response.sendRedirect(request.getContextPath() + "/librarian/checkout");
        } catch (IllegalArgumentException e) {
            SessionUtil.setErrorMessage(session, e.getMessage());
            response.sendRedirect(request.getContextPath() + "/librarian/checkout");
        } catch (Exception e) {
            SessionUtil.setErrorMessage(session, "Error processing checkout: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/librarian/checkout");
        }
    }
}
