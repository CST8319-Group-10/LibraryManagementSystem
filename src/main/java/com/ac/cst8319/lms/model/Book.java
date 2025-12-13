package com.ac.cst8319.lms.model;

public class Book {

	private long bookId;
    private String isbn;
    private String title;
    private long authorId;
    private long genreId;
    private String publisher;
    private String publicationYear;
    private String description;
    
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
	public long getGenreId() {
		return genreId;
	}
	public void setGenreId(long genreId) {
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
