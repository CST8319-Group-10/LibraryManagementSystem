package com.ac.cst8319.lms.model;

import java.time.Instant;
import java.util.Objects;

import lombok.*;

/**
 * Book DTO.
 */
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

    /**
     * Builder enabling constructor.
     * @param builder The builder to get parameters from.
     */
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

	public Book() {
		super();
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", isbn=" + isbn + ", title=" + title + ", authorId=" + authorId
				+ ", genreId=" + genreId + ", publisher=" + publisher + ", publicationYear=" + publicationYear
				+ ", description=" + description + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(authorId, bookId, createdAt, createdBy, description, genreId, isbn, publicationYear,
				publisher, title, updatedAt, updatedBy);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return authorId == other.authorId && bookId == other.bookId && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(createdBy, other.createdBy) && Objects.equals(description, other.description)
				&& genreId == other.genreId && Objects.equals(isbn, other.isbn)
				&& Objects.equals(publicationYear, other.publicationYear) && Objects.equals(publisher, other.publisher)
				&& Objects.equals(title, other.title) && Objects.equals(updatedAt, other.updatedAt)
				&& Objects.equals(updatedBy, other.updatedBy);
	}
}
