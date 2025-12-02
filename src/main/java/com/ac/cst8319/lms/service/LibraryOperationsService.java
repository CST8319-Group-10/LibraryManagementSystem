package com.ac.cst8319.lms.service;

import com.ac.cst8319.lms.dao.BookCopyDAO;
import com.ac.cst8319.lms.dao.CheckoutDAO;
import com.ac.cst8319.lms.dao.UserAccountDAO;
import com.ac.cst8319.lms.dao.impl.BookCopyDAOImpl;
import com.ac.cst8319.lms.dao.impl.CheckoutDAOImpl;
import com.ac.cst8319.lms.dao.impl.UserAccountDAOImpl;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.Checkout;
import com.ac.cst8319.lms.model.UserAccount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * Service for library operations (checkout, return, etc.).
 */
public class LibraryOperationsService {

    private static final int LOAN_PERIOD_DAYS = 14;
    private static final BigDecimal LATE_FEE_PER_DAY = new BigDecimal("1.00");
    private static final int BOOK_STATUS_AVAILABLE = 1;
    private static final int BOOK_STATUS_CHECKED_OUT = 2;

    private final CheckoutDAO checkoutDAO;
    private final BookCopyDAO bookCopyDAO;
    private final UserAccountDAO userDAO;

    public LibraryOperationsService() {
        this.checkoutDAO = new CheckoutDAOImpl();
        this.bookCopyDAO = new BookCopyDAOImpl();
        this.userDAO = new UserAccountDAOImpl();
    }

    /**
     * Checkout a book to a member.
     * @param memberId the member's user ID
     * @param bookCopyId the book copy ID
     * @param librarianId the librarian performing the checkout
     * @return the created checkout record
     * @throws IllegalArgumentException if validation fails
     */
    public Checkout checkoutBook(long memberId, long bookCopyId, long librarianId) {
        // Validate member exists and can checkout
        Optional<UserAccount> memberOpt = userDAO.findById(memberId);
        if (!memberOpt.isPresent()) {
            throw new IllegalArgumentException("Member not found");
        }

        UserAccount member = memberOpt.get();
        if (!canCheckout(memberId)) {
            throw new IllegalArgumentException("Member cannot checkout books (suspended or has overdue items)");
        }

        // Validate book copy exists and is available
        Optional<BookCopy> bookCopyOpt = bookCopyDAO.findById(bookCopyId);
        if (!bookCopyOpt.isPresent()) {
            throw new IllegalArgumentException("Book copy not found");
        }

        BookCopy bookCopy = bookCopyOpt.get();
        if (bookCopy.getStatusId() != BOOK_STATUS_AVAILABLE) {
            throw new IllegalArgumentException("Book copy is not available for checkout");
        }

        // Create checkout record
        Checkout checkout = new Checkout();
        checkout.setLoanedTo(memberId);
        checkout.setBookCopyId(bookCopyId);
        checkout.setCheckoutDate(LocalDate.now());
        checkout.setDueDate(LocalDate.now().plusDays(LOAN_PERIOD_DAYS));
        checkout.setCheckedOutBy(librarianId);
        checkout.setReturnDate(null);
        checkout.setReturnedBy(0);
        checkout.setLateFeeAssessed(null);
        checkout.setLateFeePaid(false);

        // Insert checkout
        long checkoutId = checkoutDAO.insert(checkout);
        checkout.setCheckoutId(checkoutId);

        // Update book copy status to checked out
        bookCopyDAO.updateStatus(bookCopyId, BOOK_STATUS_CHECKED_OUT);

        return checkout;
    }

    /**
     * Return a book.
     * @param checkoutId the checkout ID
     * @param librarianId the librarian processing the return
     * @return the updated checkout record
     * @throws IllegalArgumentException if validation fails
     */
    public Checkout returnBook(long checkoutId, long librarianId) {
        // Get checkout record
        Optional<Checkout> checkoutOpt = checkoutDAO.findById(checkoutId);
        if (!checkoutOpt.isPresent()) {
            throw new IllegalArgumentException("Checkout record not found");
        }

        Checkout checkout = checkoutOpt.get();

        // Check if already returned
        if (checkout.getReturnDate() != null) {
            throw new IllegalArgumentException("Book has already been returned");
        }

        // Set return date
        LocalDate returnDate = LocalDate.now();
        checkout.setReturnDate(returnDate);
        checkout.setReturnedBy(librarianId);

        // Calculate late fee if overdue
        if (returnDate.isAfter(checkout.getDueDate())) {
            long daysLate = ChronoUnit.DAYS.between(checkout.getDueDate(), returnDate);
            BigDecimal lateFee = LATE_FEE_PER_DAY.multiply(new BigDecimal(daysLate));
            checkout.setLateFeeAssessed(lateFee);
            checkout.setLateFeePaid(false);
        }

        // Update checkout record
        checkoutDAO.update(checkout);

        // Update book copy status to available
        bookCopyDAO.updateStatus(checkout.getBookCopyId(), BOOK_STATUS_AVAILABLE);

        return checkout;
    }

