package com.ac.cst8319.lms.servlet.user;

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
import com.ac.cst8319.lms.model.Genre;
import com.ac.cst8319.lms.model.GenreBuilder;
import com.ac.cst8319.lms.service.FindBookService;

/**
 * Servlet implementation class BrowseByGenre
 * @author Ashleigh Eagleson
 */
@WebServlet(name = "BrowseByGenre", urlPatterns = "/user/browseByGenre")
public class BrowseByGenre extends HttpServlet {

	/**
	 * Handling HTTP requests to browse for books by genre
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		*HTTPSession session = request.getSession(false);
		*UserAccount currentUser = SessionUtil.getCurrentUser(session);
		
		if(currentUser == null){
			response.sendRedirect(request.getContextPath() + "/login");
		}
		*/
		
		String action = request.getParameter("action");
		
		if(action != null) {
			if(action.equals("access")) {
				request.getRequestDispatcher("/WEB-INF/jsp/user/BrowseByGenre.jsp").forward(request, response);
			}			
		}
		
		String genreName = request.getParameter("genre");
		Genre genre = new GenreBuilder().setName(genreName).build();
		
		Map<Book, Author> books = new HashMap();
		String message = "";
		
		try {
			FindBookService findBookService = new FindBookService();
			books = findBookService.browseByGenre(genre);
			message = "Browsing for books under the genre of " + genreName.toLowerCase() + " was successful.";
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
			message = "Couldn't find any books in the genre of " + genreName.toLowerCase() + " that was specified.";
		}
		
		request.setAttribute("message", message);
		request.setAttribute("books", books);
		request.getRequestDispatcher("/WEB-INF/jsp/user/BrowseByGenre.jsp").forward(request, response);
	}
}
