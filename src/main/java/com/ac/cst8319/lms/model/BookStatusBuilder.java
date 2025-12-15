package com.ac.cst8319.lms.model;

public class BookStatusBuilder {

	public int statusId;
    public String name;
    public String description;
    
	public BookStatusBuilder setStatusId(int statusId) {
		this.statusId = statusId;
		return this;
	}
	
	public BookStatusBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public BookStatusBuilder setDescription(String description) {
		this.description = description;
		return this;
	}
    
    public BookStatus build() {
    	return new BookStatus(this);
    }
}
