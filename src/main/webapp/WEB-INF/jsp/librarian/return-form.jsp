<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Return Book - Library Management System"/>
</jsp:include>

<h1>Return Book</h1>

<jsp:include page="../common/messages.jsp"/>

<c:choose>
    <c:when test="${not empty activeCheckouts}">
        <form method="post" action="${pageContext.request.contextPath}/librarian/return" class="form-container">
            <div class="form-group">
                <label for="checkoutId">Active Checkout *</label>
                <select id="checkoutId" name="checkoutId" required>
                    <option value="">Select Checkout to Return</option>
                    <c:forEach items="${activeCheckouts}" var="checkout">
                        <option value="${checkout.checkoutId}">
                            Checkout ID: ${checkout.checkoutId} |
                            Member: ${checkout.loanedTo} |
                            Book Copy: ${checkout.bookCopyId} |
                            Due: ${checkout.dueDate}
                            <c:if test="${checkout.dueDate.isBefore(java.time.LocalDate.now())}">
                                - OVERDUE
                            </c:if>
                        </option>
                    </c:forEach>
                </select>
                <small>Select the checkout record to process return</small>
            </div>

            <div class="message info" style="margin-top: 1rem;">
                <p><strong>Late Fee Policy:</strong> $1.00 per day for overdue items.</p>
                <p>Late fees will be automatically calculated if the return date is past the due date.</p>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Process Return</button>
                <a href="${pageContext.request.contextPath}/librarian/dashboard" class="btn btn-secondary">Cancel</a>
            </div>
        </form>

        <!-- Active Checkouts List -->
        <div style="margin-top: 2rem;">
            <h2>Active Checkouts</h2>
            <table>
                <thead>
                    <tr>
                        <th>Checkout ID</th>
                        <th>Member ID</th>
                        <th>Book Copy ID</th>
                        <th>Checkout Date</th>
                        <th>Due Date</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${activeCheckouts}" var="checkout">
                        <tr>
                            <td>${checkout.checkoutId}</td>
                            <td>${checkout.loanedTo}</td>
                            <td>${checkout.bookCopyId}</td>
                            <td>${checkout.checkoutDate}</td>
                            <td>${checkout.dueDate}</td>
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
        </div>
    </c:when>
    <c:otherwise>
        <div class="message info">
            <p>No active checkouts to return at this time.</p>
        </div>
        <div style="margin-top: 1rem;">
            <a href="${pageContext.request.contextPath}/librarian/dashboard" class="btn btn-secondary">Back to Dashboard</a>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="../common/footer.jsp"/>
