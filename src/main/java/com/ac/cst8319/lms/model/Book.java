package com.ac.cst8319.lms.model;

import java.time.Instant;

import jakarta.persistence.*;

import lombok.*;

@Entity(name = "Book")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Book {
    @Id
    @GeneratedValue
    private long bookId;
    private String isbn;
    private String title;
    private long authorId;
    private int genreId;
    private String publisher;
    private String publicationYear;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private Long createdBy;
    private Long updatedBy;

    /* TODO: Add the JOINed members as needed by services
    */
}