package com.ac.cst8319.lms.model;

/**
 * Helper class for constructing WaitList objects.
 */
public class WaitListBuilder {
	
	public long waitListId;
    public long bookId;
    public long userId;
    public int position;

	/**
	 * Set the builder's WaitList ID.
	 * @param waitListId The waitlist ID.
	 * @return This builder.
	 */
	public WaitListBuilder setWaitListId(long waitListId) {
		this.waitListId = waitListId;
		return this;
	}

	/**
	 * Set the builder's book ID.
	 * @param bookId The book ID.
	 * @return This builder.
	 */
	public WaitListBuilder setBookId(long bookId) {
		this.bookId = bookId;
		return this;
	}

	/**
	 * Set the builder's user ID.
	 * @param userId The user ID.
	 * @return This builder.
	 */
	public WaitListBuilder setUserId(long userId) {
		this.userId = userId;
		return this;
	}

	/**
	 * Set the builder's position.
	 * @param position The position.
	 * @return This builder.
	 */
	public WaitListBuilder setPosition(int position) {
		this.position = position;
		return this;
	}

	/**
	 * Create a new WaitList object.
	 * @return Newly created WaitList object.
	 */
	public WaitList build() {
    	return new WaitList(this);
    }
}
