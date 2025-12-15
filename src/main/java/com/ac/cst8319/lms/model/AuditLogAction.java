package com.ac.cst8319.lms.model;


import lombok.*;

/**
 * AuditLogAction DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AuditLogAction {
    private int auditActionId;
    private String action;
    private int category;
    private String description;
}