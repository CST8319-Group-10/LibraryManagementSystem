package com.ac.cst8319.lms.dao;

import com.ac.cst8319.lms.model.Book2;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ac.cst8319.lms.util.DatabaseConnection;

// This class handles all persistence logic for Book objects in MySQL
public class BookDAO2 {

    private static final String INSERT_BOOKS_SQL = "INSERT INTO books (ID, Title, Author, isAvailable) VALUES (?, ?, ?, ?);";
    private static final String SELECT_BOOK_BY_ID = "SELECT ID, Title, Author, isAvailable FROM books WHERE ID = ?;";
    private static final String SELECT_ALL_BOOKS = "SELECT * FROM books;";
    private static final String DELETE_BOOKS_SQL = "DELETE FROM books WHERE ID = ?;";
    private static final String UPDATE_BOOKS_SQL = "UPDATE books SET Title = ?, Author = ?, isAvailable = ? WHERE ID = ?;";

    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    // --- CRUD Operations ---

    public void insertBook(Book2 book) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOKS_SQL)) {
            preparedStatement.setInt(1, book.getID());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setBoolean(4, book.isAvailable());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateBook(Book2 book) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BOOKS_SQL)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setBoolean(3, book.isAvailable());
            statement.setInt(4, book.getID());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean deleteBook(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BOOKS_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public Book2 selectBook(int id) throws SQLException {
        Book2 book = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String title = rs.getString("Title");
                String author = rs.getString("Author");
                boolean isAvailable = rs.getBoolean("isAvailable");
                book = new Book2(id, title, author, isAvailable);
            }
        }
        return book;
    }

    public List<Book2> selectAllBooks() throws SQLException {
        List<Book2> books = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("Title");
                String author = rs.getString("Author");
                boolean isAvailable = rs.getBoolean("isAvailable");
                books.add(new Book2(id, title, author, isAvailable));
            }
        }
        return books;
    }
}