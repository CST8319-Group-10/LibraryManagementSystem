package com.ac.cst8319.lms.servlet.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookCopyBuilder;
import com.ac.cst8319.lms.model.BookStatus;
import com.ac.cst8319.lms.model.BookStatusBuilder;
import com.ac.cst8319.lms.model.User;
import com.ac.cst8319.lms.model.WaitList;
import com.ac.cst8319.lms.service.HoldBookService;

/**
 * Servlet implementation class HoldOnBook
 * @author Ashleigh Eagleson
 */
@WebServlet(name = "HoldOnBook", urlPatterns = "/user/placeHoldOnBook")
public class HoldOnBook extends HttpServlet {

	/**
	 * Handling HTTP requests by determining whether placing hold on an available or unavailable book
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		/*
		HTTPSession session = request.getSession(false);
		UserAccount currentUser = SessionUtil.getCurrentUser(session);
		
		if(currentUser == null){
			response.sendRedirect(request.getContextPath() + "/login");
		}
		*/
		
		Long bookCopyId = Long.parseLong(request.getParameter("bookCopyId"));
		BookCopy bookCopy = new BookCopyBuilder().setBookCopyId(bookCopyId).build();
		
		try {
			HoldBookService holdBookService = new HoldBookService();
			BookStatus currentBookStatus = holdBookService.checkBookStatus(bookCopy);
			request.setAttribute("currentBookStatus", currentBookStatus.getName());
			
			if(currentBookStatus.getName().equals("Available")) {
				request.getRequestDispatcher("/user/placeHoldOnAvailableBook").forward(request, response);
				return;
			}
			else if(currentBookStatus.getName().equals("Unavailable")){
				request.getRequestDispatcher("/user/placeHoldOnUnavailableBook").forward(request, response);
				return;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
