<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Borrowed Book List - Library Management System"/>
</jsp:include>

<h1>Borrowed Book List</h1>

<jsp:include page="../common/messages.jsp"/>

<!-- Borrowed Books Table -->
<c:choose>
    <c:when test="${not empty borrowedBooks}">
        <table class="data-table">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author ID</th>
                    <th>Genre ID</th>
                    <th>Publisher</th>
                    <th>Year</th>
                    <th>Date Borrowed</th>
                    <th>Due Date</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${borrowedBooks}" var="bb">
                    <tr>
                        <td><c:out value="${bb.book.title}"/></td>
                        <td>${bb.book.authorId}</td>
                        <td>${bb.book.genreId}</td>
                        <td><c:out value="${bb.book.publisher}"/></td>
                        <td>${bb.book.publicationYear}</td>
                        <td>${bb.checkout.checkoutDate}</td>
                        <td>${bb.checkout.dueDate}</td>
                        <td>
                            <c:choose>
                                <c:when test="${checkout.dueDate.isBefore(java.time.LocalDate.now())}">
                                    <span class="badge badge-danger">Overdue</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-success">Active</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <div class="no-data">
            <p>No books currently borrowed.</p>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="../common/footer.jsp"/>
