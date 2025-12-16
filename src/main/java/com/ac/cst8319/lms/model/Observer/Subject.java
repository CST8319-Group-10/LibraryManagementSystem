package com.ac.cst8319.lms.model.Observer;

import java.util.List;

import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.User;

/**
 * Implementation of the subject interface for the observer pattern
 * @author Ashleigh Eagleson
 */
public interface Subject {

	public void addObserver(Observer observer);
	public void removeObserver(Observer observer);
	public void notifyObservers();
	public void setBookWaitList(Book newBookWaitList);
	
	public void setNextUser(Observer nextUser);
	public Observer getNextUser();
	public List<Observer> getObservers();
}