    /**
     * Get the number of overdue items for a member.
     * @param memberId the member's user ID
     * @return count of overdue checkouts
     */
    public long getMemberOverdueCount(long memberId) {
        return checkoutDAO.countOverdueByMember(memberId);
    }

    /**
     * Check if a member can checkout books.
     * Members cannot checkout if they are suspended or have overdue items.
     * @param memberId the member's user ID
     * @return true if member can checkout, false otherwise
     */
    public boolean canCheckout(long memberId) {
        // Check account standing
        Optional<UserAccount> userOpt = userDAO.findById(memberId);
        if (!userOpt.isPresent()) {
            return false;
        }

        UserAccount user = userOpt.get();
        // AccountStanding: 1 = Good Standing, 2 = Suspended
        if (user.getAccountStanding() != 1) {
            return false;
        }

        // Check for overdue items
        long overdueCount = getMemberOverdueCount(memberId);
        return overdueCount == 0;
    }

    /**
     * Get all active checkouts.
     * @return list of active checkouts
     */
    public List<Checkout> getActiveCheckouts() {
        return checkoutDAO.findActive();
    }

    /**
     * Get all overdue checkouts.
     * @return list of overdue checkouts
     */
    public List<Checkout> getOverdueCheckouts() {
        return checkoutDAO.findOverdue();
    }

    /**
     * Get active checkouts for a member.
     * @param memberId the member's user ID
     * @return list of active checkouts
     */
    public List<Checkout> getMemberActiveCheckouts(long memberId) {
        return checkoutDAO.findActiveByMember(memberId);
    }

    /**
     * Get all checkouts for a member.
     * @param memberId the member's user ID
     * @return list of all checkouts
     */
    public List<Checkout> getMemberCheckouts(long memberId) {
        return checkoutDAO.findByMember(memberId);
    }

    /**
     * Get checkout by ID.
     * @param checkoutId the checkout ID
     * @return Optional containing the checkout if found
     */
    public Optional<Checkout> getCheckoutById(long checkoutId) {
        return checkoutDAO.findById(checkoutId);
    }

    /**
     * Get statistics for the librarian dashboard.
     * @return array with [totalCheckouts, activeCheckouts, overdueCheckouts]
     */
    public long[] getCheckoutStatistics() {
        return new long[] {
            checkoutDAO.count(),
            checkoutDAO.countActive(),
            checkoutDAO.countOverdue()
        };
    }

    /**
     * Get recent checkouts for dashboard.
     * @param limit number of checkouts to return
     * @return list of recent checkouts
     */
    public List<Checkout> getRecentCheckouts(int limit) {
        List<Checkout> allCheckouts = checkoutDAO.findAll();
        if (allCheckouts.size() <= limit) {
            return allCheckouts;
        }
        return allCheckouts.subList(0, limit);
    }

    /**
     * Get checkouts that have unpaid fees.
     * @return list of checkouts with unpaid fees.
     */
    public List<Checkout> getFeesOwed() {
        return checkoutDAO.findFeesOwed();
    }

    /**
     * Get past checkouts for a member that have unpaid fees.
     * @param memberId the member's user ID
     * @return list of checkouts with unpaid fees for the member.
     */
    public List<Checkout> getFeesOwedById(long memberId) {
        return checkoutDAO.findFeesOwedByMember(memberId);
    }

    /**
     * Get sum of unpaid fees for member.
     * @param memberId the member's user ID
     * @return Sum of member's unpaid fees.
     */
    public BigDecimal getTotalFeesOwedByMember(long memberId) {
        return checkoutDAO.calcTotalFeesOwedByMember(memberId);
    }

    /**
     * Process fee payment for an overdue checkout.
     * @param checkoutId the checkout ID
     * @return the updated checkout record
     * @throws IllegalArgumentException if validation fails
     */
    public Checkout feesPaid(long checkoutId) {
        // Get checkout record
        Optional<Checkout> checkoutOpt = checkoutDAO.findById(checkoutId);
        if (!checkoutOpt.isPresent()) {
            throw new IllegalArgumentException("Checkout record not found");
        }

        Checkout checkout = checkoutOpt.get();

        // Check if fees arleady paid
        if (checkout.getLateFeeAssessed().equals(BigDecimal.ZERO)
            || checkout.isLateFeePaid()) {
            throw new IllegalArgumentException("No fees owing for checkout");
        }

        // Set fee as paid
        checkout.setLateFeePaid(true);

        // Update checkout record
        checkoutDAO.update(checkout);

        return checkout;
    }
}
