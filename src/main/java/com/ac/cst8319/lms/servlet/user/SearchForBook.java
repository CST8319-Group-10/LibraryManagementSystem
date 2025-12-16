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

import com.ac.cst8319.lms.model.Author;
import com.ac.cst8319.lms.model.AuthorBuilder;
import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookBuilder;
import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.service.FindBookService;
import com.ac.cst8319.lms.util.SessionUtil;

/**
 * Servlet implementation class SearchForBook
 * @author Ashleigh Eagleson
 */
@WebServlet(name = "SearchForBook", urlPatterns = "/user/searchForBook")
public class SearchForBook extends HttpServlet {
	
	/**
	 * Handling HTTP requests to search for books by title or author
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		UserAccount currentUser = SessionUtil.getCurrentUser(session);
		
		if(currentUser == null){
			response.sendRedirect(request.getContextPath() + "/login");
		}
		
		String action = request.getParameter("action");
		if(action != null) {
			if(action.equals("access")) {
				request.getRequestDispatcher("/WEB-INF/jsp/user/SearchForBook.jsp").forward(request, response);
			}
		}
		
		String bookTitle = request.getParameter("bookTitle");
		String bookAuthor = request.getParameter("bookAuthor");
		
		Book book = null;
		Author author = null;
		String searchValue = "";
		
		if(bookTitle != null) {
			book = new BookBuilder().setTitle(bookTitle).build();
			searchValue = book.getTitle();
		}
		
		else if (bookAuthor != null) {
			String[] names = bookAuthor.split(" ");
			author = new AuthorBuilder().setFirstName(names[0]).setLastName(names[1]).build();
			searchValue = author.getFirstName() + " " + author.getLastName();
		}
		
		Map<Book, Author> books = new HashMap();
		String message = "";
		
		try {
			FindBookService findBookService = new FindBookService();
			books = findBookService.searchForBook(book, author);
			message = "The search for " + searchValue + " was successful and a book was found.";
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
			message = "No books could be found from the search of " + searchValue + " that was entered.";
		}
		
		request.setAttribute("books", books);
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/jsp/user/SearchForBook.jsp").forward(request, response);	
	}
}
