package com.ac.cst8319.lms.servlet.librarian;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookBuilder;
import com.ac.cst8319.lms.model.User;
import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.model.WaitList;
import com.ac.cst8319.lms.service.HoldBookService;
import com.ac.cst8319.lms.util.SessionUtil;

/**
 * Servlet implementation class ReturnBookUpdateWaitlist
 * @author Ashleigh Eagleson
 */
@WebServlet(name = "UpdateWaitList", urlPatterns = "/librarian/returnBookUpdateWaitList")
public class UpdateWaitList extends HttpServlet {
	
	/**
	 * Handling HTTP requests to render a view for updating a book's wait list
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if(action != null) {
			if(action.equals("access")) {
				request.getRequestDispatcher("/WEB-INF/jsp/librarian/BookUpdateWaitList.jsp").forward(request, response);
			}
		}
	}
	
	/**
	 * Handling HTTP requests by updating a book's wait list and notifying all users
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		UserAccount currentUser = SessionUtil.getCurrentUser(session);
		
		if(currentUser == null){
			response.sendRedirect(request.getContextPath() + "/login");
		}
		
		String bookTitle = request.getParameter("bookTitle");
		Book book = new BookBuilder().setTitle(bookTitle).build();
		
		Map<User, WaitList> updatedWaitlistUsers = new HashMap();
		String message = "";
		
		try {
			HoldBookService holdBookService = new HoldBookService();
			holdBookService.subjectNotifyObservers(book);
			updatedWaitlistUsers = holdBookService.updateWaitList(book);
			message = "Updated the waitlist successfully.";
		}
		catch(Exception e) {
			e.printStackTrace();
			message = "Couldn't update the waitlist.";
		}
		
		request.setAttribute("updatedWaitlistUsers", updatedWaitlistUsers);
		request.setAttribute("book", book);
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/jsp/librarian/BookUpdateWaitList.jsp").forward(request, response);
	}

}
