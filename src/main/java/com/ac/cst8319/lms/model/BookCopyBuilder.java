package com.ac.cst8319.lms.model;

import java.time.LocalDate;

public class BookCopyBuilder {

	public long bookCopyId;
    public long bookId;
    public int statusId;
    public String location;
    public LocalDate acquisitionDate;
    
	public BookCopyBuilder setBookCopyId(long bookCopyId) {
		this.bookCopyId = bookCopyId;
		return this;
	}
	
	public BookCopyBuilder setBookId(long bookId) {
		this.bookId = bookId;
		return this;
	}
	
	public BookCopyBuilder setStatusId(int statusId) {
		this.statusId = statusId;
		return this;
	}
	
	public BookCopyBuilder setLocation(String location) {
		this.location = location;
		return this;
	}
	
	public BookCopyBuilder setAcquisitionDate(LocalDate acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
		return this;
	}
    
	public BookCopy build() {
		return new BookCopy(this);
	}
    
}
