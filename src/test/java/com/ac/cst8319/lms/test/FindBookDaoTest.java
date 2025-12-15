package com.ac.cst8319.lms.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ac.cst8319.lms.dao.FindBookDao;
import com.ac.cst8319.lms.model.Author;
import com.ac.cst8319.lms.model.AuthorBuilder;
import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookBuilder;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookCopyBuilder;
import com.ac.cst8319.lms.model.BookStatus;
import com.ac.cst8319.lms.model.BookStatusBuilder;

public class FindBookDaoTest {

	@Test
	public void testSearchByAuthor() {
		FindBookDao findBookDao = new FindBookDao();
		Map<Book, Author> foundBookAuthor = findBookDao.searchByAuthor("Stephen", "King");
		
		Book book = new BookBuilder().setTitle("Cell").build();
		Author author = new AuthorBuilder().setAuthorId(3).setFirstName("Stephen").setLastName("King").build();
	
		Map.Entry<Book, Author> firstEntry = foundBookAuthor.entrySet().iterator().next();
		
		assertEquals(book.getTitle(), firstEntry.getKey().getTitle());	
		assertEquals(author.getAuthorId(), firstEntry.getValue().getAuthorId());
	}

	@Test
	public void testSearchByTitle() {
		FindBookDao findBookDao = new FindBookDao();
		Map<Book, Author> foundBookAuthor = findBookDao.searchByTitle("It Ends With Us");
		
		Book book = new BookBuilder().setTitle("It Ends With Us").setDescription("Abuse and love story.").build();
		Author author = new AuthorBuilder().setFirstName("Colleen").setLastName("Hoover").build();
		
		Map.Entry<Book, Author> firstEntry = foundBookAuthor.entrySet().iterator().next();
		
		assertEquals(book.getDescription(), firstEntry.getKey().getDescription());	
		assertEquals(author.getFirstName(), firstEntry.getValue().getFirstName());
	}

	@Test
	public void testBrowseByGenre() {
		FindBookDao findBookDao = new FindBookDao();
		Map<Book, Author> foundBooksAuthors = findBookDao.browseByGenre("Romance");
		
		Book bookOne = new BookBuilder().setBookId(1).setTitle("Notebook").build();
		Book bookTwo = new BookBuilder().setBookId(2).setTitle("It Ends With Us").build();
		
		Author authorOne = new AuthorBuilder().setFirstName("Nicholas").build();
		Author authorTwo = new AuthorBuilder().setFirstName("Colleen").build();
		
		for(Map.Entry<Book, Author> foundBookAuthor : foundBooksAuthors.entrySet()) {
			
			if(foundBookAuthor.getKey().getBookId() == 1) {
				
				assertEquals(bookOne.getTitle(), foundBookAuthor.getKey().getTitle());
				assertEquals(authorOne.getFirstName(), foundBookAuthor.getValue().getFirstName());
			}
			else if(foundBookAuthor.getKey().getBookId() == 2) {
				
				assertEquals(bookTwo.getTitle(), foundBookAuthor.getKey().getTitle());
				assertEquals(authorTwo.getFirstName(), foundBookAuthor.getValue().getFirstName());
			}
		}
	}

	@Test
	public void testCheckBookAvailability() {
		FindBookDao findBookDao = new FindBookDao();
		Map<BookCopy, BookStatus> bookCopies = findBookDao.checkBookAvailability("It Ends With Us");
		
		BookCopy bookCopyOne = new BookCopyBuilder().setBookCopyId(1).setBookId(1).build();
		BookCopy bookCopyTwo = new BookCopyBuilder().setBookCopyId(2).setBookId(1).build();
		BookCopy bookCopyThree = new BookCopyBuilder().setBookCopyId(3).setBookId(1).build();
		
		BookStatus bookStatusAvailable = new BookStatusBuilder().setName("Available").build();
		BookStatus bookStatusUnavailable = new BookStatusBuilder().setName("Unavailable").build();

		for(Map.Entry<BookCopy, BookStatus> bookCopy : bookCopies.entrySet()) {
			
			if(bookCopy.getKey().getBookCopyId() == 1) {
				
				assertEquals(bookCopy.getKey().getBookId(), bookCopyOne.getBookId());
				assertEquals(bookCopy.getValue().getName(), bookStatusAvailable.getName());
			}
			else if(bookCopy.getKey().getBookCopyId() == 2) {
				
				assertEquals(bookCopy.getKey().getBookId(), bookCopyTwo.getBookId());
				assertEquals(bookCopy.getValue().getName(), bookStatusUnavailable.getName());
			}
			else if(bookCopy.getKey().getBookCopyId() == 3) {
				
				assertEquals(bookCopy.getKey().getBookId(), bookCopyThree.getBookId());
				assertEquals(bookCopy.getValue().getName(), bookStatusUnavailable.getName());
			}
		}
			
	}

}
