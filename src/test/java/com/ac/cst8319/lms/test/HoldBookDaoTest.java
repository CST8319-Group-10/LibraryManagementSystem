package com.ac.cst8319.lms.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ac.cst8319.lms.dao.HoldBookDao;
import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookBuilder;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookCopyBuilder;
import com.ac.cst8319.lms.model.BookStatus;
import com.ac.cst8319.lms.model.User;
import com.ac.cst8319.lms.model.UserBuilder;
import com.ac.cst8319.lms.model.WaitList;
import com.ac.cst8319.lms.model.WaitListBuilder;

public class HoldBookDaoTest {

	@Test
	public void testcheckPlaceHoldOnBook() {
		HoldBookDao holdBookDao = new HoldBookDao();
		
		BookCopy bookCopy = new BookCopyBuilder().setBookCopyId(0).build();
		
		Map<BookCopy, BookStatus> bookCopyStatus = holdBookDao.checkPlaceHoldOnBook(bookCopy.getBookCopyId());
		assertNull(bookCopyStatus);
	}
	
	@Test
	public void testHoldOnAvailableBook() {
		HoldBookDao holdBookDao = new HoldBookDao();
		
		int bookCopyIdValue = 1;
		long bookCopyId = (long)bookCopyIdValue;
		int result = holdBookDao.holdOnAvailableBook(bookCopyId);
		
		int rowUpdated = 1;
		assertTrue(rowUpdated == result);
	}

	@Test
	public void testReturnWaitListUsers() {
		HoldBookDao holdBookDao = new HoldBookDao();
		
		User expectedUser = new UserBuilder().setFirstName("David").build();
		WaitList expectedWaitList = new WaitListBuilder().setPosition(1).build();
		
		Book book = new BookBuilder().setBookId(1).build();
		long bookId = (long)book.getBookId();
		Map<User, WaitList> waitListUsers = holdBookDao.returnWaitListUsers(bookId);
		
		Map.Entry<User, WaitList> waitListUser = waitListUsers.entrySet().iterator().next();
		
		assertEquals(expectedUser.getFirstName(), waitListUser.getKey().getFirstName());
		assertEquals(expectedWaitList.getPosition(), waitListUser.getValue().getPosition());
	}
	
	@Test
	public void testReturnEmailDetails() {
		HoldBookDao holdBookDao = new HoldBookDao();
		
		User user = new UserBuilder().setUserId(1).build();
		Book book = new BookBuilder().setTitle("Notebook").build();
		Map<User, Book> userBook = holdBookDao.returnEmailDetails(user, book);
		
		User expectedUser = new UserBuilder().setEmail("ashleigheagleson@gmail.com").build();
		Book expectedBook = new BookBuilder().setDescription("Sad love story.").build();
		
		Map.Entry<User, Book> entryUserBook = userBook.entrySet().iterator().next();
		
		assertEquals(expectedUser.getEmail(), entryUserBook.getKey().getEmail());
		assertEquals(expectedBook.getDescription(), entryUserBook.getValue().getDescription());
	}
	
	@Test
	public void testReturnBookDetails() {
		HoldBookDao holdBookDao = new HoldBookDao();
		
		Book book = new BookBuilder().setTitle("Noitebok").build();
		Book foundBook = holdBookDao.returnBookDetails(book);
		
		assertNull(foundBook);
	}

}
