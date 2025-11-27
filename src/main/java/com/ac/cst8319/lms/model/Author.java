package com.ac.cst8319.lms.model;

import java.time.Instant;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Author {
    private long authorId;
    private String firstName;
    private String lastName;
    private String biography;
    private Instant createdAt;
    private Instant updatedAt;
}