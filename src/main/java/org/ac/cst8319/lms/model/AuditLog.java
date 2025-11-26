package com.ac.cst8319.lms.model;

import java.time.Instant;

import jakarta.persistence.*;

import lombok.*;

@Entity(name = "AuditLog")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AuditLog {
    @Id
    @GeneratedValue
    private long auditEntryId;
    private long userId;
    private int actionId;
    private String details;
    private String ipAddress;
    private Instant createdAt;

    /* TODO: Add the JOINed members as needed by services
    */
}