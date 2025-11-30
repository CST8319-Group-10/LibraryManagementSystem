package com.ac.cst8319.lms.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ac.cst8319.lms.model.Author;
import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.service.FindBookService;

/**
 * Servlet implementation class SearchForBook
 */
@WebServlet("/searchForBook")
public class SearchForBook extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String bookTitle = request.getParameter("bookTitle");
		String bookAuthor = request.getParameter("bookAuthor");
		
		Book book = new Book();
		Author author = new Author();
		String searchValue = "";
		
		//Determine if searching by title or author to set an instance variable
		if(bookTitle != null) {
			book.setTitle(bookTitle);
			searchValue = book.getTitle();
		}
		else if (bookAuthor != null) {
			author.setFullName(bookAuthor);
			searchValue = author.getFirstName() + " " + author.getLastName();
		}
		
		Map<Book, Author> books = new HashMap();
		String message = "";
		
		try {
			//Search for book by title or author, as determined above
			FindBookService findBookService = new FindBookService();
			books = findBookService.searchForBook(book, author);
			message = "The search for " + searchValue.toLowerCase() + " was successful and a book was found.";
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
			message = "No books could be found from the search of " + searchValue.toLowerCase() + ".";
		}
		
		//All books found from search
		request.setAttribute("books", books);
		request.setAttribute("message", message);
		request.getRequestDispatcher("SearchForBook.jsp").forward(request, response);	
	}
}
