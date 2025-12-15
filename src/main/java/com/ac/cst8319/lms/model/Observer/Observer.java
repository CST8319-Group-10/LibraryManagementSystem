package com.ac.cst8319.lms.model.Observer;

import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.User;

/**
 * Interface for Book WaitList Observer.
 */
public interface Observer {

	/**
	 * Update all the observers that the wait list has been updated
	 * @param bookWaitList the book that the users are observing
	 * @param nextUser the user who is first in a book's wait list
	 */
	public void update(Book bookWaitList, Observer nextUser);

	/**
	 * Send notifications to all user that they moved up one in a book's wait list
	 */
	public void shiftWaitList();

	/**
	 * Send a notification to an user that the book is available to be borrowed
	 */
	public void bookAvailable();

	/**
	 * 	Set the user for this observer.
 	 */
	public void setWaitListUser(User user);

	/**
	 * Return the user who is an observer
	 * @return the user who is an observer
	 */
	public User getWaitListUser();

	/**
	 * Return the book that the users are observing
	 * @param bookWaitList the book that the users are observing
	 */
	public void setBookWaitList(Book bookWaitList);
}
