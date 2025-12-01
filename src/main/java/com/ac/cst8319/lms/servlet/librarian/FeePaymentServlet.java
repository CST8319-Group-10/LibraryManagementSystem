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
 * Free payment servlet - handles fee payment operations.
 */
@WebServlet(name = "FeePaymentServlet", urlPatterns = {"/librarian/fees"})
public class FeePaymentServlet extends HttpServlet {

    private final LibraryOperationsService libraryService;

    public FeePaymentServlet() {
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

        // Get fees owed
        List<Checkout> feesOwed = libraryService.getFeesOwed();

        request.setAttribute("feesOwed", feesOwed);

        request.getRequestDispatcher("/WEB-INF/jsp/librarian/fee-form.jsp")
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

            // Process fee payment
            Checkout checkout = libraryService.feesPaid(checkoutId);

            // Build success message
            String message = "Fees paid successfully.\n"
                + "User now owes: " + libraryService.getTotalFeesOwedByMember(checkout.getLoanedTo())
                + "\n";

            SessionUtil.setSuccessMessage(session, message);
            response.sendRedirect(request.getContextPath() + "/librarian/dashboard");

        } catch (NumberFormatException e) {
            SessionUtil.setErrorMessage(session, "Invalid checkout ID");
            response.sendRedirect(request.getContextPath() + "/librarian/fees");
        } catch (IllegalArgumentException e) {
            SessionUtil.setErrorMessage(session, e.getMessage());
            response.sendRedirect(request.getContextPath() + "/librarian/fees");
        } catch (Exception e) {
            SessionUtil.setErrorMessage(session, "Error processing fee payment: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/librarian/fees");
        }
    }
}
