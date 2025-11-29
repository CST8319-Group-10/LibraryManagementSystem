package com.ac.cst8319.lms.dao;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;

import com.ac.cst8319.lms.model.BookCopy;
import com.ac.cst8319.lms.util.DatabaseConnection;

public class HoldBookDao {
	
	public int holdOnBook(Long bookCopyId) {
		
		try {
			Connection connection = DatabaseConnection.getConnection();
			
			//Always updates books that were available to unavailable
			String sql = "UPDATE bookcopy SET StatusID = ? WHERE bookCopyId = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			
			pstmt.setInt(1, 2);
			pstmt.setLong(2, bookCopyId);
			int result = pstmt.executeUpdate();
			
			DatabaseConnection.closeConnection(connection);
			return result;
		}
		catch (SQLException e) {
			e.printStackTrace();
			//No rows were updated
			return 0;
		}
		
	}

}
