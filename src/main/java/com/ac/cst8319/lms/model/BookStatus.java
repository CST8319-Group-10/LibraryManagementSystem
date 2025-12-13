package com.ac.cst8319.lms.model;

import com.ac.cst8319.lms.model.Prototype.BookStatusClone;

public class BookStatus implements BookStatusClone{
	private int statusId;
    private String name;
    private String description;
    
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	} 
    
	public BookStatus(BookStatusBuilder builder) {
		this.statusId = builder.statusId;
		this.name = builder.name;
		this.description = builder.description;
	}
	
	public BookStatus(int statusId, String name, String description) {
		this.statusId = statusId;
		this.name = name;
		this.description = description;
	}
	
	/**
	 * Creating a book status by cloning the prototype
	 * @return book status that is created by cloning the prototype
	 */
	@Override
	public BookStatus clone() {
		return new BookStatus(this.statusId, this.name, this.description);
	}
}
