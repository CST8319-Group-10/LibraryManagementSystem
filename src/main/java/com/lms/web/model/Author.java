package com.lms.web.model;

public class Author {

	private long AuthorID;
	private String firstName;
	private String lastName;
	public long getAuthorID() {
		return AuthorID;
	}
	public void setAuthorID(long authorID) {
		AuthorID = authorID;
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
	public void setFullName(String fullName) {
		String[] names = fullName.split(" ");
		this.firstName = names[0];
		this.lastName = names[1];
	}
}

