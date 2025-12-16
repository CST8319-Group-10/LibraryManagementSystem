package com.ac.cst8319.lms.model;

import java.time.LocalDate;
import java.util.Objects;

import com.ac.cst8319.lms.model.Prototype.BookCopyClone;

import lombok.*;

/**
 * DTO for Book Copies.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BookCopy implements BookCopyClone {
    private long bookCopyId;
    private long bookId;
    private int statusId;
    private String location;
    private LocalDate acquisitionDate;

	/**
	 * Construct a book copy from a builder.
	 * @param builder The builder to construct a book copy from.
	 */
	public BookCopy(BookCopyBuilder builder) {
		this.bookCopyId = builder.bookCopyId;
		this.bookId = builder.bookId;
		this.statusId = builder.statusId;
		this.location = builder.location;
		this.acquisitionDate = builder.acquisitionDate;
	}

	/**
	 * Construct a book copy from the given bookId and statusId
	 * @param bookId
	 * @param statusId
	 */
	public BookCopy(Long bookId, int statusId) {
		this.bookId = bookId;
		this.statusId = statusId;
	}

	public BookCopy() {
		this.bookId = 0;
		this.statusId = 0;
	}

	public long getBookCopyId() {
		return bookCopyId;
	}

	public void setBookCopyId(long bookCopyId) {
		this.bookCopyId = bookCopyId;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getAcquisitionDate() {
		return acquisitionDate;
	}

	public void setAcquisitionDate(LocalDate acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(acquisitionDate, bookCopyId, bookId, location, statusId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookCopy other = (BookCopy) obj;
		return Objects.equals(acquisitionDate, other.acquisitionDate) && bookCopyId == other.bookCopyId
				&& bookId == other.bookId && Objects.equals(location, other.location) && statusId == other.statusId;
	}

	@Override
	public String toString() {
		return "BookCopy [bookCopyId=" + bookCopyId + ", bookId=" + bookId + ", statusId=" + statusId + ", location="
				+ location + ", acquisitionDate=" + acquisitionDate + "]";
	}

	/**
	 * Creating a book copy by cloning the prototype
	 * @return book copy that is created by cloning the prototype
	 */

	

}

