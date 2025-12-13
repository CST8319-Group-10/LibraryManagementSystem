package com.ac.cst8319.lms.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookCopyBuilder;
import com.ac.cst8319.lms.model.BookStatus;
import com.ac.cst8319.lms.model.BookStatusBuilder;
import com.ac.cst8319.lms.servlet.user.HoldOnBook;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class HoldOnBookServletTest {

	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpServletResponse response;
	
	@Mock
	private HoldOnBook servlet;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		servlet = new HoldOnBook();
	}

	@Test
	void test() {
		
		BookCopy bookCopy = new BookCopyBuilder().setBookCopyId(3).build();
		BookStatus bookStatus = new BookStatusBuilder().setName("Available").build();
		
		String bookCopyId = Long.toString(bookCopy.getBookCopyId());
		when(request.getParameter("bookCopyId")).thenReturn(bookCopyId);
		
		try {
			servlet.doPost(request, response);
			
			verify(request).setAttribute("currentBookStatus", bookStatus.getName());
			verify(request).getRequestDispatcher("/user/placeHoldOnAvailableBook").forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
