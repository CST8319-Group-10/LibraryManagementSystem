<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Edit User - Library Management System"/>
</jsp:include>

<h1>Edit User</h1>

<jsp:include page="../common/messages.jsp"/>

<form action="${pageContext.request.contextPath}/admin/users/edit" method="post" class="form-container">
    <input type="hidden" name="userId" value="${user.userId}"/>

    <div class="form-group">
        <label for="email">Email Address: *</label>
        <input type="email" id="email" name="email" required
               value="<c:out value='${user.email}'/>"
               placeholder="user@example.com">
    </div>

    <div class="form-row">
        <div class="form-group">
            <label for="firstName">First Name: *</label>
            <input type="text" id="firstName" name="firstName" required
                   value="<c:out value='${user.firstName}'/>"
                   placeholder="First name">
        </div>

        <div class="form-group">
            <label for="lastName">Last Name: *</label>
            <input type="text" id="lastName" name="lastName" required
                   value="<c:out value='${user.lastName}'/>"
                   placeholder="Last name">
        </div>
    </div>

    <div class="form-group">
        <label for="phone">Phone Number:</label>
        <input type="tel" id="phone" name="phone"
               value="<c:out value='${user.phone}'/>"
               placeholder="(optional)">
    </div>

    <div class="form-group">
        <label for="address">Address:</label>
        <textarea id="address" name="address" rows="3"
                  placeholder="User address (optional)"><c:out value='${user.address}'/></textarea>
    </div>

    <div class="form-row">
        <div class="form-group">
            <label for="roleId">Role: *</label>
            <select id="roleId" name="roleId" required>
                <c:forEach items="${roles}" var="role">
                    <option value="${role.roleId}" ${role.roleId == user.roleId ? 'selected' : ''}>
                        <c:out value="${role.name}"/>
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="standingId">Account Standing: *</label>
            <select id="standingId" name="standingId" required>
                <c:forEach items="${standings}" var="standing">
                    <option value="${standing.standingId}" ${standing.standingId == user.accountStanding ? 'selected' : ''}>
                        <c:out value="${standing.name}"/>
                    </option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">Update User</button>
        <a href="${pageContext.request.contextPath}/admin/users/view?userId=${user.userId}" class="btn">Cancel</a>
    </div>
</form>

<jsp:include page="../common/footer.jsp"/>
