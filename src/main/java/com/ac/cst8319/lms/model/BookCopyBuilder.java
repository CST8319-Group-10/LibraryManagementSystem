package com.ac.cst8319.lms.model;

import java.time.LocalDate;

/**
 * Helper class for constructing BookCopy objects.
 */
public class BookCopyBuilder {

	public long bookCopyId;
    public long bookId;
    public int statusId;
    public String location;
    public LocalDate acquisitionDate;

	/**
	 * Set the builder's BookCopy ID.
	 * @param bookCopyId The BookCopy's ID.
	 * @return This builder.
	 */
	public BookCopyBuilder setBookCopyId(long bookCopyId) {
		this.bookCopyId = bookCopyId;
		return this;
	}

	/**
	 * Set the builder's Book ID.
	 * @param bookId The book's ID.
	 * @return This builder.
	 */
	public BookCopyBuilder setBookId(long bookId) {
		this.bookId = bookId;
		return this;
	}

	/**
	 * Set the builder's status ID.
	 * @param statusId The status ID
	 * @return This builder.
	 */
	public BookCopyBuilder setStatusId(int statusId) {
		this.statusId = statusId;
		return this;
	}

	/**
	 * Set the builder's location.
	 * @param location The location string.
	 * @return This builder.
	 */
	public BookCopyBuilder setLocation(String location) {
		this.location = location;
		return this;
	}

	/**
	 * Set the builder acquisition date.
	 * @param acquisitionDate The acquisition date.
	 * @return This builder.
	 */
	public BookCopyBuilder setAcquisitionDate(LocalDate acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
		return this;
	}

	/**
	 * Returns a newly created BookCopy object.
	 * @return Newly created BookCopy object.
	 */
	public BookCopy build() {
		return new BookCopy(this);
	}
    
}
