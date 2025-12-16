package com.ac.cst8319.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ac.cst8319.lms.util.DatabaseConnection;
import com.ac.cst8319.lms.model.Author;
import com.ac.cst8319.lms.model.AuthorBuilder;
import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookBuilder;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookCopyBuilder;
import com.ac.cst8319.lms.model.BookStatus;
import com.ac.cst8319.lms.model.BookStatusBuilder;
import com.ac.cst8319.lms.model.Prototype.BookCopyClient;
import com.ac.cst8319.lms.model.Prototype.BookStatusClient;

/**
 * Data access object interface for searching or browsing for books
 * @author Ashleigh Eagleson
 */
public class FindBookDao {
	
	/**
	 * Finding books by author's name
	 * @param authorFirstName the first name of the author
	 * @param authorLastName the last name of the author
	 * @return the books and their authors that were found from searching for books by author
	 */
	public Map<Book, Author> searchByAuthor(String authorFirstName, String authorLastName) {
			
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String sql = "SELECT * FROM book INNER JOIN author ON book.AuthorID = author.AuthorID WHERE FirstName = ? AND LastName = ?";	
			PreparedStatement pstatement = connection.prepareStatement(sql);
			pstatement.setString(1, authorFirstName);
			pstatement.setString(2, authorLastName);
			
			ResultSet rs = pstatement.executeQuery();
			Map<Book, Author> books = new HashMap<>();
			
			while (rs.next()) {
				Book book = new BookBuilder().setBookId(rs.getLong("BookID")).setIsbn(rs.getString("ISBN")).setTitle(rs.getString("Title")).setAuthorId(rs.getLong("AuthorID")).setGenreId(rs.getInt("GenreID")).setPublisher(rs.getString("Publisher")).setPublicationYear(rs.getString("PublicationYear")).setDescription(rs.getString("Description")).build();
				Author author = new AuthorBuilder().setAuthorId(rs.getLong("AuthorID")).setFirstName(rs.getString("FirstName")).setLastName(rs.getString("LastName")).build();
				books.put(book, author);
			}
			DatabaseConnection.closeConnection(connection);
			return books;
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Finding books by title
	 * @param bookTitle the title of the book
	 * @return the books and their authors that were found by searching for books by title
	 */
	public Map<Book, Author> searchByTitle(String bookTitle) {
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String sql = "SELECT * FROM book INNER JOIN author ON book.AuthorID = author.AuthorID WHERE Title = ?";
			PreparedStatement pstatement = connection.prepareStatement(sql);
			pstatement.setString(1, bookTitle);
			
			ResultSet rs = pstatement.executeQuery();
			Map<Book, Author> books = new HashMap<>();
			
			while (rs.next()) {
				Book book = new BookBuilder().setBookId(rs.getLong("BookID")).setIsbn(rs.getString("ISBN")).setTitle(rs.getString("Title")).setAuthorId(rs.getLong("AuthorID")).setGenreId(rs.getInt("GenreID")).setPublisher(rs.getString("Publisher")).setPublicationYear(rs.getString("PublicationYear")).setDescription(rs.getString("Description")).build();
				Author author = new AuthorBuilder().setAuthorId(rs.getLong("AuthorID")).setFirstName(rs.getString("FirstName")).setLastName(rs.getString("LastName")).build();
				books.put(book, author);
			}
			DatabaseConnection.closeConnection(connection);
			return books;
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Finding books by genre
	 * @param genre the genre that is specified
	 * @return the books and their authors that were found by browsing through books by genre
	 */
	public Map<Book, Author> browseByGenre(String genre){
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String sql = "SELECT * FROM book INNER JOIN genre ON book.GenreID = genre.GenreID INNER JOIN author ON book.AuthorID = author.AuthorID WHERE Name = ?";
			PreparedStatement pstatement = connection.prepareStatement(sql);
			pstatement.setString(1, genre);
			
			ResultSet rs = pstatement.executeQuery();
			Map<Book, Author> books = new HashMap<>();
			
			while (rs.next()) {
				Book book = new BookBuilder().setBookId(rs.getLong("BookID")).setIsbn(rs.getString("ISBN")).setTitle(rs.getString("Title")).setAuthorId(rs.getLong("AuthorID")).setGenreId(rs.getInt("GenreID")).setPublisher(rs.getString("Publisher")).setPublicationYear(rs.getString("PublicationYear")).setDescription(rs.getString("Description")).build();
				Author author = new AuthorBuilder().setAuthorId(rs.getLong("AuthorID")).setFirstName(rs.getString("FirstName")).setLastName(rs.getString("LastName")).build();
				books.put(book, author);
			}
			DatabaseConnection.closeConnection(connection);
			return books;
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Checking availability of book copies by cloning prototypes
	 * @param bookTitle the book that has its availability checked
	 * @return the book copies to check a book's availability
	 */
	public Map<BookCopy, BookStatus> checkBookAvailability(String bookTitle) {
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String sql = "SELECT * FROM book INNER JOIN bookcopy ON book.BookID = bookcopy.BookID INNER JOIN bookstatus ON bookcopy.StatusID = bookstatus.BookStatusID WHERE Title = ?";
			PreparedStatement pstatement = connection.prepareStatement(sql);
			pstatement.setString(1, bookTitle);
			
			ResultSet rs = pstatement.executeQuery();
			Map<BookCopy, BookStatus> bookCopies = new HashMap<>();
			
			int countAvailable = 0;
			int countUnavailable = 0;
			
			BookCopy bookCopyPrototypeAvailable = null;
			BookCopy bookCopyPrototypeUnavailable = null;
			BookCopy bookCopyClone = null;
			
			BookStatus bookStatusPrototypeAvailable = null;
			BookStatus bookStatusPrototypeUnavailable = null;
			BookStatus bookStatusClone = null;
			while (rs.next()) {
				
				if(rs.getInt("StatusID") == 1 || rs.getInt("BookStatusID") == 1) {
					
					if(countAvailable == 0) {
						bookCopyPrototypeAvailable = new BookCopyBuilder().setBookCopyId(rs.getLong("BookCopyID")).setBookId(rs.getLong("BookID")).setStatusId(rs.getInt("StatusID")).setLocation(rs.getString("Location")).build();
						bookStatusPrototypeAvailable = new BookStatusBuilder().setStatusId(rs.getInt("BookStatusID")).setName(rs.getString("Name")).setDescription(rs.getString("Description")).build();
						
						bookCopies.put(bookCopyPrototypeAvailable, bookStatusPrototypeAvailable);
					}
					else if(countAvailable >= 1) {
						BookCopyClient bookCopyClient = new BookCopyClient(bookCopyPrototypeAvailable);
						bookCopyClone = (BookCopy) bookCopyClient.createBookCopy();
						bookCopyClone.setBookCopyId(rs.getLong("BookCopyID"));
						bookCopyClone.setLocation(rs.getString("Location"));
						
						BookStatusClient bookStatusClient = new BookStatusClient(bookStatusPrototypeAvailable);
						bookStatusClone = bookStatusClient.createBookStatus();
						
						bookCopies.put(bookCopyClone, bookStatusClone);
					}
					countAvailable++;
				}
				else if(rs.getInt("StatusID") == 2 || rs.getInt("BookStatusID") == 2) {
					
					if(countUnavailable == 0) {
						bookCopyPrototypeUnavailable = new BookCopyBuilder().setBookCopyId(rs.getLong("BookCopyID")).setBookId(rs.getLong("BookID")).setStatusId(rs.getInt("StatusID")).setLocation(rs.getString("Location")).build();
						bookStatusPrototypeUnavailable = new BookStatusBuilder().setStatusId(rs.getInt("BookStatusID")).setName(rs.getString("Name")).setDescription(rs.getString("Description")).build();
						
						bookCopies.put(bookCopyPrototypeUnavailable, bookStatusPrototypeUnavailable);
					}
					else if(countUnavailable >= 1) {
						BookCopyClient bookCopyClient = new BookCopyClient(bookCopyPrototypeUnavailable);
						bookCopyClone = (BookCopy) bookCopyClient.createBookCopy();
						bookCopyClone.setBookCopyId(rs.getLong("BookCopyID"));
						bookCopyClone.setLocation(rs.getString("Location"));
						
						BookStatusClient bookStatusClient = new BookStatusClient(bookStatusPrototypeUnavailable);
						bookStatusClone = bookStatusClient.createBookStatus();
						
						bookCopies.put(bookCopyClone, bookStatusClone);
					}
					countUnavailable++;
				}
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
