<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Login - Library Management System"/>
</jsp:include>

<div class="auth-container">
    <h2>Login to Library Management System</h2>

    <jsp:include page="../common/messages.jsp"/>

    <form action="${pageContext.request.contextPath}/login" method="post" class="auth-form">
        <div class="form-group">
            <label for="email">Email Address:</label>
            <input type="email" id="email" name="email" required
                   value="<c:out value='${param.email}'/>"
                   placeholder="Enter your email">
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required
                   placeholder="Enter your password">
        </div>

        <div class="form-group">
            <button type="submit" class="btn btn-primary">Login</button>
        </div>

        <div class="form-footer">
            <p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Register here</a></p>
        </div>
    </form>

    <div class="demo-info">
        <h3>Demo Accounts</h3>
        <p><strong>Admin:</strong> admin@library.com / Admin123!</p>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
