package com.ac.cst8319.lms.model;

/**
 * Helper class for constructing BookStatus objects.
 */
public class BookStatusBuilder {

	public int statusId;
    public String name;
    public String description;

	/**
	 * Set the builder's Status ID.
	 * @param statusId The status ID.
	 * @return This builder.
	 */
	public BookStatusBuilder setStatusId(int statusId) {
		this.statusId = statusId;
		return this;
	}

	/**
	 * Set the builder's name.
	 * @param name The name string.
	 * @return This builder.
	 */
	public BookStatusBuilder setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Set the builder's description.
	 * @param description The description string.
	 * @return This builder.
	 */
	public BookStatusBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Create a new BookStatus object from the builder.
	 * @return Newly created BookStatus object.
	 */
	public BookStatus build() {
    	return new BookStatus(this);
    }
}
