package com.ac.cst8319.lms.model.Prototype;

import java.time.LocalDate;

/**
 * Interface for cloneable BookCopy objects.
 */
public interface BookCopyClone extends Cloneable {

	/**
	 * Clone the BookCopy object.
	 * @return
	 */
	public BookCopyClone clone();

	/**
	 * Set the BookCopyId of this BookCopy.
	 * @param bookCopyId The ID to set
	 */
	public void setBookCopyId(long bookCopyId);

	/**
	 * Set the location of the book copy.
	 * @param Location The location of this book copy.
	 */
	public void setLocation(String Location);
}
