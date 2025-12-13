package com.ac.cst8319.lms.service;

//import com.ac.cst8319.lms.util.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ac.cst8319.lms.dao.HoldBookDao;
import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookStatus;
import com.ac.cst8319.lms.model.Observer.Observer;
import com.ac.cst8319.lms.model.User;
import com.ac.cst8319.lms.model.UserBuilder;
import com.ac.cst8319.lms.model.WaitList;
import com.ac.cst8319.lms.model.WaitListBuilder;
import com.ac.cst8319.lms.model.Observer.BookWaitListSubject;
import com.ac.cst8319.lms.model.Observer.Subject;
import com.ac.cst8319.lms.model.Observer.UserObserver;

/**
 * This class places holds on books and manages the wait lists for books
 * @author Ashleigh Eagleson
 */
public class HoldBookService {

	private HoldBookDao holdBookDao = new HoldBookDao();
	
	/**
	 * Confirming the availability of a book
	 * @param bookCopy the book copy that has it's availability confirmed
	 * @return the book's availability
	 * @throws SQLException if the book cannot be found
	 */
	public BookStatus checkBookStatus(BookCopy bookCopy) throws SQLException{
		
		Map<BookCopy, BookStatus> bookCopyStatus = new HashMap();
		bookCopyStatus = holdBookDao.checkPlaceHoldOnBook(bookCopy.getBookCopyId());
		BookStatus checkedBookStatus = null;
		
		if(bookCopyStatus.size() == 1) {
			if(bookCopyStatus.entrySet().iterator().next().getKey().getBookCopyId() == bookCopy.getBookCopyId()){
				checkedBookStatus = bookCopyStatus.entrySet().iterator().next().getValue();
			}
		}
		else {
			throw new SQLException("Couldn't find the book's availability.");
		}
		return checkedBookStatus;
	}
	
	/**
	 * Placing hold on an available book
	 * @param bookCopy the book copy that has a hold placed on it
	 * @param bookStatus the book's availability that will be updated after placing a hold
	 * @param user the user that placed a hold a book
	 * @param book the book that has a hold placed on it
	 * @return updated book's availability that was updated after placing a hold 
	 * @throws IllegalArgumentException if the book's availability couldn't be updated
	 */
	public BookStatus placeHoldOnAvailableBook(BookCopy bookCopy, BookStatus bookStatus, User user, Book book) throws IllegalArgumentException{
		
		int result = holdBookDao.holdOnAvailableBook(bookCopy.getBookCopyId());
		
		if(result == 1) {
			bookStatus.setName("Unavailable");
			emailAvailableBookWithHold(user, book, bookCopy);
		}
		else if(result == 0){
			throw new IllegalArgumentException("Book's availability couldn't be updated.");
		}
		return bookStatus;	
	}
	
	/**
	 * Sending email to the user who placed a hold on an available book
	 * @param user the user who placed a hold on a book
	 * @param book the book that has a hold on it
	 * @param bookCopy the book copy that the user borrows
	 */
	public void emailAvailableBookWithHold(User user, Book book, BookCopy bookCopy) {
		
		Map<User, Book> userBook = holdBookDao.returnEmailDetails(user, book);
		Map.Entry<User, Book> firstEntry = userBook.entrySet().iterator().next();
		
		String subject = "Placed Hold on a Book that is Available";
		String fromName = "LibraryManagementSystem";
		String fromEmail = "Devel";
		String toName = firstEntry.getKey().getFirstName() + " " + firstEntry.getKey().getLastName();
		String toEmail = firstEntry.getKey().getEmail();
		String message = "You placed a hold on the book " + firstEntry.getValue().getTitle() + " that is available and ready to be picked from " + bookCopy.getLocation() + ". As a reminder, the book is described as " + firstEntry.getValue().getDescription().toLowerCase();
	
		/*
		 * Mailer.sendEmail(subject, fromName, fromEmail, toName, toEmail, message);
		 */
	}
	
