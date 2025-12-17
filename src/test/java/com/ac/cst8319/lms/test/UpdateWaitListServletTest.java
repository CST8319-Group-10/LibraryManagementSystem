package com.ac.cst8319.lms.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ac.cst8319.lms.servlet.librarian.UpdateWaitList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class UpdateWaitListServletTest {
	
	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpServletResponse response;
	
	@Mock
	private UpdateWaitList servlet;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		servlet = new UpdateWaitList();
	}
	
	@Test
	void testDoGetHttpServletRequestHttpServletResponse() {
		
		String action = "access";
		when(request.getParameter("action")).thenReturn(action);
		
		try {
			servlet.doGet(request, response);
			verify(request).getRequestDispatcher("/WEB-INF/jsp/user/BookUpdateWaitList.jsp").forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
