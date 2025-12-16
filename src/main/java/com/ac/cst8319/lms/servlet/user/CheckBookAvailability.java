package com.ac.cst8319.lms.servlet.user;

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
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookStatus;
import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.FindBookService;
import com.ac.cst8319.lms.util.SessionUtil;

/**
 * Servlet implementation class CheckBookAvailability
 * @author Ashleigh Eagleson
 */
@WebServlet(name = "CheckBookAvailability", urlPatterns = "/user/checkBookAvailability")
public class CheckBookAvailability extends HttpServlet {
       
	/**
	 * Handling HTTP requests to check a book's availability
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		UserAccount currentUser = SessionUtil.getCurrentUser(session);
		
		if(currentUser == null){
			response.sendRedirect(request.getContextPath() + "/login");
		}
		
		String bookTitle = request.getParameter("bookTitle");
		Book book = new BookBuilder().setTitle(bookTitle).build();	
		Map<BookCopy, BookStatus> bookCopies = null;
		String message = "";
		
		try {
			FindBookService findBookService = new FindBookService();
			bookCopies = findBookService.checkBookAvailability(book);
			message = "The book copies were found successfully.";
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
			message = "The book copies couldn't be found.";
		}
		
		request.setAttribute("bookTitle", book.getTitle());
		request.setAttribute("bookCopies", bookCopies);
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/jsp/user/CheckBookAvailability.jsp").forward(request, response);
	}
}
