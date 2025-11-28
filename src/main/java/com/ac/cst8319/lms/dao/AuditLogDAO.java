package com.ac.cst8319.lms.dao;

import com.ac.cst8319.lms.model.AuditLog;
import com.ac.cst8319.lms.model.AuditLogEntry;
import java.time.Instant;
import java.util.List;

/**
 * Data Access Object interface for AuditLog entity.
 */
public interface AuditLogDAO {

    /**
     * Insert a new audit log entry.
     * @param auditLog the audit log entry to insert
     * @return the generated audit entry ID
     */
    long insert(AuditLog auditLog);

    /**
     * Find recent audit log entries (for dashboard).
     * @param limit maximum number of entries to return
     * @return List of recent audit logs
     */
    List<AuditLog> findRecent(int limit);

    /**
     * Find all audit log entries with pagination.
     * @param limit maximum number of entries to return
     * @param offset number of entries to skip
     * @return List of audit logs
     */
    List<AuditLog> findAll(int limit, int offset);

    /**
     * Find audit logs for a specific user.
     * @param userId the user ID
     * @param limit maximum number of entries to return
     * @param offset number of entries to skip
     * @return List of audit logs for the user
     */
    List<AuditLog> findByUserId(long userId, int limit, int offset);

    /**
     * Find audit logs by action ID.
     * @param actionId the action ID
     * @param limit maximum number of entries to return
     * @param offset number of entries to skip
     * @return List of audit logs for the action
     */
    List<AuditLog> findByActionId(int actionId, int limit, int offset);

    /**
     * Find audit logs within a date range.
     * @param startDate start of the date range
     * @param endDate end of the date range
     * @param limit maximum number of entries to return
     * @param offset number of entries to skip
     * @return List of audit logs within the date range
     */
    List<AuditLog> findByDateRange(Instant startDate, Instant endDate, int limit, int offset);

    /**
     * Count total number of audit log entries.
     * @return total count
     */
    long count();

    /**
     * Count audit log entries for a specific user.
     * @param userId the user ID
     * @return count of entries for the user
     */
    long countByUserId(long userId);

    /**
     * Find audit log entries with filters and JOIN user/action data.
     * @param userId filter by user ID (null = all users)
     * @param roleId filter by role ID (null = all roles)
     * @param actionId filter by action ID (null = all actions)
     * @param startDate filter by start date (null = no start limit)
     * @param endDate filter by end date (null = no end limit)
     * @param limit maximum number of entries to return
     * @param offset number of entries to skip
     * @return List of enriched audit log entries
     */
    List<AuditLogEntry> findWithFilters(Long userId, Integer roleId, Integer actionId,
                                        Instant startDate, Instant endDate, int limit, int offset);

    /**
     * Count audit log entries matching the filters.
     * @param userId filter by user ID (null = all users)
     * @param roleId filter by role ID (null = all roles)
     * @param actionId filter by action ID (null = all actions)
     * @param startDate filter by start date (null = no start limit)
     * @param endDate filter by end date (null = no end limit)
     * @return count of matching entries
     */
    long countWithFilters(Long userId, Integer roleId, Integer actionId,
                          Instant startDate, Instant endDate);
}
