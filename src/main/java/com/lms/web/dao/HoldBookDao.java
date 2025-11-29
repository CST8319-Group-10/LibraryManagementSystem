package com.lms.web.dao;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;

import com.lms.web.model.BookCopy;

public class HoldBookDao {
	
	public int holdOnBook(Long bookCopyId) {
		
		try {
			Connection connection = DBConnection.createConnection();
			
			//Always updates books that were available to unavailable
			String sql = "UPDATE bookcopy SET StatusID = ? WHERE bookCopyId = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			
			pstmt.setInt(1, 2);
			pstmt.setLong(2, bookCopyId);
			int result = pstmt.executeUpdate();
			
			DBConnection.closeConnection(connection);
			return result;
		}
		catch (SQLException e) {
			e.printStackTrace();
			//No rows were updated
			return 0;
		}
		
	}

}
