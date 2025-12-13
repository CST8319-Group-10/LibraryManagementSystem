package com.ac.cst8319.lms.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.*;
import org.junit.jupiter.api.Test;

import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookBuilder;
import com.ac.cst8319.lms.model.User;
import com.ac.cst8319.lms.model.UserBuilder;
import com.ac.cst8319.lms.model.WaitList;
import com.ac.cst8319.lms.model.WaitListBuilder;
import com.ac.cst8319.lms.service.HoldBookService;

public class HoldBookServiceTest {

	@Test
	public void testPlaceHoldOnUnavailableBook() {
		HoldBookService holdBookService = new HoldBookService();
		
		User user = new UserBuilder().setUserId(3).setFirstName("Terri").setLastName("Eagleson").build();
		Book book = new BookBuilder().setBookId(1).setTitle("Notebook").build();
		
		WaitList userWaitList = holdBookService.placeHoldOnUnavailableBook(book, user);
		WaitList expectedWaitList = new WaitListBuilder().setBookId(1).setPosition(3).build();
		
		assertEquals(expectedWaitList.getBookId(), userWaitList.getBookId());
		assertEquals(expectedWaitList.getPosition(), userWaitList.getPosition());
	}

	@Test
	public void testUpdateWaitList() {
		HoldBookService holdBookService = new HoldBookService();
		
		Book book = new BookBuilder().setBookId(1).setTitle("Notebook").build();
		try {
			Map<User, WaitList> waitListUsers = holdBookService.updateWaitList(book);
			int expectedMapSize = 2;
			int mapSize = waitListUsers.size();
			assertTrue(expectedMapSize == mapSize);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateEmptyWaitList() {
		HoldBookService holdBookService = new HoldBookService();
		
		Book book = new BookBuilder().setBookId(2).setTitle("It Ends With Us").build();
		assertThrows(NullPointerException.class, () -> {holdBookService.updateWaitList(book);});
	}

}
