package com.ac.cst8319.lms.model;

import java.time.Instant;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Book {
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

    public Book(BookBuilder builder) {
		this.bookId = builder.bookId;
		this.isbn = builder.isbn;
		this.title = builder.title;
		this.authorId = builder.authorId;
		this.genreId = builder.genreId;
		this.publisher = builder.publisher;
		this.publicationYear = builder.publicationYear;
		this.description = builder.description;
	}
}
