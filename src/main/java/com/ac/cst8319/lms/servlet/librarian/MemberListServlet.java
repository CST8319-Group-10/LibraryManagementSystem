package com.ac.cst8319.lms.servlet.librarian;

import com.ac.cst8319.lms.model.UserAccount;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Member list servlet - displays all members with overdue information.
 */
@WebServlet(name = "MemberListServlet", urlPatterns = {"/librarian/members"})
public class MemberListServlet extends HttpServlet {

    private final MemberManagementService memberService;
    private final LibraryOperationsService libraryService;

    public MemberListServlet() {
        this.memberService = new MemberManagementService();
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

        // Get success/error messages from session
        String successMessage = SessionUtil.getAndClearSuccessMessage(session);
        String errorMessage = SessionUtil.getAndClearErrorMessage(session);

        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
        }
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        }

        // Get all members
        List<UserAccount> members = memberService.getAllMembers();

        // Get overdue counts for each member
        Map<Long, Long> overdueCountMap = new HashMap<>();
        for (UserAccount member : members) {
            long overdueCount = libraryService.getMemberOverdueCount(member.getUserId());
            overdueCountMap.put(member.getUserId(), overdueCount);
        }

        request.setAttribute("members", members);
        request.setAttribute("overdueCountMap", overdueCountMap);

        request.getRequestDispatcher("/WEB-INF/jsp/librarian/member-list.jsp")
               .forward(request, response);
    }
}
