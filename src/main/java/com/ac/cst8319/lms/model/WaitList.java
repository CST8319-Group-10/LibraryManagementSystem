package com.ac.cst8319.lms.model;

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
}
