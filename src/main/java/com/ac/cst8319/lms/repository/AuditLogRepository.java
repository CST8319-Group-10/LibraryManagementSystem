package com.ac.cst8319.lms.repository;

import com.ac.cst8319.lms.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    // Find logs by user ID
    List<AuditLog> findByUserId(long userId);

    // Find logs by action ID
    List<AuditLog> findByActionId(int actionId);

    // Find logs within date range
    List<AuditLog> findByCreatedAtBetween(Instant start, Instant end);

    // Find recent logs (for dashboard)
    List<AuditLog> findTop10ByOrderByCreatedAtDesc();
}