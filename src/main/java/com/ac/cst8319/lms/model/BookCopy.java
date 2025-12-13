package com.ac.cst8319.lms.model;

import java.time.LocalDate;

import com.ac.cst8319.lms.model.Prototype.BookCopyClone;

/**
 * 
 */
/**
 * 
 */
/**
 * 
 */
public class BookCopy implements BookCopyClone{
	  	
	private long bookCopyId;
    private long bookId;
    private int statusId;
    private String location;
    private LocalDate aquisitionDate;
    
	public long getBookCopyId() {
		return bookCopyId;
	}
	
	@Override
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
	
	@Override
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDate getAquisitionDate() {
		return aquisitionDate;
	}
	
	public void setAquisitionDate(LocalDate aquisitionDate) {
		this.aquisitionDate = aquisitionDate;
	}
    
	public BookCopy(BookCopyBuilder builder) {
		this.bookCopyId = builder.bookCopyId;
		this.bookId = builder.bookId;
		this.statusId = builder.statusId;
		this.location = builder.location;
		this.aquisitionDate = builder.aquisitionDate;
	}
	
	public BookCopy(Long bookId, int statusId) {
		this.bookId = bookId;
		this.statusId = statusId;
	}
	
	/**
	 * Creating a book copy by cloning the prototype
	 * @return book copy that is created by cloning the prototype
	 */
	@Override
	public BookCopy clone() {
		return new BookCopy(this.bookId, this.statusId);
	}
}
