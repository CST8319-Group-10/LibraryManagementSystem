package com.ac.cst8319.lms.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookStatus;
import com.ac.cst8319.lms.service.HoldBookService;

/**
 * Servlet implementation class HoldOnBook
 */
@WebServlet("/placeHoldBook")
public class HoldOnBook extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Long bookCopyId = Long.parseLong(request.getParameter("bookCopyId"));
		String bookCopyLocation = request.getParameter("location");
		BookCopy bookCopy = new BookCopy();
		bookCopy.setBookCopyId(bookCopyId);
		bookCopy.setLocation(bookCopyLocation);
		
		//Retrieve the book's current status
		String bookStatusName = request.getParameter("bookStatus");
		BookStatus bookStatus = new BookStatus();
		bookStatus.setName(bookStatusName);
		
		//Place hold on book
		HoldBookService holdBookService = new HoldBookService();
		bookStatus = holdBookService.placeHoldBook(bookCopy, bookStatus);
		
		request.setAttribute("bookCopy", bookCopy);
		request.setAttribute("bookStatus", bookStatus);
		request.getRequestDispatcher("HoldOnBook.jsp").forward(request, response);
	}

}
