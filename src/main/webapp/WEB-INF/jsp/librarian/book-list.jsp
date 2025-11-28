<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Book List - Library Management System"/>
</jsp:include>

<h1>Book List</h1>

<jsp:include page="../common/messages.jsp"/>

<!-- Search and Actions -->
<div class="page-actions">
    <form method="get" action="${pageContext.request.contextPath}/librarian/books" class="inline-form" style="display: inline-block; margin-right: 1rem;">
        <input type="text" name="search" placeholder="Search by title, author, or ISBN"
               value="${searchTerm}" style="width: 300px; display: inline-block; margin-right: 0.5rem;">
        <button type="submit" class="btn btn-secondary">Search</button>
        <c:if test="${not empty searchTerm}">
            <a href="${pageContext.request.contextPath}/librarian/books" class="btn btn-secondary">Clear</a>
        </c:if>
    </form>
    <a href="${pageContext.request.contextPath}/librarian/books/create" class="btn btn-primary">Add New Book</a>
</div>

<!-- Books Table -->
<c:choose>
    <c:when test="${not empty books}">
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>ISBN</th>
                    <th>Title</th>
                    <th>Author ID</th>
                    <th>Genre ID</th>
                    <th>Publisher</th>
                    <th>Year</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${books}" var="book">
                    <tr>
                        <td>${book.bookId}</td>
                        <td>${book.isbn}</td>
                        <td><c:out value="${book.title}"/></td>
                        <td>${book.authorId}</td>
                        <td>${book.genreId}</td>
                        <td><c:out value="${book.publisher}"/></td>
                        <td>${book.publicationYear}</td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/librarian/books/view?id=${book.bookId}"
                               class="btn btn-small btn-secondary">View</a>
                            <a href="${pageContext.request.contextPath}/librarian/books/edit?id=${book.bookId}"
                               class="btn btn-small btn-primary">Edit</a>
                            <a href="${pageContext.request.contextPath}/librarian/books/copies?bookId=${book.bookId}"
                               class="btn btn-small btn-secondary">Copies</a>
                            <form method="post" action="${pageContext.request.contextPath}/librarian/books/delete"
                                  style="display: inline;" onsubmit="return confirm('Are you sure you want to delete this book?');">
                                <input type="hidden" name="id" value="${book.bookId}">
                                <button type="submit" class="btn btn-small btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <div class="no-data">
            <c:choose>
                <c:when test="${not empty searchTerm}">
                    <p>No books found matching "${searchTerm}"</p>
                </c:when>
                <c:otherwise>
                    <p>No books in the library yet.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="../common/footer.jsp"/>
