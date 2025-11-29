package com.ac.cst8319.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ac.cst8319.lms.model.Author;
import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookStatus;
import com.ac.cst8319.lms.util.DatabaseConnection;

public class FindBookDao {
	
	public Map<Book, Author> searchByAuthor(String authorFirstName, String authorLastName) {
			
		try {
			Connection connection = DatabaseConnection.getConnection();
		
			String sql = "SELECT * FROM book INNER JOIN author ON book.AuthorID = author.AuthorID WHERE FirstName = ? AND LastName = ?";
			
			PreparedStatement pstatement = connection.prepareStatement(sql);
			pstatement.setString(1, authorFirstName);
			pstatement.setString(2, authorLastName);
			
			System.out.println(authorFirstName + authorLastName);
			
			ResultSet rs = pstatement.executeQuery();
			Map<Book, Author> books = new HashMap();
			
			//Iterate through result set to retrieve all searched for books
			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getLong("BookID"));
				book.setIsbn(rs.getString("ISBN"));
				book.setTitle(rs.getString("Title"));
				book.setAuthorId(rs.getLong("AuthorID"));
				book.setGenreId(rs.getInt("GenreID"));
				book.setPublisher(rs.getString("Publisher"));
				book.setPublicationYear(rs.getString("PublicationYear"));
				book.setDescription(rs.getString("Description"));
				
				Author author = new Author();
				author.setAuthorId(rs.getLong("AuthorID"));
				author.setFirstName(rs.getString("FirstName"));
				author.setLastName(rs.getString("LastName"));
			
				books.put(book, author);
			}
			DatabaseConnection.closeConnection(connection);
			return books;
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			//No books were found
			return null;
		}
	}
	
	public Map<Book, Author> searchByTitle(String bookTitle) {
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String sql = "SELECT * FROM book INNER JOIN author ON book.AuthorID = author.AuthorID WHERE Title = ?";
			
			PreparedStatement pstatement = connection.prepareStatement(sql);
			pstatement.setString(1, bookTitle);
			
			ResultSet rs = pstatement.executeQuery();
			Map<Book, Author> books = new HashMap();
			
			//Iterate through result set to retrieve all searched for books
			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getLong("BookID"));
				book.setIsbn(rs.getString("ISBN"));
				book.setTitle(rs.getString("Title"));
				book.setAuthorId(rs.getLong("AuthorID"));
				book.setGenreId(rs.getInt("GenreID"));
				book.setPublisher(rs.getString("Publisher"));
				book.setPublicationYear(rs.getString("PublicationYear"));
				book.setDescription(rs.getString("Description"));
				
				Author author = new Author();
				author.setAuthorId(rs.getLong("AuthorID"));
				author.setFirstName(rs.getString("FirstName"));
				author.setLastName(rs.getString("LastName"));
			
				books.put(book, author);
			}
			DatabaseConnection.closeConnection(connection);
			return books;
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			//No books were found
			return null;
		}
	}
	
	public Map<Book, Author> browseByGenre(String genre){
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String sql = "SELECT * FROM book INNER JOIN genre ON book.GenreID = genre.GenreID INNER JOIN author ON book.AuthorID = author.AuthorID WHERE Name = ?";
			
			PreparedStatement pstatement = connection.prepareStatement(sql);
			pstatement.setString(1, genre);
			
			ResultSet rs = pstatement.executeQuery();
			Map<Book, Author> books = new HashMap();

			//Iterate through result set to browse for books in a genre
			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getLong("BookID"));
				book.setIsbn(rs.getString("ISBN"));
				book.setTitle(rs.getString("Title"));
				book.setAuthorId(rs.getLong("AuthorID"));
				book.setGenreId(rs.getInt("GenreID"));
				book.setPublisher(rs.getString("Publisher"));
				book.setPublicationYear(rs.getString("PublicationYear"));
				book.setDescription(rs.getString("Description"));
				
				Author author = new Author();
				author.setAuthorId(rs.getLong("AuthorID"));
				author.setFirstName(rs.getString("FirstName"));
				author.setLastName(rs.getString("LastName"));
			
				books.put(book, author);
			}
			DatabaseConnection.closeConnection(connection);
			return books;
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			//No books were found
			return null;
		}
	}
	
	public Map<BookCopy, BookStatus> checkBookAvailability(String bookTitle) {
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String sql = "SELECT * FROM book INNER JOIN bookcopy ON book.BookID = bookcopy.BookID INNER JOIN bookstatus ON bookcopy.StatusID = bookstatus.BookStatusID WHERE Title = ?";
			
			PreparedStatement pstatement = connection.prepareStatement(sql);
			pstatement.setString(1, bookTitle);
			
			ResultSet rs = pstatement.executeQuery();
			Map<BookCopy, BookStatus> bookCopies = new HashMap();
			
			//Iterate through result set to retrieve all book copies
			while (rs.next()) {
				BookCopy bookCopy = new BookCopy();
				bookCopy.setBookCopyId(rs.getLong("BookCopyID"));
				bookCopy.setBookId(rs.getLong("BookID"));
				bookCopy.setStatusId(rs.getInt("BookStatusID"));
				bookCopy.setLocation(rs.getString("Location"));
				
				BookStatus bookStatus = new BookStatus();
				bookStatus.setStatusId(rs.getInt("BookStatusID"));
				bookStatus.setName(rs.getString("Name"));
				bookStatus.setDescription(rs.getString("Description"));
				
				bookCopies.put(bookCopy, bookStatus);
			}
			DatabaseConnection.closeConnection(connection);
			return bookCopies;
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			//No book copies were found
			return null;
		}
		
	}

}
