<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Access Denied - Library Management System"/>
</jsp:include>

<div class="error-container">
    <h1>403 - Access Denied</h1>
    <p class="error-message">You do not have permission to access this resource.</p>
    <p class="error-details">Administrator privileges are required to view this page.</p>

    <div class="error-actions">
        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary">
            Go to Dashboard
        </a>
        <a href="${pageContext.request.contextPath}/" class="btn">
            Go to Home
        </a>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
