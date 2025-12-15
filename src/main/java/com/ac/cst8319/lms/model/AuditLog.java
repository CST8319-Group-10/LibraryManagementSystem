package com.ac.cst8319.lms.model;

import java.time.Instant;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AuditLog {
    private long auditEntryId;
    private long userId;
    private int actionId;
    private String details;
    private String ipAddress;
    private Instant createdAt;

}