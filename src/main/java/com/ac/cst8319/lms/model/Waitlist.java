package com.ac.cst8319.lms.model;

import java.time.Instant;
import java.time.LocalDateTime;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Waitlist {
    private long waitListId;
    private long bookId;
    private long userId;
    private LocalDateTime requestedAt;
    private int statusId;
}