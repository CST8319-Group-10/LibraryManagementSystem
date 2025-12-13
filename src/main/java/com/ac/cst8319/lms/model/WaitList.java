package com.ac.cst8319.lms.model;

public class WaitList {
	private long waitListId;
    private long bookId;
    private long userId;
    private int position;
    
	public long getWaitListId() {
		return waitListId;
	}
	public void setWaitListId(long waitListId) {
		this.waitListId = waitListId;
	}
	public long getBookId() {
		return bookId;
	}
	public void setBookId(long bookId) {
		this.bookId = bookId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
    
   public WaitList(WaitListBuilder builder) {
	   this.waitListId = builder.waitListId;
	   this.bookId = builder.bookId;
	   this.userId = builder.userId;
	   this.position = builder.position;
   }
}
