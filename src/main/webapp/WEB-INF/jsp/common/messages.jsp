<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- Display success message -->
<c:if test="${not empty successMessage}">
    <div class="message success">
        <c:out value="${successMessage}"/>
    </div>
</c:if>

<!-- Display error message -->
<c:if test="${not empty errorMessage}">
    <div class="message error">
        <c:out value="${errorMessage}"/>
    </div>
</c:if>

<!-- Display info message -->
<c:if test="${not empty infoMessage}">
    <div class="message info">
        <c:out value="${infoMessage}"/>
    </div>
</c:if>
