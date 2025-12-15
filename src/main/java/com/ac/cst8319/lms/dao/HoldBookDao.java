package com.ac.cst8319.lms.dao;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ac.cst8319.lms.model.Book;
import com.ac.cst8319.lms.model.BookBuilder;
import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookCopyBuilder;
import com.ac.cst8319.lms.model.BookStatus;
import com.ac.cst8319.lms.util.DatabaseConnection;
import com.ac.cst8319.lms.model.BookStatusBuilder;
import com.ac.cst8319.lms.model.User;
import com.ac.cst8319.lms.model.UserBuilder;
import com.ac.cst8319.lms.model.WaitList;
import com.ac.cst8319.lms.model.WaitListBuilder;

/**
 * Data access object interface for placing holds on books
 * @author Ashleigh Eagleson
 */
public class HoldBookDao {
	
	/**
	 * Checking the availability of a book copy
	 * @param bookCopyId the book copy 
	 * @return the availability of a book copy
	 */
	public Map<BookCopy, BookStatus> checkPlaceHoldOnBook(Long bookCopyId) {
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String sql = "SELECT * FROM BookCopy INNER JOIN BookStatus ON BookCopy.StatusID = BookStatus.BookStatusID WHERE bookCopyID = ?";
			PreparedStatement pstatement = connection.prepareStatement(sql);
			
			pstatement.setLong(1, bookCopyId);
			ResultSet rs = pstatement.executeQuery();
			Map<BookCopy, BookStatus> bookCopyStatus = new HashMap<>();
			
			while (rs.next()){
				BookCopy bookCopy = new BookCopyBuilder().setBookCopyId(rs.getLong("BookCopyID")).setBookId(rs.getLong("BookID")).setStatusId(rs.getInt("StatusID")).setLocation(rs.getString("Location")).build();
				BookStatus bookStatus = new BookStatusBuilder().setStatusId(rs.getInt("BookStatusID")).setName(rs.getString("Name")).setDescription(rs.getString("Description")).build();
				bookCopyStatus.put(bookCopy, bookStatus);
			}
			DatabaseConnection.closeConnection(connection);
			return bookCopyStatus;
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Updating a hold was placed on an available book
	 * @param bookCopyId the book copy with a hold placed on it
	 * @return the number of rows affected after updating the book's availability to unavailable
	 */
	public int holdOnAvailableBook(Long bookCopyId) {
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String sql = "UPDATE BookCopy SET StatusID = ? WHERE BookCopyId = ?";
			PreparedStatement pstatement = connection.prepareStatement(sql);
			
			pstatement.setInt(1, 2);
			pstatement.setLong(2, bookCopyId);
			int result = pstatement.executeUpdate();
			
			DatabaseConnection.closeConnection(connection);
			return result;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Updating a hold was placed on an unavailable book
	 * @param bookId the book with a hold placed on it by adding an user to its wait list
	 * @param userId the user that is placing a hold on a book and added to its wait list
	 * @param nextPosition the next position in the wait list where the user is inserted
	 * @return the number of rows affected after inserting a new user into a book's wait list
	 */
	public int holdOnUnavailableBook(Long bookId, Long userId, int nextPosition) {
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String sql = "INSERT INTO Waitlist (BookID, UserID, Position) VALUES (?, ?, ?)";
			PreparedStatement pstatement = connection.prepareStatement(sql);
			
			pstatement.setLong(1, bookId);
			pstatement.setLong(2, userId);
			pstatement.setInt(3, nextPosition);
			int result = pstatement.executeUpdate();
			
			DatabaseConnection.closeConnection(connection);
			return result;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Returning the book details depending on a book's title
	 * @param book the book that has its details returned
	 * @return the book including all of its details 
	 */
	public Book returnBookDetails(Book book) {
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String sql = "SELECT * FROM Book WHERE Title = ?";
			PreparedStatement pstatement = connection.prepareStatement(sql);
			
			pstatement.setString(1, book.getTitle());
			ResultSet rs = pstatement.executeQuery();
			
			Book returnedBook = null;
			while(rs.next()) {
				returnedBook = new BookBuilder().setBookId(rs.getLong("BookID")).setIsbn(rs.getString("ISBN")).setTitle(rs.getString("Title")).setAuthorId(rs.getLong("AuthorID")).setGenreId(rs.getInt("GenreID")).setPublisher(rs.getString("Publisher")).setDescription(rs.getString("Description")).build();
			}
			DatabaseConnection.closeConnection(connection);
			return returnedBook;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Determining the next position in a book's wait list
	 * @param bookId the book that has its wait list checked
	 * @return the next position in a book's wait list
	 */
	public int waitListNextPosition(Long bookId) {
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String sql = "SELECT position FROM WaitList WHERE BookID = ? ORDER BY WaitlistID DESC LIMIT ?";
			PreparedStatement pstatement = connection.prepareStatement(sql);
			
			int lastRow = 1;
			pstatement.setLong(1, bookId);
			pstatement.setInt(2, lastRow);
			
			ResultSet rs = pstatement.executeQuery();
			int lastPosition = 0;
			
			while(rs.next()) {
				lastPosition = rs.getInt("Position");
			}
			DatabaseConnection.closeConnection(connection);
			return lastPosition;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	/**
	 * Updating a book's wait list by moving all the users up one position
	 * @param bookId the book that has its wait list updated
	 * @return the number of updates to move the positions up one for all users in a book's wait list
	 */
	public int waitListUserUpdate(Long bookId) {
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String sql = "UPDATE WaitList SET Position = ? WHERE UserID = ? ORDER BY Position ASC";
			PreparedStatement pstatement = connection.prepareStatement(sql);
			
			Map<User, WaitList> waitListUsers = returnWaitListUsers(bookId);
			
			int count = 0;
			for (Map.Entry<User, WaitList> waitlistUser : waitListUsers.entrySet()) {
				
				if(waitlistUser.getValue().getPosition() > 1) {
					int nextPosition = waitlistUser.getValue().getPosition() - 1;
					
					pstatement.setInt(1, nextPosition);
					pstatement.setLong(2, waitlistUser.getKey().getUserId());
					int result = pstatement.executeUpdate();
					
					if(result == 1) {
						count++;
					}
				}
			}
			DatabaseConnection.closeConnection(connection);
			return count;
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Removing the next user in a book's wait list because book is available to be borrowed immediately
	 * @param bookId the book's wait list that is updated by removing the first user in a book's wait list
	 * @return the number of rows affected by deleting the user from a book's wait list
	 */
	public int removeNextUserWaitlist(Long bookId) {
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String sql = "DELETE FROM WaitList WHERE UserID = ?";
			PreparedStatement pstatement = connection.prepareStatement(sql);
			
			Map<User, WaitList> waitListUsers = returnWaitListUsers(bookId);
			int result = 0;
			
			for(Map.Entry<User, WaitList> waitlistUser : waitListUsers.entrySet()) {
				
				if(waitlistUser.getValue().getPosition() == 1) {
					pstatement.setLong(1, waitlistUser.getKey().getUserId());
					result = pstatement.executeUpdate();
				}
			}
			return result;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Finding all the users in a book's wait list
	 * @param bookId the book that has its wait list accessed
	 * @return all the users in a book's wait list
	 */
	public Map<User, WaitList> returnWaitListUsers(Long bookId){
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String sql = "SELECT * FROM UserAccount INNER JOIN WaitList ON UserAccount.UserID = WaitList.UserID WHERE WaitList.BookID = ?";
			PreparedStatement pstatement = connection.prepareStatement(sql);
			pstatement.setLong(1, bookId);
			
			ResultSet rs = pstatement.executeQuery();
			var waitListUsers = new HashMap<User, WaitList>();
			
			while(rs.next()) {
				User user = new UserBuilder().setUserId(rs.getLong("UserID")).setEmail(rs.getString("Email")).setFirstName(rs.getString("FirstName")).setLastName(rs.getString("LastName")).setPhone(rs.getString("Phone")).setAddress(rs.getString("Address")).setRoleId(rs.getInt("RoleID")).build();
				WaitList waitList = new WaitListBuilder().setWaitListId(rs.getLong("WaitListID")).setBookId(rs.getLong("BookID")).setUserId(rs.getLong("UserID")).setPosition(rs.getInt("Position")).build();
				waitListUsers.put(user, waitList);
			}
			DatabaseConnection.closeConnection(connection);
			return waitListUsers;
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Sending email to user who placed a hold on an available book
	 * @param user the user who placed a hold on a book
	 * @param book the book that has a hold on it
	 * @return the user who placed a hold and the book with a hold on it
	 */
	public Map<User, Book> returnEmailDetails(User user, Book book) {
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String userSql = "SELECT * FROM UserAccount WHERE UserID = ?";
			PreparedStatement userPstatement = connection.prepareStatement(userSql);
			
			userPstatement.setLong(1, user.getUserId());
			ResultSet userRs = userPstatement.executeQuery();
			
			while(userRs.next()) {
				user = new UserBuilder().setEmail(userRs.getString("Email")).setFirstName(userRs.getString("FirstName")).setLastName(userRs.getString("LastName")).setPhone(userRs.getString("Phone")).setAddress(userRs.getString("Address")).build();
			}
			
			String bookSql = "SELECT * FROM Book WHERE Title = ?";
			PreparedStatement bookPstatement = connection.prepareStatement(bookSql);
			
			bookPstatement.setString(1, book.getTitle());
			ResultSet bookRs = bookPstatement.executeQuery();
			
			while(bookRs.next()) {
				book = new BookBuilder().setBookId(bookRs.getLong("BookID")).setIsbn(bookRs.getString("ISBN")).setTitle(bookRs.getString("Title")).setAuthorId(bookRs.getLong("AuthorId")).setDescription(bookRs.getString("Description")).build();
			}
			
			var userBook = new HashMap<User, Book>();
			userBook.put(user, book);
			
			DatabaseConnection.closeConnection(connection);
			return userBook;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
