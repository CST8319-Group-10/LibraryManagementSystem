package com.ac.cst8319.lms.model.Prototype;

import com.ac.cst8319.lms.model.BookStatus;

/**
 * 
 */
public class BookStatusClient {
	
	public BookStatus bookStatusPrototype;
	
	/**
	 * Setting the prototype that is cloned
	 * @param bookStatusPrototype the prototype that is cloned
	 */
	public BookStatusClient(BookStatus bookStatusPrototype) {
		this.bookStatusPrototype = bookStatusPrototype;
	}
	
	
	/**
	 * Cloning the prototype to create a book status
	 * @return the book status that is created by cloning the prototype
	 */
	public BookStatus createBookStatus() {
		return bookStatusPrototype.clone();
	}
}
