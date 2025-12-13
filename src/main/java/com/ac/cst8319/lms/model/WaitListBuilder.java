package com.ac.cst8319.lms.model;

public class WaitListBuilder {
	
	public long waitListId;
    public long bookId;
    public long userId;
    public int position;
    
	public WaitListBuilder setWaitListId(long waitListId) {
		this.waitListId = waitListId;
		return this;
	}
	public WaitListBuilder setBookId(long bookId) {
		this.bookId = bookId;
		return this;
	}
	public WaitListBuilder setUserId(long userId) {
		this.userId = userId;
		return this;
	}
	public WaitListBuilder setPosition(int position) {
		this.position = position;
		return this;
	}
    
    public WaitList build() {
    	return new WaitList(this);
    }
}
