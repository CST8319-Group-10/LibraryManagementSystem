<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="User Details - Library Management System"/>
</jsp:include>

<h1>User Details</h1>

<div class="detail-container">
    <div class="detail-section">
        <h2>Personal Information</h2>
        <div class="detail-row">
            <span class="label">User ID:</span>
            <span class="value">${user.userId}</span>
        </div>
        <div class="detail-row">
            <span class="label">Email:</span>
            <span class="value"><c:out value="${user.email}"/></span>
        </div>
        <div class="detail-row">
            <span class="label">First Name:</span>
            <span class="value"><c:out value="${user.firstName}"/></span>
        </div>
        <div class="detail-row">
            <span class="label">Last Name:</span>
            <span class="value"><c:out value="${user.lastName}"/></span>
        </div>
        <div class="detail-row">
            <span class="label">Phone:</span>
            <span class="value"><c:out value="${user.phone}"/></span>
        </div>
        <div class="detail-row">
            <span class="label">Address:</span>
            <span class="value"><c:out value="${user.address}"/></span>
        </div>
    </div>

    <div class="detail-section">
        <h2>Account Information</h2>
        <div class="detail-row">
            <span class="label">Current Role:</span>
            <span class="value">
                <c:forEach items="${roles}" var="role">
                    <c:if test="${role.roleId == user.roleId}">
                        <c:out value="${role.name}"/>
                    </c:if>
                </c:forEach>
            </span>
        </div>
        <div class="detail-row">
            <span class="label">Account Standing:</span>
            <span class="value">
                <c:forEach items="${standings}" var="standing">
                    <c:if test="${standing.standingId == user.accountStanding}">
                        <c:out value="${standing.name}"/>
                    </c:if>
                </c:forEach>
            </span>
        </div>
        <div class="detail-row">
            <span class="label">Created At:</span>
            <span class="value">
                ${user.createdAt.toString().replace('T', ' ').replace('Z', '').substring(0, 19)}
            </span>
        </div>
        <div class="detail-row">
            <span class="label">Last Updated:</span>
            <span class="value">
                ${user.updatedAt.toString().replace('T', ' ').replace('Z', '').substring(0, 19)}
            </span>
        </div>
        <c:if test="${user.lastLoginAt != null}">
            <div class="detail-row">
                <span class="label">Last Login:</span>
                <span class="value">
                    ${user.lastLoginAt.toString().replace('T', ' ').replace('Z', '').substring(0, 19)}
                </span>
            </div>
        </c:if>
    </div>

    <div class="detail-section">
        <h2>Update Role</h2>
        <form action="${pageContext.request.contextPath}/admin/users/role/update" method="post" class="inline-form">
            <input type="hidden" name="userId" value="${user.userId}"/>
            <select name="roleId" required>
                <c:forEach items="${roles}" var="role">
                    <option value="${role.roleId}" ${role.roleId == user.roleId ? 'selected' : ''}>
                        <c:out value="${role.name}"/>
                    </option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn-primary">Update Role</button>
        </form>
    </div>

    <div class="detail-section">
        <h2>Update Account Standing</h2>
        <form action="${pageContext.request.contextPath}/admin/users/standing/update" method="post" class="inline-form">
            <input type="hidden" name="userId" value="${user.userId}"/>
            <div class="form-group">
                <label for="standingId">Standing:</label>
                <select id="standingId" name="standingId" required>
                    <c:forEach items="${standings}" var="standing">
                        <option value="${standing.standingId}" ${standing.standingId == user.accountStanding ? 'selected' : ''}>
                            <c:out value="${standing.name}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="reason">Reason:</label>
                <textarea id="reason" name="reason" rows="3" required placeholder="Enter reason for status change"></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Update Standing</button>
        </form>
    </div>

    <div class="detail-actions">
        <a href="${pageContext.request.contextPath}/admin/users/edit?userId=${user.userId}" class="btn btn-secondary">
            Edit User
        </a>
        <c:if test="${user.userId != sessionScope.currentUser.userId}">
            <form action="${pageContext.request.contextPath}/admin/users/delete" method="post" style="display:inline;"
                  onsubmit="return confirm('Are you sure you want to delete this user? This action cannot be undone.');">
                <input type="hidden" name="userId" value="${user.userId}"/>
                <button type="submit" class="btn btn-danger">Delete User</button>
            </form>
        </c:if>
        <a href="${pageContext.request.contextPath}/admin/users" class="btn">
            Back to User List
        </a>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
