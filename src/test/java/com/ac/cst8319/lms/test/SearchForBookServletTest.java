package com.ac.cst8319.lms.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ac.cst8319.lms.servlet.user.SearchForBook;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class SearchForBookServletTest {
	
	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpServletResponse response;
	
	@Mock
	private SearchForBook servlet;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		servlet = new SearchForBook();
	}
	
	@Test
	void testDoGetHttpServletRequestHttpServletResponse() {
		
		String action = "access";
		when(request.getParameter("action")).thenReturn(action);
		
		try {
			servlet.doGet(request, response);
			verify(request).getRequestDispatcher("/WEB-INF/jsp/user/SearchForBook.jsp").forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
