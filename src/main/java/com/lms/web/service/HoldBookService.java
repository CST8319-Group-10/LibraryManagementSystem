package com.lms.web.service;

import com.lms.web.dao.HoldBookDao;
import com.lms.web.model.BookCopy;
import com.lms.web.model.BookStatus;

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
