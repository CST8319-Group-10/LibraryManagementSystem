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
import com.lms.web.model.Genre;
import com.lms.web.service.FindBookService;

/**
 * Servlet implementation class BrowseByGenre
 */
@WebServlet("/browseByGenre")
public class BrowseByGenre extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String genreName = request.getParameter("genre");
		Genre genre = new Genre();
		genre.setName(genreName);
		
		//Browse for books by genre
		FindBookService findBookService = new FindBookService();
		Map<Book, Author> books = new HashMap();
		books = findBookService.browseByGenre(genre);
		
		request.setAttribute("books", books);
		request.getRequestDispatcher("BrowseByGenre.jsp").forward(request, response);
	}
}