	/**
	 * Placing a hold on an unavailable book
	 * @param book the book that has a hold placed on it
	 * @param user the user that placed a hold on a book
	 * @return wait list that is updated after a hold is placed on a book
	 * @throws SQLException if the wait list couldn't be updated
	 */
	public WaitList placeHoldOnUnavailableBook(Book book, User user) throws IllegalArgumentException {
		
		Book returnedBook = holdBookDao.returnBookDetails(book);
		int nextPositionWaitlist = holdBookDao.waitListNextPosition(returnedBook.getBookId()) + 1;
		int result = holdBookDao.holdOnUnavailableBook(returnedBook.getBookId(), user.getUserId(), nextPositionWaitlist);
		
		WaitList userWaitList = null;
		if(result == 1) {
			userWaitList = new WaitListBuilder().setBookId(returnedBook.getBookId()).setUserId(user.getUserId()).setPosition(nextPositionWaitlist).build();
		}
		else if(result == 0) {
			throw new IllegalArgumentException("Couldn't update waitlist");
		}
		return userWaitList;
	}
	
	/**
	 * Updating the wait list after a book was returned
	 * @param book the book that has its wait list updated
	 * @return the wait list that was updated after a book is returned
	 * @throws NullPointerException if the wait list contains no users
	 * @throws SQLException if the wait list size is inconsistent with how many users in the wait list moved up one in the wait list
	 */
	public Map<User, WaitList> updateWaitList(Book book) throws NullPointerException, SQLException {
		
		Book returnedBook = holdBookDao.returnBookDetails(book);
		Map<User, WaitList> waitListUsers = holdBookDao.returnWaitListUsers(returnedBook.getBookId());
		
		if(waitListUsers.size() > 0) {
			
			int result = holdBookDao.removeNextUserWaitlist(returnedBook.getBookId());
			int count = holdBookDao.waitListUserUpdate(returnedBook.getBookId());
			count++;
			int size = waitListUsers.size();
			
			if(result == 1 && count == size) {
				Map<User, WaitList> updatedWaitListUsers = holdBookDao.returnWaitListUsers(returnedBook.getBookId());
				return updatedWaitListUsers;
			}
			else {
				throw new SQLException("Couldn't remove first user in wait list or update all users in the wait list.");
			}
		}
		else {
			throw new NullPointerException("The waitlist is empty.");
		}
	}
	
	/**
	 * Implementing the observer pattern by notifying all users that the wait list was updated
	 * @param book the book that is the subject with updating its observers after its wait list is updated
	 */
	public void subjectNotifyObservers(Book book) {
		
		Book returnedBook = holdBookDao.returnBookDetails(book);
		BookWaitListSubject bookWaitListSubject = new BookWaitListSubject();
		bookWaitListSubject.setBookWaitList(returnedBook);
		
		Map<User, WaitList> waitListObservers = holdBookDao.returnWaitListUsers(returnedBook.getBookId());
		
		for(Map.Entry<User, WaitList> waitListObserver : waitListObservers.entrySet()) {
			
			User user = new UserBuilder().setUserId(waitListObserver.getKey().getUserId()).setEmail(waitListObserver.getKey().getEmail()).setFirstName(waitListObserver.getKey().getFirstName()).setLastName(waitListObserver.getKey().getLastName()).setPhone(waitListObserver.getKey().getPhone()).setAddress(waitListObserver.getKey().getAddress()).build();
			Observer observer = new UserObserver();
			observer.setWaitListUser(user);
			
			if(waitListObserver.getValue().getPosition() == 1) {
				bookWaitListSubject.setNextUser(observer);
			}
			
			bookWaitListSubject.addObserver(observer);
		}
		
		bookWaitListSubject.notifyObservers();
		subjectRemoveObserver(bookWaitListSubject);
	}
	
	/**
	 * Removing the first user in the wait list from being an observer
	 * @param subject the subject represents a book's wait list that is observed by users 
	 */
	public void subjectRemoveObserver(Subject subject) {
		
		for(Observer observer : subject.getObservers()) {
			
			if(observer.getWaitListUser().getFirstName().equals(subject.getNextUser().getWaitListUser().getFirstName()) && observer.getWaitListUser().getLastName().equals(subject.getNextUser().getWaitListUser().getLastName())) {
				subject.removeObserver(observer);
				return;
			}
		}
	}
}
