package com.ac.cst8319.lms.model.Observer;

import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.User;

public interface Observer {
	
	public void update(Book bookWaitlist, Observer nextUser);
	
	public void shiftWaitList();
	public void bookAvailable();
	public void setWaitListUser(User user);
	public User getWaitListUser();
	public void setBookWaitList(Book bookWaitList);
}
