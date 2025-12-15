package com.ac.cst8319.lms.model;

import java.time.Instant;

import lombok.*;

/**
 * UserAccount DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserAccount {
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

}