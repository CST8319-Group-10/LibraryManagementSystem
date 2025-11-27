package com.ac.cst8319.lms.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;

@Entity(name = "Notification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Notification {
    @Id
    @GeneratedValue
    private long notificationId;
    private long userId;
    private String message;
    private int notificationTypeId;
    private LocalDateTime sentAt;
}