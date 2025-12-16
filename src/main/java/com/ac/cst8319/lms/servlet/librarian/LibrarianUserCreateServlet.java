package com.ac.cst8319.lms.servlet.librarian;

import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.AccountStandingService;
import com.ac.cst8319.lms.service.UserManagementService;
import com.ac.cst8319.lms.util.RequestUtil;
import com.ac.cst8319.lms.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet for creating a new member user (librarian access).
 * Librarians can only create regular members (roleId = 2).
 */
@WebServlet(name = "LibrarianUserCreateServlet", urlPatterns = {"/librarian/users/create"})
public class LibrarianUserCreateServlet extends HttpServlet {

    private final UserManagementService userService;
    private final AccountStandingService standingService;

    public LibrarianUserCreateServlet() {
        this.userService = new UserManagementService();
        this.standingService = new AccountStandingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Load standings for dropdown
        request.setAttribute("standings", standingService.getAllStandings());

        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/user-create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters
        String email = RequestUtil.getStringParameter(request, "email");
        String password = RequestUtil.getStringParameter(request, "password");
        String firstName = RequestUtil.getStringParameter(request, "firstName");
        String lastName = RequestUtil.getStringParameter(request, "lastName");
        String phone = RequestUtil.getStringParameter(request, "phone");
        String address = RequestUtil.getStringParameter(request, "address");
        int standingId = RequestUtil.getIntParameter(request, "standingId", 1);

        // Create user object - force roleId to 2 (regular member)
        UserAccount user = new UserAccount();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setAddress(address);
        user.setRoleId(2); // Always create as regular member
        user.setAccountStanding(standingId);

        try {
            // Get current librarian user
            HttpSession session = request.getSession();
            long createdBy = SessionUtil.getCurrentUserId(session);
            String ipAddress = RequestUtil.getClientIpAddress(request);

            // Create user
            userService.createUser(user, password, createdBy, ipAddress);

            // Set success message
            SessionUtil.setSuccessMessage(session, "Member account created successfully!");

            // Redirect to user list
            response.sendRedirect(request.getContextPath() + "/librarian/users");

        } catch (IllegalArgumentException e) {
            // Creation failed
            request.setAttribute("error", e.getMessage());
            request.setAttribute("standings", standingService.getAllStandings());
            request.getRequestDispatcher("/WEB-INF/jsp/librarian/user-create.jsp").forward(request, response);
        }
    }
}
