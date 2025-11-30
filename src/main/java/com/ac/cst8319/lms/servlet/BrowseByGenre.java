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
import com.ac.cst8319.lms.model.Genre;
import com.ac.cst8319.lms.service.FindBookService;

/**
 * Servlet implementation class BrowseByGenre
 */
@WebServlet("/browseByGenre")
public class BrowseByGenre extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String genreName = request.getParameter("genre");
		Genre genre = new Genre();
		genre.setName(genreName);
		
		Map<Book, Author> books = new HashMap();
		String message = "";
		
		try {
			//Browse for books in a genre
			FindBookService findBookService = new FindBookService();
			books = findBookService.browseByGenre(genre);
			message = "Browsing for books under the genre of " + genreName.toLowerCase() + " was successful.";
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
			message = "The genre of " + genreName.toLowerCase() + " couldn't be found and no books can be browsed.";
		}
		
		request.setAttribute("message", message);
		request.setAttribute("books", books);
		request.getRequestDispatcher("BrowseByGenre.jsp").forward(request, response);
	}
}
