package com.ac.cst8319.lms.model;

import java.time.Instant;
import lombok.*;

/**
 * Enhanced AuditLog entry with JOINed user and action information for display.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AuditLogEntry {
    // From AuditLog
    private long auditEntryId;
    private long userId;
    private int actionId;
    private String details;
    private String ipAddress;
    private Instant createdAt;

    // From UserAccount (JOIN)
    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private int roleId;

    // From Role (JOIN)
    private String roleName;

    // From AuditLogAction (JOIN)
    private String actionName;
    private String actionDescription;
}
