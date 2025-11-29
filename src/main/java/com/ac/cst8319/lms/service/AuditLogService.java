package com.ac.cst8319.lms.service;

import com.ac.cst8319.lms.dao.AuditLogDAO;
import com.ac.cst8319.lms.dao.impl.AuditLogDAOImpl;
import com.ac.cst8319.lms.model.AuditLogEntry;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * Service for managing and querying audit logs.
 */
public class AuditLogService {

    private final AuditLogDAO auditLogDAO;

    public AuditLogService() {
        this.auditLogDAO = new AuditLogDAOImpl();
    }

    /**
     * Get audit logs with filters and pagination.
     */
    public List<AuditLogEntry> getFilteredLogs(Long userId, Integer roleId, Integer actionId,
                                                LocalDate startDate, LocalDate endDate,
                                                int limit, int offset) {
        Instant startInstant = startDate != null
                ? startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
                : null;
        Instant endInstant = endDate != null
                ? endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant()
                : null;

        return auditLogDAO.findWithFilters(userId, roleId, actionId,
                startInstant, endInstant, limit, offset);
    }

    /**
     * Count audit logs matching filters.
     */
    public long countFilteredLogs(Long userId, Integer roleId, Integer actionId,
                                   LocalDate startDate, LocalDate endDate) {
        Instant startInstant = startDate != null
                ? startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
                : null;
        Instant endInstant = endDate != null
                ? endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant()
                : null;

        return auditLogDAO.countWithFilters(userId, roleId, actionId,
                startInstant, endInstant);
    }

    /**
     * Get recent audit logs for dashboard.
     */
    public List<AuditLogEntry> getRecentLogs(int limit) {
        return auditLogDAO.findWithFilters(null, null, null, null, null, limit, 0);
    }
}
