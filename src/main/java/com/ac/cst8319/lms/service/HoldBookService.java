package com.ac.cst8319.lms.service;

import java.util.HashMap;
import java.util.Map;

import com.ac.cst8319.lms.dao.HoldBookDao;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookStatus;

public class HoldBookService {

	private HoldBookDao holdBookDao = new HoldBookDao();
	
	public BookStatus placeHoldBook(BookCopy bookCopy, BookStatus bookStatus) {
		
		Map<BookCopy, BookStatus> bookCopyStatus = new HashMap();
		bookCopyStatus = holdBookDao.checkPlaceHoldOnBook(bookCopy.getBookCopyId());
		
		//Double check if book is available or unavailable to control placing hold on book
		if(bookCopyStatus.size() == 1) {
			bookCopy = bookCopyStatus.entrySet().iterator().next().getKey();
			bookStatus = bookCopyStatus.entrySet().iterator().next().getValue();
		}
		
		if(bookStatus.getName().equals("Available")) {

			//Place hold on the book
			int result = holdBookDao.holdOnBook(bookCopy.getBookCopyId());
			
			//Determine if update was successful
			if(result == 1) {
				bookStatus.setName("Unavailable");
			}
			else if (result == 0){
				bookStatus.setName("Available");
				throw new IllegalArgumentException("Book's availability couldn't be updated.");
			}
		}
		else if(bookStatus.getName().equals("Unavailable")) {
			throw new IllegalArgumentException("Book is unavailable");
		}
		return bookStatus;
	}
}
