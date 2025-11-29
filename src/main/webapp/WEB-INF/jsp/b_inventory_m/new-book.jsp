<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Book</title>
</head>
<body>
    <h2>Add New Book</h2>
    <form action="BookServlet" method="POST">
        <input type="hidden" name="action" value="add"/>
        
        <label for="id">ID:</label>
        <input type="text" id="id" name="id" required/><br/><br/>

        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required/><br/><br/>

        <label for="author">Author:</label>
        <input type="text" id="author" name="author" required/><br/><br/>

        <label for="available">Available:</label>
        <input type="checkbox" id="available" name="available" value="true" checked/><br/><br/>

        <input type="submit" value="Save Book"/>
        <a href="BookServlet?action=list">Cancel</a>
    </form>
</body>
</html>
