<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.ac.cst8319.lms.model.BookStatus" %>
<%@ page import="com.ac.cst8319.lms.model.BookCopy" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Place Hold on Book</title>
	</head>
<body>
	<%
		BookStatus bookStatus = (BookStatus)request.getAttribute("bookStatus");
		BookCopy bookCopy = (BookCopy)request.getAttribute("bookCopy");
		String message = (String)request.getAttribute("message");
	%>
	<nav>
		<a href="BrowseByGenre.jsp">Browse</a> 
		<a href="SearchForBook.jsp">Search</a>
	</nav>
	
	<h2>Place Hold on Book</h2><br>
	
	<p><%=message%></p><br>
	
	<table>
			<tr>
				<th>Book Copy Id</th>
				<th>Status</th>
				<th>Location</th>
			</tr>
			<tr>
				<td><%=bookCopy.getBookCopyId()%>
				<td><%=bookStatus.getName()%>
				<td><%=bookCopy.getLocation()%>
			</tr>
	</table>		
</body>
</html>