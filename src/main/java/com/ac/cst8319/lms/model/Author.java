package com.ac.cst8319.lms.model;

import java.time.Instant;

import jakarta.persistence.*;

import lombok.*;

@Entity(name = "Author")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Author {
    @Id
    @GeneratedValue
    private long authorId;
    private String firstName;
    private String lastName;
    private String biography;
    private Instant createdAt;
    private Instant updatedAt;
}