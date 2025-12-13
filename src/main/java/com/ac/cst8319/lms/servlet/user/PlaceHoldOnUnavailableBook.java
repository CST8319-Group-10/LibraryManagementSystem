package com.ac.cst8319.lms.servlet.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookBuilder;
import com.ac.cst8319.lms.model.User;
import com.ac.cst8319.lms.model.UserBuilder;
import com.ac.cst8319.lms.model.WaitList;
import com.ac.cst8319.lms.service.HoldBookService;

/**
 * Servlet implementation class PlaceHoldOnUnavailableBook
 * @author Ashleigh Eagleson
 */
@WebServlet(name = "PlaceHoldOnUnavailableBook", urlPatterns = "/user/PlaceHoldOnUnavailableBook")
public class PlaceHoldOnUnavailableBook extends HttpServlet {

	/**
	 * Handling HTTP requests to place a hold on an unavailable book
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		HTTPSession session = request.getSession(false);
		UserAccount currentUser = SessionUtil.getCurrentUser(session);
		
		if(currentUser == null){
			response.sendRedirect(request.getContextPath() + "/login");
		}
		 */
		
		//int userId = session.getAttribute(USER_ID_KEY);
		User user = new UserBuilder().setUserId(2).build();
		
		String bookTitle = request.getParameter("bookTitle");
		Book book = new BookBuilder().setTitle(bookTitle).build();
		
		WaitList userWaitList = null;
		String message = "";
		try {
			HoldBookService holdBookService = new HoldBookService();
			userWaitList = holdBookService.placeHoldOnUnavailableBook(book, user);
			
			if(userWaitList != null) {
				message = "Placing a hold on the book and joining its waitlist was successful.";
			}
			else {
				message = "Couldn't place a hold on the book nor join its waitlist.";
			}
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("waitlist", userWaitList);
		request.setAttribute("book", book);
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/jsp/user/HoldOnUnavailableBook.jsp").forward(request, response);
	}

}
