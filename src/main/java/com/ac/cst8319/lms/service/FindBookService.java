package com.ac.cst8319.lms.service;

import java.util.HashMap;
import java.util.Map;

import com.ac.cst8319.lms.dao.FindBookDao;
import com.ac.cst8319.lms.model.Author;
import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookStatus;
import com.ac.cst8319.lms.model.Genre;

public class FindBookService {

	private FindBookDao findBookDao = new FindBookDao();
	
	public Map<Book, Author> searchForBook(Book book, Author author) {
		
		Map<Book, Author> books = new HashMap();	
		
		//Determine if searching by title or author to invoke DAO
		if(book.getTitle() != null) {
			books = findBookDao.searchByTitle(book.getTitle());
		}
		else if(author.getFirstName() != null && author.getLastName() != null){
			books = findBookDao.searchByAuthor(author.getFirstName(), author.getLastName());
		}
		
		if(books.size() == 0) {
			throw new IllegalArgumentException("The title or author couldn't be found.");
		}
		return books;
	}
	
	public Map<Book, Author> browseByGenre(Genre genre) {
		
		Map<Book, Author> books = findBookDao.browseByGenre(genre.getName());
		
		if(books.size() == 0) {
			throw new IllegalArgumentException("The genre is invalid.");
		}
		return books;
	}
	
	public Map<BookCopy, BookStatus> checkBookAvailability(Book book) {
		return findBookDao.checkBookAvailability(book.getTitle());
	}
}
