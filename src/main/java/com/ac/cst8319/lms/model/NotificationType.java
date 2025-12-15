package com.ac.cst8319.lms.model;


import lombok.*;

/**
 * NotificationType DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class NotificationType {
    private int notificationTypeId;
    private String name;
}