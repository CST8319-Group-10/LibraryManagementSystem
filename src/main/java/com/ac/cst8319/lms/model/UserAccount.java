package com.ac.cst8319.lms.model;

import java.time.Instant;

import jakarta.persistence.*;

import lombok.*;

@Entity(name = "UserAccount")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserAccount {
    @Id
    @GeneratedValue
    private long userId;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private int roleId;
    private int accountStanding;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant lastLoginAt;
    private Instant lastReminderSentAt;

    /* TODO: Add the JOINed members as needed by services
    */
}