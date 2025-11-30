package com.ac.cst8319.lms.dao;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.model.BookStatus;
import com.ac.cst8319.lms.util.DatabaseConnection;

public class HoldBookDao {
	
	public int holdOnBook(Long bookCopyId) {
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			//Always updates books that were available to unavailable
			String sql = "UPDATE bookcopy SET StatusID = ? WHERE bookCopyId = ?";
			PreparedStatement pstatement = connection.prepareStatement(sql);
			
			pstatement.setInt(1, 2);
			pstatement.setLong(2, bookCopyId);
			int result = pstatement.executeUpdate();
			
			DatabaseConnection.closeConnection(connection);
			return result;
		}
		catch (SQLException e) {
			e.printStackTrace();
			//No rows were updated
			return 0;
		}
		
	}
	
	public Map<BookCopy, BookStatus> checkPlaceHoldOnBook(Long bookCopyId) {
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			String sql = "SELECT * FROM bookcopy INNER JOIN bookstatus ON bookcopy.StatusID = bookstatus.BookStatusID WHERE bookCopyID = ?";
			PreparedStatement pstatement = connection.prepareStatement(sql);
			
			pstatement.setLong(1, bookCopyId);
			ResultSet rs = pstatement.executeQuery();
			Map<BookCopy, BookStatus> bookCopyStatus = new HashMap();
			
			while (rs.next()){
				BookCopy bookCopy = new BookCopy();
				bookCopy.setBookCopyId(rs.getLong("BookCopyID"));
				bookCopy.setBookId(rs.getLong("BookID"));
				bookCopy.setStatusId(rs.getInt("BookStatusID"));
				bookCopy.setLocation(rs.getString("Location"));
				
				BookStatus bookStatus = new BookStatus();
				bookStatus.setStatusId(rs.getInt("BookStatusID"));
				bookStatus.setName(rs.getString("Name"));
				bookStatus.setDescription(rs.getString("Description"));
				
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

}
