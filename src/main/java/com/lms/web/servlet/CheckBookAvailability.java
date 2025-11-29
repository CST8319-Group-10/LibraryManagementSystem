package com.lms.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.lms.web.model.Book;
import com.lms.web.model.BookCopy;
import com.lms.web.model.BookStatus;
import com.lms.web.service.FindBookService;

/**
 * Servlet implementation class CheckBookAvailability
 */
@WebServlet("/checkBookAvailability")
public class CheckBookAvailability extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String bookTitle = request.getParameter("bookTitle");
		Book book = new Book();
		book.setTitle(bookTitle);
		
		//Check book availability
		FindBookService findBookService = new FindBookService();
		Map<BookCopy, BookStatus> bookCopies = new HashMap();
		bookCopies = findBookService.checkBookAvailability(book);
		
		request.setAttribute("bookTitle", book.getTitle());
		request.setAttribute("bookCopies", bookCopies);
		request.getRequestDispatcher("CheckBookAvailability.jsp").forward(request, response);
	}
}
