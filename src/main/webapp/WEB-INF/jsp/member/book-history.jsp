<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Book History - Library Management System"/>
</jsp:include>

<h1>Borrowed Book List</h1>

<jsp:include page="../common/messages.jsp"/>

<!-- Borrowed Books Table -->
<c:choose>
    <c:when test="${not empty bookHistory}">
        <table class="data-table">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author ID</th>
                    <th>Genre ID</th>
                    <th>Publisher</th>
                    <th>Year</th>
                    <th>Latest Checkout</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${bookHistory}" var="hist">
                    <tr>
                        <td><c:out value="${hist.book.title}"/></td>
                        <td>${hist.book.authorId}</td>
                        <td>${hist.book.genreId}</td>
                        <td><c:out value="${hist.book.publisher}"/></td>
                        <td>${hist.book.publicationYear}</td>
                        <td>${hist.latestCheckout}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <div class="no-data">
            <p>No books borrowed.</p>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="../common/footer.jsp"/>
