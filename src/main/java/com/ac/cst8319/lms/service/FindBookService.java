package com.ac.cst8319.lms.service;

import java.util.HashMap;
import java.util.Map;

import com.ac.cst8319.lms.dao.FindBookDao;
import com.ac.cst8319.lms.model.Author;
import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookStatus;
import com.ac.cst8319.lms.model.Genre;

/**
 * Service for searching and browsing for books
 * @author Ashleigh Eagleson
 */
public class FindBookService {

	private FindBookDao findBookDao = new FindBookDao();
	
	/**
	 * Searching for book by title or author
	 * @param book the book title to search for a book
	 * @param author the author to search for a book
	 * @return the books that were returned from the search
	 * @throws IllegalArgumentException if the book title or author cannot be found
	 */
	public Map<Book, Author> searchForBook(Book book, Author author) throws IllegalArgumentException {
		
		Map<Book, Author> books = new HashMap();	
		
		if(book != null) {
			books = findBookDao.searchByTitle(book.getTitle());
		}
		else if(author != null){
			books = findBookDao.searchByAuthor(author.getFirstName(), author.getLastName());
		}
		
		if(books.size() == 0) {
			throw new IllegalArgumentException("The title or author couldn't be found.");
		}
		return books;
	}
	
	/**
	 * Browsing for books by genre
	 * @param genre the genre that is searched for
	 * @return the books that were returned to browse in a genre
	 * @throws IllegalArgumentException if the genre cannot be found
	 */
	public Map<Book, Author> browseByGenre(Genre genre) throws IllegalArgumentException {
		
		Map<Book, Author> books = findBookDao.browseByGenre(genre.getName());
		
		if(books.size() == 0) {
			throw new IllegalArgumentException("The genre couldn't be found.");
		}
		return books;
	}
	
	/**
	 * Checking availability of a book
	 * @param book the book that has its availability checked
	 * @return the availabilities for all the book copies
	 */
	public Map<BookCopy, BookStatus> checkBookAvailability(Book book) throws IllegalArgumentException {
		
		Map<BookCopy, BookStatus> bookCopies = findBookDao.checkBookAvailability(book.getTitle());
		
		if(bookCopies.size() == 0) {
			throw new IllegalArgumentException("Couldn't find book copies.");
		}
		return bookCopies;
	}
}
