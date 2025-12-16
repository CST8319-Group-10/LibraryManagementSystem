package com.ac.cst8319.lms.model.Prototype;

import java.time.LocalDate;

/**
 * Implementation of the prototype interface for the prototype pattern
 * @author Ashleigh Eagleson
 */
public interface BookCopyClone {
	
	public BookCopyClone clone();
	public void setBookCopyId(long bookCopyId);
	public void setLocation(String Location);
}
