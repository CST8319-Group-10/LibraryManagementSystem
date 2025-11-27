package com.ac.cst8319.lms.model;

import jakarta.persistence.*;

import lombok.*;

@Entity(name = "NotificationType")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class NotificationType {
    @Id
    @GeneratedValue
    private int notificationTypeId;
    private String name;
}