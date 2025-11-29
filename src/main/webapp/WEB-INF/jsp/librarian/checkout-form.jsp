<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Checkout Book - Library Management System"/>
</jsp:include>

<h1>Checkout Book</h1>

<jsp:include page="../common/messages.jsp"/>

<form method="post" action="${pageContext.request.contextPath}/librarian/checkout" class="form-container">
    <div class="form-group">
        <label for="memberId">Member *</label>
        <select id="memberId" name="memberId" required>
            <option value="">Select Member</option>
            <c:forEach items="${members}" var="member">
                <option value="${member.userId}">
                    ${member.userId} - ${member.firstName} ${member.lastName} (${member.email})
                </option>
            </c:forEach>
        </select>
        <small>Select the member checking out the book</small>
    </div>

    <div class="form-group">
        <label for="bookCopyId">Available Book Copy *</label>
        <select id="bookCopyId" name="bookCopyId" required>
            <option value="">Select Book Copy</option>
            <c:forEach items="${availableCopies}" var="copy">
                <option value="${copy.bookCopyId}">
                    Copy ID: ${copy.bookCopyId} - Book ID: ${copy.bookId} - Location: ${copy.location}
                </option>
            </c:forEach>
        </select>
        <small>Only available copies are shown</small>
    </div>

    <div class="message info" style="margin-top: 1rem;">
        <p><strong>Note:</strong> The due date will be set to 14 days from today.</p>
        <p>Members with overdue items or suspended accounts cannot checkout books.</p>
    </div>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">Checkout Book</button>
        <a href="${pageContext.request.contextPath}/librarian/dashboard" class="btn btn-secondary">Cancel</a>
    </div>
</form>

<jsp:include page="../common/footer.jsp"/>
