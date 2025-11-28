<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Create User - Library Management System"/>
</jsp:include>

<h1>Create New User</h1>

<jsp:include page="../common/messages.jsp"/>

<form action="${pageContext.request.contextPath}/admin/users/create" method="post" class="form-container">
    <div class="form-group">
        <label for="email">Email Address: *</label>
        <input type="email" id="email" name="email" required
               value="<c:out value='${param.email}'/>"
               placeholder="user@example.com">
    </div>

    <div class="form-group">
        <label for="password">Initial Password: *</label>
        <input type="password" id="password" name="password" required
               placeholder="Min 8 chars, uppercase, lowercase, number">
        <small>Password must be at least 8 characters and contain uppercase, lowercase, and a number.</small>
    </div>

    <div class="form-row">
        <div class="form-group">
            <label for="firstName">First Name: *</label>
            <input type="text" id="firstName" name="firstName" required
                   value="<c:out value='${param.firstName}'/>"
                   placeholder="First name">
        </div>

        <div class="form-group">
            <label for="lastName">Last Name: *</label>
            <input type="text" id="lastName" name="lastName" required
                   value="<c:out value='${param.lastName}'/>"
                   placeholder="Last name">
        </div>
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
                  placeholder="User address (optional)"><c:out value='${param.address}'/></textarea>
    </div>

    <div class="form-row">
        <div class="form-group">
            <label for="roleId">Role: *</label>
            <select id="roleId" name="roleId" required>
                <c:forEach items="${roles}" var="role">
                    <option value="${role.roleId}" ${param.roleId == role.roleId ? 'selected' : ''}>
                        <c:out value="${role.name}"/>
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="standingId">Account Standing: *</label>
            <select id="standingId" name="standingId" required>
                <c:forEach items="${standings}" var="standing">
                    <option value="${standing.standingId}"
                            ${param.standingId == standing.standingId ? 'selected' : (standing.standingId == 1 ? 'selected' : '')}>
                        <c:out value="${standing.name}"/>
                    </option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">Create User</button>
        <a href="${pageContext.request.contextPath}/admin/users" class="btn">Cancel</a>
    </div>
</form>

<jsp:include page="../common/footer.jsp"/>
