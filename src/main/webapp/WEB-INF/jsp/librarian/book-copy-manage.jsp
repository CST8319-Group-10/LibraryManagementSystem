<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Manage Book Copies - Library Management System"/>
</jsp:include>

<h1>Manage Copies for: ${book.title}</h1>

<jsp:include page="../common/messages.jsp"/>

<div class="detail-container">
    <!-- Add New Copy Form -->
    <div class="detail-section">
        <h2>Add New Copy</h2>
        <form method="post" action="${pageContext.request.contextPath}/librarian/books/copies">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="bookId" value="${book.bookId}">

            <div class="form-row">
                <div class="form-group">
                    <label for="statusId">Status *</label>
                    <select id="statusId" name="statusId" required>
                        <c:forEach items="${bookStatuses}" var="status">
                            <option value="${status.statusId}"
                                    ${status.statusId == 1 ? 'selected' : ''}>
                                ${status.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="location">Location</label>
                    <input type="text" id="location" name="location" placeholder="e.g., Shelf A-12">
                </div>

                <div class="form-group">
                    <label for="acquisitionDate">Acquisition Date</label>
                    <input type="date" id="acquisitionDate" name="acquisitionDate"
                           value="<fmt:formatDate value='${java.time.LocalDate.now()}' pattern='yyyy-MM-dd'/>">
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Add Copy</button>
            </div>
        </form>
    </div>

    <!-- Existing Copies -->
    <div class="detail-section">
        <h2>Existing Copies (${bookCopies.size()})</h2>

        <c:choose>
            <c:when test="${not empty bookCopies}">
                <table>
                    <thead>
                        <tr>
                            <th>Copy ID</th>
                            <th>Status</th>
                            <th>Location</th>
                            <th>Acquisition Date</th>
                            <th>Actions</th>
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
                                <td class="actions">
                                    <form method="post" action="${pageContext.request.contextPath}/librarian/books/copies"
                                          style="display: inline;"
                                          onsubmit="return confirm('Are you sure you want to delete this copy?');">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="bookId" value="${book.bookId}">
                                        <input type="hidden" name="copyId" value="${copy.bookCopyId}">
                                        <button type="submit" class="btn btn-small btn-danger">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p class="info-text">No copies for this book yet.</p>
            </c:otherwise>
        </c:choose>

        <div style="margin-top: 1.5rem;">
            <a href="${pageContext.request.contextPath}/librarian/books/view?id=${book.bookId}"
               class="btn btn-secondary">Back to Book</a>
            <a href="${pageContext.request.contextPath}/librarian/books"
               class="btn btn-secondary">Back to List</a>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
