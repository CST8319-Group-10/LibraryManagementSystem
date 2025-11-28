<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Register - Library Management System"/>
</jsp:include>

<div class="auth-container">
    <h2>Create Your Account</h2>

    <jsp:include page="../common/messages.jsp"/>

    <form action="${pageContext.request.contextPath}/register" method="post" class="auth-form">
        <div class="form-group">
            <label for="email">Email Address: *</label>
            <input type="email" id="email" name="email" required
                   value="<c:out value='${param.email}'/>"
                   placeholder="your.email@example.com">
        </div>

        <div class="form-group">
            <label for="password">Password: *</label>
            <input type="password" id="password" name="password" required
                   placeholder="Min 8 chars, uppercase, lowercase, number">
            <small>Password must be at least 8 characters and contain uppercase, lowercase, and a number.</small>
        </div>

        <div class="form-group">
            <label for="confirmPassword">Confirm Password: *</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required
                   placeholder="Re-enter your password">
        </div>

        <div class="form-group">
            <label for="firstName">First Name: *</label>
            <input type="text" id="firstName" name="firstName" required
                   value="<c:out value='${param.firstName}'/>"
                   placeholder="Your first name">
        </div>

        <div class="form-group">
            <label for="lastName">Last Name: *</label>
            <input type="text" id="lastName" name="lastName" required
                   value="<c:out value='${param.lastName}'/>"
                   placeholder="Your last name">
        </div>

        <div class="form-group">
            <label for="phone">Phone Number:</label>
            <input type="tel" id="phone" name="phone"
                   value="<c:out value='${param.phone}'/>"
                   placeholder="(optional)">
        </div>

        <div class="form-group">
            <label for="address">Address:</label>
            <textarea id="address" name="address" rows="3"
                      placeholder="Your address (optional)"><c:out value='${param.address}'/></textarea>
        </div>

        <div class="form-group">
            <button type="submit" class="btn btn-primary">Register</button>
        </div>

        <div class="form-footer">
            <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a></p>
        </div>
    </form>
</div>

<script>
// Client-side password confirmation validation
document.querySelector('.auth-form').addEventListener('submit', function(e) {
    var password = document.getElementById('password').value;
    var confirmPassword = document.getElementById('confirmPassword').value;

    if (password !== confirmPassword) {
        e.preventDefault();
        alert('Passwords do not match!');
        return false;
    }
});
</script>

<jsp:include page="../common/footer.jsp"/>
