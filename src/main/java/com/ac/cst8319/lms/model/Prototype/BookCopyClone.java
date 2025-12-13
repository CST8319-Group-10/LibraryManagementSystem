package com.ac.cst8319.lms.model.Prototype;

import java.time.LocalDate;

public interface BookCopyClone {
	
	public BookCopyClone clone();
	public void setBookCopyId(long bookCopyId);
	public void setLocation(String Location);
}
