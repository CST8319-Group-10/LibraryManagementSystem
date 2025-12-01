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
 * Service for user operations (borrow list, borrow history, etc.).
 */
public class UserOperationsService {

    private final CheckoutDAO checkoutDAO;
    private final BookCopyDAO bookCopyDAO;
    private final UserAccountDAO userDAO;

    public UserOperationsService() {
        this.checkoutDAO = new CheckoutDAOImpl();
        this.bookCopyDAO = new BookCopyDAOImpl();
        this.userDAO = new UserAccountDAOImpl();
    }

    /** Retrieves the Books the member currently has on loan.
     * 
     */
    public List<CheckoutDAO.BorrowedBook> getBorrowedBooks(long memberId) {
        return checkoutDAO.findBorrowedBooksByMember(memberId);
    }

    public List<CheckoutDAO.BookHistory> getBookHistory(long memberId) {
        return checkoutDAO.findBookHistoryByMember(memberId);
    }
}