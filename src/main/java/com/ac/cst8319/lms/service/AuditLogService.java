package com.ac.cst8319.lms.service;

import com.ac.cst8319.lms.model.AuditLog;
import com.ac.cst8319.lms.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    // Log an action
    public AuditLog logAction(long userId, int actionId, String details) {
        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setActionId(actionId);
        log.setDetails(details);
        log.setCreatedAt(Instant.now());
        log.setIpAddress(""); // TODO: Get actual IP address from request

        return auditLogRepository.save(log);
    }

    // Get all audit logs
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }

    // Get logs by user
    public List<AuditLog> getLogsByUser(long userId) {
        return auditLogRepository.findByUserId(userId);
    }

    // Get logs by action type
    public List<AuditLog> getLogsByAction(int actionId) {
        return auditLogRepository.findByActionId(actionId);
    }

    // Get logs within date range
    public List<AuditLog> getLogsByDateRange(Instant start, Instant end) {
        return auditLogRepository.findByCreatedAtBetween(start, end);
    }

    // Get recent logs (for dashboard)
    public List<AuditLog> getRecentLogs() {
        return auditLogRepository.findTop10ByOrderByCreatedAtDesc();
    }
}