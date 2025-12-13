package com.ac.cst8319.lms.model;

public class AuthorBuilder {

	public long authorId;
	public String firstName;
	public String lastName;
	
	public AuthorBuilder setAuthorId(long authorId) {
		this.authorId = authorId;
		return this;
	}
	
	public AuthorBuilder setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	
	public AuthorBuilder setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	
	public Author build() {
    	return new Author(this);
    }
}
