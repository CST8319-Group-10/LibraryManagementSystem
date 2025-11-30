package com.ac.cst8319.lms.controller;

import com.ac.cst8319.lms.model.Book2;
import com.ac.cst8319.lms.model.BookCatalog;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

// Maps this servlet to the URL pattern "/BookServlet"
@WebServlet(name = "BookServlet", value = "/BookServlet")
public class BookServlet extends HttpServlet {
    // Use the BookCatalog as an in-memory "database"
    private BookCatalog catalogue = new BookCatalog();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list"; // Default action
        }

        switch (action) {
            case "list":
                listBooks(request, response);
                break;
            case "editForm":
                showEditForm(request, response);
                break;
            case "delete":
                deleteBook(request, response);
                break;
            default:
                listBooks(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            addBook(request, response);
        } else if ("edit".equals(action)) {
            updateBook(request, response);
        }
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book2> bookList = catalogue.getBooks();
        request.setAttribute("bookList", bookList);
        // Forward to the JSP page to display the list
        RequestDispatcher dispatcher = request.getRequestDispatcher("/inventoryList.jsp");
        dispatcher.forward(request, response);
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Simple parameter parsing
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        boolean isAvailable = Boolean.parseBoolean(request.getParameter("available"));

        Book2 newBook = new Book2(id, title, author, isAvailable);
        catalogue.addBook(newBook);
        response.sendRedirect("BookServlet?action=list"); // Redirect back to list view
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book2 existingBook = catalogue.findBookById(id);
        request.setAttribute("book", existingBook);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/editBookForm.jsp");
        dispatcher.forward(request, response);
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        boolean isAvailable = Boolean.parseBoolean(request.getParameter("available"));
        
        Book2 updatedBook = new Book2(id, title, author, isAvailable);
        catalogue.updateBook(updatedBook);
        response.sendRedirect("BookServlet?action=list");
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        catalogue.deleteBook(id);
        response.sendRedirect("BookServlet?action=list");
    }
}