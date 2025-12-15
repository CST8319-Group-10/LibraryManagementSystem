<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.ac.cst8319.lms.model.BookStatus" %>
<%@ page import="com.ac.cst8319.lms.model.BookCopy" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Place Hold on Available Book</title>
	</head>
<body>
	<%
		BookStatus bookStatus = (BookStatus)request.getAttribute("updatedBookStatus");
		BookCopy bookCopy = (BookCopy)request.getAttribute("bookCopy");
		String message = (String)request.getAttribute("message");
	%>
	<nav>
		<a href="${pageContext.request.contextPath}/user/browseByGenre?action=access">Browse</a> 
		<a href="${pageContext.request.contextPath}/user/searchForBook?action=access">Search</a>
		<a href="${pageContext.request.contextPath}/user/returnBookUpdateWaitList?action=access">Update Wait List</a>
	</nav>
	
	<h2>Place Hold on Available Book</h2><br>
	
	<%
		if(bookStatus == null){
	%>
			<p><%=message%></p><br>
	<%
		}
		else{
	%>
	<table>
			<tr>
				<th>Book Copy Id</th>
				<th>Status</th>
				<th>Location</th>
			</tr>
			<tr>
				<td><%=bookCopy.getBookCopyId()%></td>
				<td><%=bookStatus.getName()%></td>
				<td><%=bookCopy.getLocation()%></td>
			</tr>
	</table>
	
	<p><%=message%></p><br>
	<%
		}
	%>		
</body>
</html>