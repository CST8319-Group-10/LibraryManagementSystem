<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.ac.cst8319.lms.model.Author" %>
<%@ page import="com.ac.cst8319.lms.model.Book" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Search for a Book</title>
	</head>
<body>
	<%
		Map<Book, Author> books = (Map<Book, Author>)request.getAttribute("books");
		String message = (String)request.getAttribute("message");
	%>
	<nav>
		<a href="${pageContext.request.contextPath}/user/browseByGenre?action=access">Browse</a> 
		<a href="${pageContext.request.contextPath}/user/searchForBook?action=access">Search</a>
		<a href="${pageContext.request.contextPath}/librarian/returnBookUpdateWaitList?action=access">Update Wait List</a>
	</nav>
	
	<h2>Search For Books</h2><br>
	
	<h4>Search By Title</h4><br>
	
	<form action="${pageContext.request.contextPath}/user/searchForBook" method="GET">
		<label for="title">Title</label>
		<input type="text" id="title" name="bookTitle">
		<input type="submit" value="Search">
	</form><br>
	
	<h4>Search By Author</h4><br>
	
	<form action="${pageContext.request.contextPath}/user/searchForBook" method="GET">
		<label for="author">Author</label>
		<input type="text" id="author" name="bookAuthor">
		<input type="submit" value="Search">
	</form><br>
	
	<h4>Books Found</h4><br>	
	
	<!--Controls whether book copies were found after searching by title or author-->	
	<%
		if (books == null){
	%>
			<p>Search by title or author to search for books.</p>
	<%
		}
		else {
			for (Map.Entry<Book, Author> book : books.entrySet()) {	
	%>
				<form action="${pageContext.request.contextPath}/user/checkBookAvailability" method="GET">			
					<label for="title">Title</label> 
					<input type="text" id="title" name="bookTitle" value="<%=book.getKey().getTitle()%>">
					<label for="firstName">Author's First Name</label> 
					<input type="text" id="firstName" name="authorFirstName" value="<%=book.getValue().getFirstName()%>">
					<label for="lastName">Author's Last Name</label> 
					<input type="text" id="lastName" name="authorLastName" value="<%=book.getValue().getLastName()%>">
					<label for="publisher">Publisher</label> 
					<input type="text" id="publisher" name="bookPublisher" value="<%=book.getKey().getPublisher()%>">
					<label for="publicationYear">Publication Year</label> 
					<input type="text" id="publicationYear" name="bookPublicationYear" value="<%=book.getKey().getPublicationYear()%>">
					<label for="description">Description</label> 
					<input type="text" id="description" name="bookDescription" value="<%=book.getKey().getDescription()%>">
					<input type="submit" value="Check Availability">
				</form><br>
	<%
			}
	%>
		<p><%=message%></p>
	<%		
		}
	%>
</body>
</html>