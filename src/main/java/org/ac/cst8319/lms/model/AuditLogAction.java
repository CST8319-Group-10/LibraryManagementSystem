package com.ac.cst8319.lms.model;

import jakarta.persistence.*;

import lombok.*;

@Entity(name = "AuditLogAction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AuditLogAction {
    @Id
    @GeneratedValue
    private int auditActionId;
    private String action;
    private int category;
    private String description;
}