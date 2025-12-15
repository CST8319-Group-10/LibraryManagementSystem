<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.ac.cst8319.lms.model.BookStatus" %>
<%@ page import="com.ac.cst8319.lms.model.BookCopy" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Check Book Availability</title>
	</head>
<body>
	<%
		Map<BookCopy, BookStatus> bookCopies = (Map<BookCopy, BookStatus>)request.getAttribute("bookCopies");
		String bookTitle = (String)request.getAttribute("bookTitle");
		String message = (String)request.getAttribute("message");
	%>
	<nav>
		<a href="${pageContext.request.contextPath}/user/browseByGenre?action=access">Browse</a> 
		<a href="${pageContext.request.contextPath}/user/searchForBook?action=access">Search</a>
		<a href="${pageContext.request.contextPath}/user/returnBookUpdateWaitList?action=access">Update Wait List</a>
	</nav>
	
	<h2>Check Availability of <%=bookTitle%></h2><br>
	
	<!--Controls whether book copies were found after checking availability for a book-->	
	<%
		if (bookCopies == null) {
	%>
		<p>Search or browse for a book, then click it to view its availability.</p>
	<%
		}
		else {
			for (Map.Entry<BookCopy, BookStatus> bookCopy : bookCopies.entrySet()) {
	%>
				<form action="${pageContext.request.contextPath}/user/placeHoldOnBook?bookTitle=<%=bookTitle%>" method="POST">			
					<label for="bookCopyId">Book Copy Id</label> 
					<input type="text" id="bookCopyId" name="bookCopyId" value="<%=bookCopy.getKey().getBookCopyId()%>">
					<label for="status">Status</label> 
					<input type="text" id="status" name="bookStatus" value="<%=bookCopy.getValue().getName()%>">
					<label for="location">Location</label> 
					<input type="text" id="location" name="location" value="<%=bookCopy.getKey().getLocation()%>">
					<input type="submit" value="Place Hold">
				</form><br>
	<%
			}
		}
	%>	
	<p><%=message%></p>
</body>
</html>