package com.ac.cst8319.lms.service;

import com.ac.cst8319.lms.dao.HoldBookDao;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookStatus;

public class HoldBookService {

	private HoldBookDao holdBookDao = new HoldBookDao();
	
	public BookStatus placeHoldBook(BookCopy bookCopy, BookStatus bookStatus) {
		
		int result = holdBookDao.holdOnBook(bookCopy.getBookCopyId());

		//Determine if update was successful
		if(result == 1) {
			bookStatus.setName("Unavailable");
		}
		else if (result == 0){
			bookStatus.setName("Available");
		}
		
		return bookStatus;
	}
}
