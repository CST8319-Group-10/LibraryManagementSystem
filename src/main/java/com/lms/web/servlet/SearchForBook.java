package com.lms.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.lms.web.model.Author;
import com.lms.web.model.Book;
import com.lms.web.service.FindBookService;

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
		
		//Determine if searching by title or author to set an instance variable
		if(bookTitle != null) {
			book.setTitle(bookTitle);
		}
		else if (bookAuthor != null) {
			author.setFullName(bookAuthor);
		}
		
		//Search for book by title or author, as determined above
		Map<Book, Author> books = new HashMap();
		FindBookService findBookService = new FindBookService();
		books = findBookService.searchForBook(book, author);
		
		//All books found from search
		request.setAttribute("books", books);
		request.getRequestDispatcher("SearchForBook.jsp").forward(request, response);	
	}
}
