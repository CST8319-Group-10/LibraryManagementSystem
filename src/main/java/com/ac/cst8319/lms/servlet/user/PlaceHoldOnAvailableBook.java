package com.ac.cst8319.lms.servlet.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookBuilder;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookCopyBuilder;
import com.ac.cst8319.lms.model.BookStatus;
import com.ac.cst8319.lms.model.BookStatusBuilder;
import com.ac.cst8319.lms.model.User;
import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.model.UserBuilder;
import com.ac.cst8319.lms.model.WaitList;
import com.ac.cst8319.lms.service.HoldBookService;
import com.ac.cst8319.lms.util.SessionUtil;

/**
 * Servlet implementation class PlaceHoldOnAvailableBook
 * @author Ashleigh Eagleson
 */
@WebServlet(name = "PlaceHoldOnAvailableBook", urlPatterns = "/user/placeHoldOnAvailableBook")
public class PlaceHoldOnAvailableBook extends HttpServlet {

	/**
	 * Handling HTTP requests to place a hold on an available book
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession(false);
		UserAccount currentUser = SessionUtil.getCurrentUser(session);
		
		if(currentUser == null){
			response.sendRedirect(request.getContextPath() + "/login");
		}
		
		long userId = SessionUtil.getCurrentUserId(session);
		User user = new UserBuilder().setUserId(userId).build();
		
		String bookTitle = request.getParameter("bookTitle");
		Book book = new BookBuilder().setTitle(bookTitle).build();
		
		Long bookCopyId = Long.parseLong(request.getParameter("bookCopyId"));
		String bookCopyLocation = request.getParameter("location");
		BookCopy bookCopy = new BookCopyBuilder().setBookCopyId(bookCopyId).setLocation(bookCopyLocation).build();
		
		String bookStatusValue = request.getParameter("bookStatus");
		BookStatus currentBookStatus = new BookStatusBuilder().setName(bookStatusValue).build();
		
		BookStatus updatedBookStatus = null;
		String message = "";
		try {
			HoldBookService holdBookService = new HoldBookService();
			updatedBookStatus = holdBookService.placeHoldOnAvailableBook(bookCopy, currentBookStatus, user, book);
			message = "Placing a hold on the book was successful.";
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
			message = "A hold couldn't be placed on the book.";
		}	
		
		request.setAttribute("bookCopy", bookCopy);
		request.setAttribute("updatedBookStatus", updatedBookStatus);
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/jsp/user/HoldOnAvailableBook.jsp").forward(request, response);
	}

}
