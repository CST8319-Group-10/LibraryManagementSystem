<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Book Details</title>
</head>
<body>
    <h2>Edit Book Details</h2>
    <%-- The 'book' object is set in the BookServlet when handling the 'editForm' action --%>
    <form action="BookServlet" method="POST">
        <input type="hidden" name="action" value="edit"/>
        
        <%-- The ID is hidden because we are updating an existing record --%>
        <input type="hidden" name="id" value="${book.id}"/>

        <label for="title">Title:</label>
        <input type="text" id="title" name="title" value="${book.title}" required/><br/><br/>

        <label for="author">Author:</label>
        <input type="text" id="author" name="author" value="${book.author}" required/><br/><br/>

        <label for="available">Available:</label>
        <input type="checkbox" id="available" name="available" value="true" ${book.available ? 'checked' : ''}/><br/><br/>

        <input type="submit" value="Update Book"/>
        <a href="BookServlet?action=list">Cancel</a>
    </form>
</body>
</html>
