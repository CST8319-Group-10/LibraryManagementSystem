<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Process Fee Payment - Library Management System"/>
</jsp:include>

<h1>Fee Payment</h1>

<jsp:include page="../common/messages.jsp"/>

<c:choose>
    <c:when test="${not empty feesOwed}">
        <form method="post" action="${pageContext.request.contextPath}/librarian/fees" class="form-container">
            <div class="form-group">
                <label for="checkoutId">Active Checkout *</label>
                <select id="checkoutId" name="checkoutId" required>
                    <option value="">Select Checkout to Return</option>
                    <c:forEach items="${feesOwed}" var="checkout">
                        <option value="${checkout.checkoutId}">
                            Checkout ID: ${checkout.checkoutId} |
                            Member: ${checkout.loanedTo} |
                            Fee: ${checkout.lateFeeAssessed} |
                            Checkout Date: ${checkout.checkoutDate}
                        </option>
                    </c:forEach>
                </select>
                <small>Select the checkout record to process fees for</small>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Process Fee Payment</button>
                <a href="${pageContext.request.contextPath}/librarian/dashboard" class="btn btn-secondary">Cancel</a>
            </div>
        </form>

        <!-- Fees Owed Checkouts List -->
        <div style="margin-top: 2rem;">
            <h2>Fees Owed</h2>
            <table>
                <thead>
                    <tr>
                        <th>Checkout ID</th>
                        <th>Member ID</th>
                        <th>Book Copy ID</th>
                        <th>Checkout Date</th>
                        <th>Late Fee</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${feesOwed}" var="checkout">
                        <tr>
                            <td>${checkout.checkoutId}</td>
                            <td>${checkout.loanedTo}</td>
                            <td>${checkout.bookCopyId}</td>
                            <td>${checkout.checkoutDate}</td>
                            <td>${checkout.lateFeeAssessed}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:when>
    <c:otherwise>
        <div class="message info">
            <p>No unpaid fees found at this time.</p>
        </div>
        <div style="margin-top: 1rem;">
            <a href="${pageContext.request.contextPath}/librarian/dashboard" class="btn btn-secondary">Back to Dashboard</a>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="../common/footer.jsp"/>
