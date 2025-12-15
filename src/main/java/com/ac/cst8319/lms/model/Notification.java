package com.ac.cst8319.lms.model;

import java.time.LocalDateTime;


import lombok.*;

/**
 * Notification DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Notification {
    private long notificationId;
    private long userId;
    private String message;
    private int notificationTypeId;
    private LocalDateTime sentAt;
}