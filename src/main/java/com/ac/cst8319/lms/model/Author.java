package com.ac.cst8319.lms.model;

public class Author {

	private long authorId;
	private String firstName;
	private String lastName;
	
	public long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(long authorId) {
		authorId = authorId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}	
	
	public Author(AuthorBuilder builder) {
		this.authorId = builder.authorId;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
	}
}

