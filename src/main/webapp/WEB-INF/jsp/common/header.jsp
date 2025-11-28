<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title != null ? param.title : 'Library Management System'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav>
        <div class="nav-container">
            <div class="nav-brand">
                <a href="${pageContext.request.contextPath}/">Library Management System</a>
            </div>
            <div class="nav-links">
                <c:choose>
                    <c:when test="${sessionScope.currentUser != null}">
                        <!-- Authenticated user menu -->
                        <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>

                        <!-- Admin menu -->
                        <c:if test="${sessionScope.currentUser.roleId == 4}">
                            <a href="${pageContext.request.contextPath}/admin/users">Manage Users</a>
                        </c:if>

                        <!-- User info and logout -->
                        <span class="user-info">
                            Welcome, <c:out value="${sessionScope.currentUser.firstName}"/>
                            <c:choose>
                                <c:when test="${sessionScope.currentUser.roleId == 4}">(Admin)</c:when>
                                <c:when test="${sessionScope.currentUser.roleId == 3}">(Librarian)</c:when>
                            </c:choose>
                        </span>

                        <form action="${pageContext.request.contextPath}/logout" method="post" style="display: inline;">
                            <button type="submit" class="btn-link">Logout</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <!-- Guest menu -->
                        <a href="${pageContext.request.contextPath}/login">Login</a>
                        <a href="${pageContext.request.contextPath}/register">Register</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </nav>
    <div class="container">
