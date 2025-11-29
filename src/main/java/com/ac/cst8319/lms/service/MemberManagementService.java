package com.ac.cst8319.lms.service;

import com.ac.cst8319.lms.dao.UserAccountDAO;
import com.ac.cst8319.lms.dao.impl.UserAccountDAOImpl;
import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.util.AuditLogger;
import com.ac.cst8319.lms.util.RoleUtil;

import java.util.List;
import java.util.Optional;

/**
 * Service for member management operations (for librarians).
 */
public class MemberManagementService {

    private static final int STANDING_GOOD = 1;
    private static final int STANDING_SUSPENDED = 2;

    private final UserAccountDAO userDAO;

    public MemberManagementService() {
        this.userDAO = new UserAccountDAOImpl();
    }

    /**
     * Get all members (users with Registered User role).
     * @return list of members
     */
    public List<UserAccount> getAllMembers() {
        return userDAO.findByRole(RoleUtil.ROLE_REGISTERED_USER);
    }

    /**
     * Get a member by ID.
     * @param userId user ID
     * @return Optional containing the member if found
     */
    public Optional<UserAccount> getMemberById(long userId) {
        return userDAO.findById(userId);
    }

    /**
     * Activate a member (set account standing to Good Standing).
     * @param memberId member's user ID
     * @param performedBy librarian's user ID
     * @param ipAddress IP address
     * @return true if activation was successful
     */
    public boolean activateMember(long memberId, long performedBy, String ipAddress) {
        // Verify member exists
        Optional<UserAccount> memberOpt = userDAO.findById(memberId);
        if (!memberOpt.isPresent()) {
            throw new IllegalArgumentException("Member not found");
        }

        UserAccount member = memberOpt.get();

        // Check if member is a registered user
        if (member.getRoleId() != RoleUtil.ROLE_REGISTERED_USER) {
            throw new IllegalArgumentException("Can only activate registered user accounts");
        }

        // Update standing
        boolean updated = userDAO.updateStanding(memberId, STANDING_GOOD);

        if (updated) {
            AuditLogger.logStandingUpdated(performedBy, memberId, STANDING_GOOD,
                "Good Standing", "Member activated by librarian", ipAddress);
        }

        return updated;
    }

    /**
     * Deactivate a member (set account standing to Suspended).
     * @param memberId member's user ID
     * @param performedBy librarian's user ID
     * @param ipAddress IP address
     * @return true if deactivation was successful
     */
    public boolean deactivateMember(long memberId, long performedBy, String ipAddress) {
        // Verify member exists
        Optional<UserAccount> memberOpt = userDAO.findById(memberId);
        if (!memberOpt.isPresent()) {
            throw new IllegalArgumentException("Member not found");
        }

        UserAccount member = memberOpt.get();

        // Check if member is a registered user
        if (member.getRoleId() != RoleUtil.ROLE_REGISTERED_USER) {
            throw new IllegalArgumentException("Can only deactivate registered user accounts");
        }

        // Update standing
        boolean updated = userDAO.updateStanding(memberId, STANDING_SUSPENDED);

        if (updated) {
            AuditLogger.logStandingUpdated(performedBy, memberId, STANDING_SUSPENDED,
                "Suspended", "Member suspended by librarian", ipAddress);
        }

        return updated;
    }

    /**
     * Get members by account standing.
     * @param standingId standing ID
     * @return list of members with the specified standing
     */
    public List<UserAccount> getMembersByStanding(int standingId) {
        List<UserAccount> allWithStanding = userDAO.findByStanding(standingId);
        // Filter to only registered users
        allWithStanding.removeIf(user -> user.getRoleId() != RoleUtil.ROLE_REGISTERED_USER);
        return allWithStanding;
    }

    /**
     * Get suspended members.
     * @return list of suspended members
     */
    public List<UserAccount> getSuspendedMembers() {
        return getMembersByStanding(STANDING_SUSPENDED);
    }

    /**
     * Get active members (good standing).
     * @return list of active members
     */
    public List<UserAccount> getActiveMembers() {
        return getMembersByStanding(STANDING_GOOD);
    }

    /**
     * Get total member count.
     * @return total number of members
     */
    public long getTotalMemberCount() {
        return userDAO.countByRole(RoleUtil.ROLE_REGISTERED_USER);
    }
}
