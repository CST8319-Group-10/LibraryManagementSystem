package com.ac.cst8319.lms.model;

/**
 * Helper class for constructing Author objects.
 */
public class AuthorBuilder {

	public long authorId;
	public String firstName;
	public String lastName;

	/**
	 * Set the builder's Author ID.
	 * @param authorId The author ID.
	 * @return This builder.
	 */
	public AuthorBuilder setAuthorId(long authorId) {
		this.authorId = authorId;
		return this;
	}

	/**
	 * Set the builder's first name.
	 * @param firstName The first name string.
	 * @return This builder.
	 */
	public AuthorBuilder setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	/**
	 * Set the builder's last name.
	 * @param lastName The last name string.
	 * @return This builder.
	 */
	public AuthorBuilder setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	/**
	 * Create a new Author object from this builder.
	 * @return Newly created Author object.
	 */
	public Author build() {
    	return new Author(this);
    }
}
