<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.lms.web.model.Author" %>
<%@ page import="com.lms.web.model.Book" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Browse for Books by Genre</title>
	</head>
<body>
	<%
		Map<Book, Author> books = (Map<Book, Author>)request.getAttribute("books");
	%>
	<nav>
		<a href="BrowseByGenre.jsp">Browse</a> 
		<a href="SearchForBook.jsp">Search</a>
	</nav>
	
	<h2>Browse for Books by Genre</h2><br>
	
	<form action="browseByGenre" method="get">
		<label for="genre">Genre</label>
		<input type="text" id="genre" name="genre">
		<input type="submit" value="Browse">
	</form><br>

	<h4>Books Found</h4><br>	
	
	<!--Controls whether books were found from browsing through a genre-->	
		<% 
			if(books == null) {		
		%>	
				<p>Specify a genre to browse for books.</p>
		<%
			}
			else {
					for (Map.Entry<Book, Author> book : books.entrySet()) {	
		%>
						<form action="checkBookAvailability" method="get">	
							<label for="title>">Title</label> 
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
			}
		%>
</body>
</html>