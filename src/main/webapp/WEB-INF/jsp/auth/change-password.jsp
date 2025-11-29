<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Change Password - Library Management System"/>
</jsp:include>

<h1>Change Password</h1>

<jsp:include page="../common/messages.jsp"/>

<div class="form-container">
    <form action="${pageContext.request.contextPath}/user/change-password" method="post">
        <div class="form-group">
            <label for="oldPassword">Current Password: *</label>
            <input type="password" id="oldPassword" name="oldPassword" required
                   placeholder="Enter your current password">
        </div>

        <div class="form-group">
            <label for="newPassword">New Password: *</label>
            <input type="password" id="newPassword" name="newPassword" required
                   placeholder="Min 8 chars, uppercase, lowercase, number">
            <small>Password must be at least 8 characters and contain uppercase, lowercase, and a number.</small>
        </div>

        <div class="form-group">
            <label for="confirmPassword">Confirm New Password: *</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required
                   placeholder="Re-enter your new password">
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">Change Password</button>
            <a href="${pageContext.request.contextPath}/dashboard" class="btn">Cancel</a>
        </div>
    </form>
</div>

<script>
// Client-side password confirmation validation
document.querySelector('form').addEventListener('submit', function(e) {
    var newPassword = document.getElementById('newPassword').value;
    var confirmPassword = document.getElementById('confirmPassword').value;

    if (newPassword !== confirmPassword) {
        e.preventDefault();
        alert('New passwords do not match!');
        return false;
    }
});
</script>

<jsp:include page="../common/footer.jsp"/>
