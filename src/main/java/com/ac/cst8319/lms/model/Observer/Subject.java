package com.ac.cst8319.lms.model.Observer;

import java.util.List;

import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.User;

/**
 * Implementation of the subject interface for the observer pattern
 * @author Ashleigh Eagleson
 */
public interface Subject {

	/**
	 * Add observer to the subject
	 * @param observer the observer who observes the subject
	 */
	public void addObserver(Observer observer);

	/**
	 * Remove observer from the subject
	 * @param observer the observer who no longer observes the subject
	 */
	public void removeObserver(Observer observer);

	/**
	 * Notify all the observers when the subject is updated
	 */
	public void notifyObservers();

	/**
	 * Set the subject to be a book's wait list
	 * @param newBookWaitList
	 */
	public void setBookWaitList(Book newBookWaitList);

	/**
	 * Set the user who is first in a book's wait list
	 * @param nextUser the next user is a user who is first in a book's wait list
	 */
	public void setNextUser(Observer nextUser);


	/**
	 * Return the user who is first in a book's wait list
	 * @return the user who is first in a book's wait list
	 */
	public Observer getNextUser();

	/**
	 * Return all the users who are observers for a book's wait list
	 * @return all the observers for a book's wait list
	 */
	public List<Observer> getObservers();
}
