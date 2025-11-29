package com.lms.web.service;

import java.util.HashMap;
import java.util.Map;

import com.lms.web.dao.FindBookDao;
import com.lms.web.model.Author;
import com.lms.web.model.Book;
import com.lms.web.model.BookCopy;
import com.lms.web.model.BookStatus;
import com.lms.web.model.Genre;

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
		return books;
	}
	
	public Map<Book, Author> browseByGenre(Genre genre) {
		return findBookDao.browseByGenre(genre.getName());
	}
	
	public Map<BookCopy, BookStatus> checkBookAvailability(Book book) {
		return findBookDao.checkBookAvailability(book.getTitle());
	}
}
