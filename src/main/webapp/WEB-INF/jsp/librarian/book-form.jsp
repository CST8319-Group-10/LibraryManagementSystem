<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${isEdit ? 'Edit Book' : 'Create Book'} - Library Management System"/>
</jsp:include>

<h1>${isEdit ? 'Edit Book' : 'Create New Book'}</h1>

<jsp:include page="../common/messages.jsp"/>

<form method="post" action="${pageContext.request.contextPath}/librarian/books/${isEdit ? 'edit' : 'create'}" class="form-container">
    <c:if test="${isEdit}">
        <input type="hidden" name="bookId" value="${book.bookId}">
    </c:if>

    <div class="form-group">
        <label for="isbn">ISBN *</label>
        <input type="text" id="isbn" name="isbn" value="${book.isbn}" required>
        <small>International Standard Book Number</small>
    </div>

    <div class="form-group">
        <label for="title">Title *</label>
        <input type="text" id="title" name="title" value="${book.title}" required>
    </div>

    <div class="form-row">
        <div class="form-group">
            <label for="authorId">Author *</label>
            <select id="authorId" name="authorId" required>
                <option value="">Select Author</option>
                <c:forEach items="${authors}" var="author">
                    <option value="${author.authorId}"
                            ${book.authorId == author.authorId ? 'selected' : ''}>
                        ${author.firstName} ${author.lastName}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="genreId">Genre *</label>
            <select id="genreId" name="genreId" required>
                <option value="">Select Genre</option>
                <c:forEach items="${genres}" var="genre">
                    <option value="${genre.genreId}"
                            ${book.genreId == genre.genreId ? 'selected' : ''}>
                        ${genre.name}
                    </option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="form-row">
        <div class="form-group">
            <label for="publisher">Publisher</label>
            <input type="text" id="publisher" name="publisher" value="${book.publisher}">
        </div>

        <div class="form-group">
            <label for="publicationYear">Publication Year</label>
            <input type="text" id="publicationYear" name="publicationYear"
                   value="${book.publicationYear}" placeholder="YYYY">
        </div>
    </div>

    <div class="form-group">
        <label for="description">Description</label>
        <textarea id="description" name="description" rows="4">${book.description}</textarea>
    </div>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">
            ${isEdit ? 'Update Book' : 'Create Book'}
        </button>
        <a href="${pageContext.request.contextPath}/librarian/books" class="btn btn-secondary">Cancel</a>
    </div>
</form>

<jsp:include page="../common/footer.jsp"/>
