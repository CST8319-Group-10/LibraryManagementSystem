<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.ac.cst8319.lms.model.WaitList" %>
<%@ page import="com.ac.cst8319.lms.model.User" %>
<%@ page import="com.ac.cst8319.lms.model.Book" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
		<title>Update Wait List for a Book</title>
	</head>
<body>
	<%
		Map<User, WaitList> waitListUsers = (Map<User, WaitList>)request.getAttribute("updatedWaitlistUsers");
		Book book = (Book)request.getAttribute("book");
		String message = (String)request.getAttribute("message");
	%>
	<nav>
		<a href="${pageContext.request.contextPath}/user/browseByGenre?action=access">Browse</a> 
		<a href="${pageContext.request.contextPath}/user/searchForBook?action=access">Search</a>
		<a href="${pageContext.request.contextPath}/user/returnBookUpdateWaitList?action=access">Update Wait List</a>
	</nav>
	
	<h1>What book was returned?</h1>
	
	<form action="${pageContext.request.contextPath}/user/returnBookUpdateWaitList" method="POST">
		<label for="title">Title</label>
		<input type="text" id="title" name="bookTitle">
		<input type="submit" value="Update Wait List">
	</form><br>
	
	<h2>Updated Wait List</h2>
	<%
		if (waitListUsers == null){
	%>
			<p>Enter book title to update wait list.</p>
	<%
		}
		else {
	%>
		<table>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Book Title</th>
				<th>Position</th>
			</tr>
	<%
			for (Map.Entry<User, WaitList> waitListUser : waitListUsers.entrySet()) {
	%>			
				<tr>
					<td><%=waitListUser.getKey().getFirstName()%></td>
					<td><%=waitListUser.getKey().getLastName()%></td>
					<td><%=book.getTitle()%></td>
					<td><%=waitListUser.getValue().getPosition()%></td>
				</tr>	
	<%			
			}
	%>
		</table><br>	
		<p><%=message%></p>		
	<%
		}	
	%>
</body>
</html>