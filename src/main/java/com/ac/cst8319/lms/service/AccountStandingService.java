package com.ac.cst8319.lms.service;

import com.ac.cst8319.lms.dao.AccountStandingDAO;
import com.ac.cst8319.lms.dao.UserAccountDAO;
import com.ac.cst8319.lms.dao.impl.AccountStandingDAOImpl;
import com.ac.cst8319.lms.dao.impl.UserAccountDAOImpl;
import com.ac.cst8319.lms.model.AccountStanding;
import com.ac.cst8319.lms.util.AuditLogger;

import java.util.List;
import java.util.Optional;

/**
 * Service for account standing management operations.
 */
public class AccountStandingService {

    private final AccountStandingDAO standingDAO;
    private final UserAccountDAO userDAO;

    public AccountStandingService() {
        this.standingDAO = new AccountStandingDAOImpl();
        this.userDAO = new UserAccountDAOImpl();
    }

    /**
     * Get all account standings.
     * @return list of all standings
     */
    public List<AccountStanding> getAllStandings() {
        return standingDAO.findAll();
    }

    /**
     * Get an account standing by ID.
     * @param standingId standing ID
     * @return Optional containing the standing if found
     */
    public Optional<AccountStanding> getStandingById(int standingId) {
        return standingDAO.findById(standingId);
    }

    /**
     * Get an account standing by name.
     * @param name standing name
     * @return Optional containing the standing if found
     */
    public Optional<AccountStanding> getStandingByName(String name) {
        return standingDAO.findByName(name);
    }

    /**
     * Update user's account standing.
     * @param userId user ID
     * @param standingId new standing ID
     * @param updatedBy user ID of admin/librarian making the change
     * @param reason reason for the change
     * @param ipAddress IP address
     * @return true if update was successful
     */
    public boolean updateStanding(long userId, int standingId, long updatedBy, String reason, String ipAddress) {
        // Validate standing ID
        if (standingId < 1 || standingId > 4) {
            throw new IllegalArgumentException("Invalid standing ID");
        }

        // Get standing name for logging
        Optional<AccountStanding> standingOpt = standingDAO.findById(standingId);
        if (standingOpt.isEmpty()) {
            throw new IllegalArgumentException("Standing not found");
        }

        // Update standing
        boolean updated = userDAO.updateStanding(userId, standingId);

        if (updated) {
            String standingName = standingOpt.get().getName();
            AuditLogger.logStandingUpdated(updatedBy, userId, standingId, standingName, reason, ipAddress);
        }

        return updated;
    }

    /**
     * Suspend a user account.
     * @param userId user ID
     * @param suspendedBy user ID of admin/librarian suspending the account
     * @param reason reason for suspension
     * @param ipAddress IP address
     * @return true if suspension was successful
     */
    public boolean suspendAccount(long userId, long suspendedBy, String reason, String ipAddress) {
        return updateStanding(userId, 2, suspendedBy, reason, ipAddress); // Standing ID 2 = Suspended
    }

    /**
     * Reinstate a user account to good standing.
     * @param userId user ID
     * @param reinstatedBy user ID of admin/librarian reinstating the account
     * @param ipAddress IP address
     * @return true if reinstatement was successful
     */
    public boolean reinstateAccount(long userId, long reinstatedBy, String ipAddress) {
        return updateStanding(userId, 1, reinstatedBy, "Account reinstated to good standing", ipAddress);
    }

    /**
     * Ban a user account.
     * @param userId user ID
     * @param bannedBy user ID of admin banning the account
     * @param reason reason for ban
     * @param ipAddress IP address
     * @return true if ban was successful
     */
    public boolean banAccount(long userId, long bannedBy, String reason, String ipAddress) {
        return updateStanding(userId, 3, bannedBy, reason, ipAddress); // Standing ID 3 = Banned
    }
}
