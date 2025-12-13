<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.ac.cst8319.lms.model.WaitList" %>
<%@ page import="com.ac.cst8319.lms.model.Book" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Place Hold on Available Book</title>
	</head>
<body>
	<%
		WaitList waitList = (WaitList)request.getAttribute("waitlist");
		Book book = (Book)request.getAttribute("book");
		String message = (String)request.getAttribute("message");
	%>
	<nav>
		<a href="${pageContext.request.contextPath}/user/browseByGenre?action=access">Browse</a> 
		<a href="${pageContext.request.contextPath}/user/searchForBook?action=access">Search</a>
		<a href="${pageContext.request.contextPath}/user/returnBookUpdateWaitList?action=access">Update Wait List</a>
	</nav>
	
	<h2>Place Hold on Unavailable Book</h2>
	
	<p><%=message%></p>
	
	<table>
			<tr>
				<th>Book Title</th>
				<th>Position</th>
			</tr>
			<tr>
				<td><%=book.getTitle()%></td>
				<td><%=waitList.getPosition()%></td>
			</tr>
	</table>
</body>
</html>