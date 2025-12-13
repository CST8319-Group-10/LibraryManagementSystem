package com.ac.cst8319.lms.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ac.cst8319.lms.model.Author;
import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookBuilder;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookStatus;
import com.ac.cst8319.lms.service.FindBookService;

class FindBookServiceTest {

	@Test
	void testCheckBookAvailability() {
		FindBookService findBookService = new FindBookService();
		
		Book book = new BookBuilder().setTitle("It Ends With Us").build();
		
		try {
			Map<BookCopy, BookStatus> bookCopies = findBookService.checkBookAvailability(book);
			fail("Expected illegal argument exception to be thrown because the book has no book copies yet.");
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testSearchForBook() {
		FindBookService findBookService = new FindBookService();
		
		Book book = new BookBuilder().setTitle("Cel").build();
		Author author = null;
		
		assertThrows(IllegalArgumentException.class, () -> {findBookService.searchForBook(book, author);});
	}

}
