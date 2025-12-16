package com.ac.cst8319.lms.model.Prototype;

import com.ac.cst8319.lms.model.BookCopy;

/**
 * A helper class for cloning BookCopy objects. Implementation of the client
 * of the prototype pattern.
 * @author Ashleigh Eagleson
 */
public class BookCopyClient {
	
	private BookCopy bookCopyPrototype;
	
	/**
	 * Setting the prototype that is cloned
	 * @param bookCopyPrototype the prototype that is cloned
	 */
	public BookCopyClient(BookCopy bookCopyPrototype) {
		this.bookCopyPrototype = bookCopyPrototype;
	}
	
	/**
	 * Cloning the prototype to create a book copy
	 * @return the book copy that was created by cloning the prototype
	 */
	public BookCopy createBookCopy() {
		return bookCopyPrototype.clone();
	}
}
