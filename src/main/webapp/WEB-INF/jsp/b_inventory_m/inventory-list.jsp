<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="java.sun.com" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Inventory Management</title>
    <style>
        table { border-collapse: collapse; width: 80%; margin: 20px 0; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .action-link { margin-right: 10px; }
    </style>
</head>
<body>
    <h2>Book Inventory List</h2>

    <p><a href="addBookForm.jsp">Add New Book</a></p>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Available</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%-- Iterate over the list of books passed from the Servlet --%>
            <c:forEach var="book" items="${bookList}">
                <tr>
                    <td>${book.id}</td>
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td>${book.available ? 'Yes' : 'No'}</td>
                    <td>
                        <%-- Links to the Servlet actions, passing book ID as a parameter --%>
                        <a href="BookServlet?action=editForm&id=${book.id}" class="action-link">Edit</a>
                        <a href="BookServlet?action=delete&id=${book.id}" onclick="return confirm('Are you sure you want to delete this book?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
