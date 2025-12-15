package com.ac.cst8319.lms.model.Prototype;

import com.ac.cst8319.lms.model.BookStatus;

/**
 * Cloneable interface for BookStatus objects.
 */
public interface BookStatusClone {

	/**
	 * Clone this BookStatus object.
	 * @return A copy of the BookStatus object.
	 */
	public BookStatus clone();
	
}
