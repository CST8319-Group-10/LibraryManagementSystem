package com.ac.cst8319.lms.model;

public class BookBuilder {

	public long bookId;
    public String isbn;
    public String title;
    public long authorId;
    public int genreId;
    public String publisher;
    public String publicationYear;
    public String description;
    
	public BookBuilder setBookId(long bookId) {
		this.bookId = bookId;
		return this;
	}
	
	public BookBuilder setIsbn(String isbn) {
		this.isbn = isbn;
		return this;
	}
	
	public BookBuilder setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public BookBuilder setAuthorId(long authorId) {
		this.authorId = authorId;
		return this;
	}
	
	public BookBuilder setGenreId(int genreId) {
		this.genreId = genreId;
		return this;
	}
	
	public BookBuilder setPublisher(String publisher) {
		this.publisher = publisher;
		return this;
	}
	
	public BookBuilder setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
		return this;
	}
	
	public BookBuilder setDescription(String description) {
		this.description = description;
		return this;
	}
    
    public Book build() {
    	return new Book(this);
    }
}
