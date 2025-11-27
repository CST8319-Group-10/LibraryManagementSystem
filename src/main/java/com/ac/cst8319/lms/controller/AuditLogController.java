package com.ac.cst8319.lms.controller;

import com.ac.cst8319.lms.model.AuditLog;
import com.ac.cst8319.lms.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    // Get all audit logs
    @GetMapping
    public ResponseEntity<List<AuditLog>> getAllLogs() {
        return ResponseEntity.ok(auditLogService.getAllLogs());
    }

    // Get logs by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLog>> getLogsByUser(@PathVariable long userId) {
        return ResponseEntity.ok(auditLogService.getLogsByUser(userId));
    }

    // Get logs by action type
    @GetMapping("/action/{actionId}")
    public ResponseEntity<List<AuditLog>> getLogsByAction(@PathVariable int actionId) {
        return ResponseEntity.ok(auditLogService.getLogsByAction(actionId));
    }

    // Get recent logs (for dashboard)
    @GetMapping("/recent")
    public ResponseEntity<List<AuditLog>> getRecentLogs() {
        return ResponseEntity.ok(auditLogService.getRecentLogs());
    }

    // Get logs by date range
    @GetMapping("/date-range")
    public ResponseEntity<List<AuditLog>> getLogsByDateRange(
            @RequestParam String start,
            @RequestParam String end) {
        Instant startTime = Instant.parse(start);
        Instant endTime = Instant.parse(end);
        return ResponseEntity.ok(auditLogService.getLogsByDateRange(startTime, endTime));
    }
}