package com.ac.cst8319.lms.model;

import java.util.Objects;

import lombok.*;

/**
 * WaitList DTO.
 */
@Getter
@Setter
public class WaitList {
	private long waitListId;
    private long bookId;
    private long userId;
    private int position;

    /**
     * Builder enabling constructor.
     * @param builder
     */
   public WaitList(WaitListBuilder builder) {
	   this.waitListId = builder.waitListId;
	   this.bookId = builder.bookId;
	   this.userId = builder.userId;
	   this.position = builder.position;
   }
   
   public WaitList() {
	   this.waitListId = 0;
	   this.bookId = 0;
	   this.userId = 0;
	   this.position = 0;
   }

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

   @Override
   public String toString() {
	return "WaitList [waitListId=" + waitListId + ", bookId=" + bookId + ", userId=" + userId + ", position=" + position
			+ "]";
   }

   @Override
   public int hashCode() {
	return Objects.hash(bookId, position, userId, waitListId);
   }

   @Override
   public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	WaitList other = (WaitList) obj;
	return bookId == other.bookId && position == other.position && userId == other.userId
			&& waitListId == other.waitListId;
   }
}
