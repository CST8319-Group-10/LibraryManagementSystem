<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Book Details - Library Management System"/>
</jsp:include>

<h1>Book Details</h1>

<jsp:include page="../common/messages.jsp"/>

<div class="detail-container">
    <!-- Book Information -->
    <div class="detail-section">
        <h2>${book.title}</h2>

        <div class="detail-row">
            <div class="label">Book ID:</div>
            <div class="value">${book.bookId}</div>
        </div>

        <div class="detail-row">
            <div class="label">ISBN:</div>
            <div class="value">${book.isbn}</div>
        </div>

        <div class="detail-row">
            <div class="label">Author:</div>
            <div class="value">
                <c:if test="${not empty author}">
                    ${author.firstName} ${author.lastName}
                </c:if>
                <c:if test="${empty author}">
                    Author ID: ${book.authorId}
                </c:if>
            </div>
        </div>

        <div class="detail-row">
            <div class="label">Genre:</div>
            <div class="value">
                <c:if test="${not empty genre}">
                    ${genre.name}
                </c:if>
                <c:if test="${empty genre}">
                    Genre ID: ${book.genreId}
                </c:if>
            </div>
        </div>

        <div class="detail-row">
            <div class="label">Publisher:</div>
            <div class="value"><c:out value="${book.publisher}"/></div>
        </div>

        <div class="detail-row">
            <div class="label">Publication Year:</div>
            <div class="value">${book.publicationYear}</div>
        </div>

        <c:if test="${not empty book.description}">
            <div class="detail-row">
                <div class="label">Description:</div>
                <div class="value"><c:out value="${book.description}"/></div>
            </div>
        </c:if>

        <div class="detail-actions">
            <a href="${pageContext.request.contextPath}/librarian/books/edit?id=${book.bookId}"
               class="btn btn-primary">Edit Book</a>
            <a href="${pageContext.request.contextPath}/librarian/books/copies?bookId=${book.bookId}"
               class="btn btn-secondary">Manage Copies</a>
            <a href="${pageContext.request.contextPath}/librarian/books"
               class="btn btn-secondary">Back to List</a>
        </div>
    </div>

    <!-- Book Copies -->
    <div class="detail-section">
        <h2>Book Copies</h2>

        <c:choose>
            <c:when test="${not empty bookCopies}">
                <table>
                    <thead>
                        <tr>
                            <th>Copy ID</th>
                            <th>Status ID</th>
                            <th>Location</th>
                            <th>Acquisition Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${bookCopies}" var="copy">
                            <tr>
                                <td>${copy.bookCopyId}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${copy.statusId == 1}">
                                            <span class="badge badge-success">Available</span>
                                        </c:when>
                                        <c:when test="${copy.statusId == 2}">
                                            <span class="badge badge-warning">Checked Out</span>
                                        </c:when>
                                        <c:when test="${copy.statusId == 3}">
                                            <span class="badge badge-danger">Damaged</span>
                                        </c:when>
                                        <c:when test="${copy.statusId == 4}">
                                            <span class="badge badge-danger">Lost</span>
                                        </c:when>
                                        <c:otherwise>
                                            Status ${copy.statusId}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><c:out value="${copy.location}"/></td>
                                <td>${copy.aquisitionDate}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p class="info-text">No copies for this book yet.</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
