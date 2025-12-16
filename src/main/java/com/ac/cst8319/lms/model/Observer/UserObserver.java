package com.ac.cst8319.lms.model.Observer;

import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.Observer.Observer;
import com.ac.cst8319.lms.util.Mailer;
import com.ac.cst8319.lms.model.User;
//import com.ac.cst8319.lms.util.*;

/**
 * Implementation of the concrete observer for the observer pattern
 * @author Ashleigh Eagleson
 */
public class UserObserver implements Observer{
	
	private User waitListUser;
	private Book bookWaitList;

	/**
	 * Send notifications to all user that they moved up one in a book's wait list
	 */
	@Override
	public void shiftWaitList() {
		String subject = "Wait List Update";
		String fromName = "LibraryManagementSystem";
		String fromEmail = "notification";
		String toName = waitListUser.getFirstName() + " " + waitListUser.getLastName();
		String toEmail = waitListUser.getEmail();
		String message = "You have moved up one in the waitlist for the book with the title " + bookWaitList.getTitle() + ".";

		 Mailer.sendEmail(subject, fromName, fromEmail, toName, toEmail, message);
	}
	
	/**
	 * Send a notification to an user that the book is available to be borrowed
	 */
	@Override
	public void bookAvailable() {
		String subject = "A Book is Available";
		String fromName = "LibraryManagementSystem";
		String fromEmail = "notification";
		String toName = waitListUser.getFirstName() + " " + waitListUser.getLastName();
		String toEmail = waitListUser.getEmail();
		String message = "The book with the title " + bookWaitList.getTitle() + " is available to be borrowed from the library.";

		Mailer.sendEmail(subject, fromName, fromEmail, toName, toEmail, message);
	}

	/**
	 * Set the user who is an observer
	 * @param waitListUser the user who is an observer
	 */
	@Override
	public void setWaitListUser(User waitListUser) {
		this.waitListUser = waitListUser;
	}
	
	/**
	 * Return the user who is an observer
	 * @return the user who is an observer
	 */
	@Override
	public User getWaitListUser() {
		return this.waitListUser;
	}

	/**
	 * Return the book that the users are observing
	 * @param bookWaitList the book that the users observe
	 */
	@Override
	public void setBookWaitList(Book bookWaitList) {
		this.bookWaitList = bookWaitList;
	}
	

	/**
	 * Update all the observers that the wait list has been updated
	 * @param bookWaitList the book that the users observe
	 * @param nextUser the user who is first in a book's wait list
	 */
	@Override
	public void update(Book bookWaitList, Observer nextUser) {
		
		this.bookWaitList = bookWaitList;
		shiftWaitList();
		
		if (waitListUser.getFirstName().equals(nextUser.getWaitListUser().getFirstName()) && waitListUser.getLastName().equals(nextUser.getWaitListUser().getLastName())) {
			bookAvailable();
		}
	}

}
