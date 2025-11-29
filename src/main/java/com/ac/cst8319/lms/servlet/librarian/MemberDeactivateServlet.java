package com.ac.cst8319.lms.servlet.librarian;

import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.MemberManagementService;
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
 * Member deactivate servlet - suspends a member account.
 */
@WebServlet(name = "MemberDeactivateServlet", urlPatterns = {"/librarian/members/deactivate"})
public class MemberDeactivateServlet extends HttpServlet {

    private final MemberManagementService memberService;

    public MemberDeactivateServlet() {
        this.memberService = new MemberManagementService();
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

        // Get member ID parameter
        String memberIdParam = request.getParameter("id");
        if (memberIdParam == null || memberIdParam.trim().isEmpty()) {
            SessionUtil.setErrorMessage(session, "Member ID is required");
            response.sendRedirect(request.getContextPath() + "/librarian/members");
            return;
        }

        try {
            long memberId = Long.parseLong(memberIdParam);
            String ipAddress = RequestUtil.getClientIpAddress(request);

            // Deactivate member
            memberService.deactivateMember(memberId, currentUser.getUserId(), ipAddress);

            SessionUtil.setSuccessMessage(session, "Member suspended successfully");
            response.sendRedirect(request.getContextPath() + "/librarian/members");

        } catch (NumberFormatException e) {
            SessionUtil.setErrorMessage(session, "Invalid member ID");
            response.sendRedirect(request.getContextPath() + "/librarian/members");
        } catch (IllegalArgumentException e) {
            SessionUtil.setErrorMessage(session, e.getMessage());
            response.sendRedirect(request.getContextPath() + "/librarian/members");
        } catch (Exception e) {
            SessionUtil.setErrorMessage(session, "Error suspending member: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/librarian/members");
        }
    }
}
