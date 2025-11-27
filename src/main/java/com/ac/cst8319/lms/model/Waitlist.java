package com.ac.cst8319.lms.model;

import java.time.Instant;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;

@Entity(name = "WaitList")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Waitlist {
    @Id
    @GeneratedValue
    private long waitListId;
    private long bookId;
    private long userId;
    private LocalDateTime requestedAt;
    private int statusId;
}