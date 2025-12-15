package com.ac.cst8319.lms.model;

/**
 * Helper class for constructing Book objects.
 */
public class BookBuilder {

	public long bookId;
    public String isbn;
    public String title;
    public long authorId;
    public int genreId;
    public String publisher;
    public String publicationYear;
    public String description;

	/**
	 * Set the builder's book ID.
	 * @param bookId The book ID integer.
	 * @return This builder.
	 */
	public BookBuilder setBookId(long bookId) {
		this.bookId = bookId;
		return this;
	}

	/**
	 * Set the builder's ISBN
	 * @param isbn The ISBN string.
	 * @return This builder.
	 */
	public BookBuilder setIsbn(String isbn) {
		this.isbn = isbn;
		return this;
	}

	/**
	 * Set the builder's title.
	 * @param title The title string.
	 * @return This builder.
	 */
	public BookBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * Set the builder's Author ID.
	 * @param authorId The author ID.
	 * @return This builder.
	 */
	public BookBuilder setAuthorId(long authorId) {
		this.authorId = authorId;
		return this;
	}

	/**
	 * Set the builder's genre ID.
	 * @param genreId The genre ID
	 * @return This builder.
	 */
	public BookBuilder setGenreId(int genreId) {
		this.genreId = genreId;
		return this;
	}

	/**
	 * Set the builder's publisher.
	 * @param publisher The publisher string
	 * @return This builder.
	 */
	public BookBuilder setPublisher(String publisher) {
		this.publisher = publisher;
		return this;
	}

	/**
	 * Set the BookBuilder's publicationYear field.
	 * @param publicationYear The publication year.
	 * @return This builder.
	 */
	public BookBuilder setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
		return this;
	}

	/**
	 * Set the BookBuilder's description.
	 * @param description The description string.
	 * @return This builder.
	 */
	public BookBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Create a new Book object using the Builder's attributes.
	 * @return Newly constructed Book object.
	 */
	public Book build() {
    	return new Book(this);
    }
}
