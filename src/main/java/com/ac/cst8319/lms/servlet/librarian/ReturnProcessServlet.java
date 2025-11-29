package com.ac.cst8319.lms.servlet.librarian;

import com.ac.cst8319.lms.model.Checkout;
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
import java.math.BigDecimal;
import java.util.List;

/**
 * Return process servlet - handles book return operations.
 */
@WebServlet(name = "ReturnProcessServlet", urlPatterns = {"/librarian/return"})
public class ReturnProcessServlet extends HttpServlet {

    private final LibraryOperationsService libraryService;

    public ReturnProcessServlet() {
        this.libraryService = new LibraryOperationsService();
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

        // Get active checkouts
        List<Checkout> activeCheckouts = libraryService.getActiveCheckouts();

        request.setAttribute("activeCheckouts", activeCheckouts);

        request.getRequestDispatcher("/WEB-INF/jsp/librarian/return-form.jsp")
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
            // Get form parameter
            String checkoutIdParam = request.getParameter("checkoutId");
            long checkoutId = Long.parseLong(checkoutIdParam);

            // Process return
            Checkout checkout = libraryService.returnBook(checkoutId, currentUser.getUserId());

            // Build success message
            String message = "Book returned successfully";
            if (checkout.getLateFeeAssessed() != null &&
                checkout.getLateFeeAssessed().compareTo(BigDecimal.ZERO) > 0) {
                message += ". Late fee assessed: $" + checkout.getLateFeeAssessed();
            }

            SessionUtil.setSuccessMessage(session, message);
            response.sendRedirect(request.getContextPath() + "/librarian/dashboard");

        } catch (NumberFormatException e) {
            SessionUtil.setErrorMessage(session, "Invalid checkout ID");
            response.sendRedirect(request.getContextPath() + "/librarian/return");
        } catch (IllegalArgumentException e) {
            SessionUtil.setErrorMessage(session, e.getMessage());
            response.sendRedirect(request.getContextPath() + "/librarian/return");
        } catch (Exception e) {
            SessionUtil.setErrorMessage(session, "Error processing return: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/librarian/return");
        }
    }
}
