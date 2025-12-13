package com.ac.cst8319.lms.model.Observer;

import java.util.ArrayList;
import java.util.List;

import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.Observer.Subject;
import com.ac.cst8319.lms.model.Observer.Observer;
import com.ac.cst8319.lms.model.User;

/**
 * Implementation for the concrete subject of the observer pattern
 */
public class BookWaitListSubject implements Subject{

	private List<Observer> observers = new ArrayList<>();
	private Book bookWaitList;
	private Observer nextUser;

	/**
	 * Add observer to the subject
	 * @param observer the observer who observes the subject
	 */
	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	/**
	 * Remove observer from the subject
	 * @param observer the observer who no longer observes the subject
	 */
	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	/**
	 * Notify all the observers when the subject is updated
	 */
	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(bookWaitList, nextUser);
		}
	}
	
	/**
	 * Set the subject to be a book's wait list
	 * @param newBookWaitList
	 */
	@Override
	public void setBookWaitList(Book newBookWaitList) {
		this.bookWaitList = newBookWaitList;
		notifyObservers();
	}
	
	/**
	 * Set the user who is first in a book's wait list
	 * @param nextUser the next user is a user who is first in a book's wait list
	 */
	@Override
	public void setNextUser(Observer nextUser) {
		this.nextUser = nextUser;
	}
	
	/**
	 * Return the user who is first in a book's wait list
	 * @return the user who is first in a book's wait list
	 */
	@Override
	public Observer getNextUser() {
		return this.nextUser;
	}
	
	/**
	 * Return all the users who are observers for a book's wait list
	 * @return all the observers for a book's wait list
	 */
	public List<Observer> getObservers(){
		return this.observers;
	}


}
