<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Dashboard - Library Management System"/>
</jsp:include>

<h1>Welcome, <c:out value="${sessionScope.currentUser.firstName}"/>!</h1>

<jsp:include page="../common/messages.jsp"/>

<div class="dashboard-grid">
    <!-- Account Information -->
    <div class="account-info">
        <h2>Your Account</h2>
        <div class="info-row">
            <span class="label">Email:</span>
            <span class="value"><c:out value="${sessionScope.currentUser.email}"/></span>
        </div>
        <div class="info-row">
            <span class="label">Name:</span>
            <span class="value">
                <c:out value="${sessionScope.currentUser.firstName}"/>
                <c:out value="${sessionScope.currentUser.lastName}"/>
            </span>
        </div>
        <div class="info-row">
            <span class="label">Account Standing:</span>
            <span class="value">
                <c:choose>
                    <c:when test="${sessionScope.currentUser.accountStanding == 1}">
                        <span class="badge badge-success">Good Standing</span>
                    </c:when>
                    <c:when test="${sessionScope.currentUser.accountStanding == 2}">
                        <span class="badge badge-warning">Suspended</span>
                    </c:when>
                    <c:when test="${sessionScope.currentUser.accountStanding == 3}">
                        <span class="badge badge-danger">Banned</span>
                    </c:when>
                    <c:otherwise>
                        <span class="badge badge-info">Pending Approval</span>
                    </c:otherwise>
                </c:choose>
            </span>
        </div>
    </div>

    <!-- Quick Actions -->
    <div class="quick-actions">
        <h2>Quick Actions</h2>
        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/user/change-password" class="btn btn-secondary">
                Change Password
            </a>
        </div>
    </div>

    <!-- Placeholder for future features -->
    <div class="placeholder-section">
        <h2>My Checkouts</h2>
        <p class="info-text">You currently have no books checked out.</p>
        <p class="info-text"><em>Book checkout functionality will be available soon.</em></p>
    </div>

    <div class="placeholder-section">
        <h2>Waitlist</h2>
        <p class="info-text">You are not on any waitlists.</p>
        <p class="info-text"><em>Waitlist functionality will be available soon.</em></p>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
