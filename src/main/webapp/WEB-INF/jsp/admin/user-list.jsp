<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Manage Users - Library Management System"/>
</jsp:include>

<h1>Manage Users</h1>

<jsp:include page="../common/messages.jsp"/>

<div class="page-actions">
    <a href="${pageContext.request.contextPath}/admin/users/create" class="btn btn-primary">
        Create New User
    </a>
</div>

<c:choose>
    <c:when test="${not empty users}">
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Role</th>
                    <th>Standing</th>
                    <th>Created Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.userId}</td>
                        <td><c:out value="${user.email}"/></td>
                        <td>
                            <c:out value="${user.firstName}"/>
                            <c:out value="${user.lastName}"/>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${user.roleId == 4}">
                                    <span class="badge badge-admin">Administrator</span>
                                </c:when>
                                <c:when test="${user.roleId == 3}">
                                    <span class="badge badge-librarian">Librarian</span>
                                </c:when>
                                <c:when test="${user.roleId == 2}">
                                    <span class="badge badge-user">User</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-guest">Guest</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${user.accountStanding == 1}">
                                    <span class="badge badge-success">Good Standing</span>
                                </c:when>
                                <c:when test="${user.accountStanding == 2}">
                                    <span class="badge badge-warning">Suspended</span>
                                </c:when>
                                <c:when test="${user.accountStanding == 3}">
                                    <span class="badge badge-danger">Banned</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-info">Pending</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            ${user.createdAt.toString().substring(0, 10)}
                        </td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/admin/users/view?userId=${user.userId}"
                               class="btn-small">View</a>
                            <a href="${pageContext.request.contextPath}/admin/users/edit?userId=${user.userId}"
                               class="btn-small">Edit</a>
                            <c:if test="${user.userId != sessionScope.currentUser.userId}">
                                <!-- Role Management -->
                                <c:choose>
                                    <c:when test="${user.roleId == 2}">
                                        <!-- User -> Librarian -->
                                        <form action="${pageContext.request.contextPath}/admin/users/role/update"
                                              method="post" style="display:inline;">
                                            <input type="hidden" name="userId" value="${user.userId}"/>
                                            <input type="hidden" name="roleId" value="3"/>
                                            <button type="submit" class="btn-small btn-primary" title="Promote to Librarian">
                                                ↑ Librarian
                                            </button>
                                        </form>
                                    </c:when>
                                    <c:when test="${user.roleId == 3}">
                                        <!-- Librarian -> User -->
                                        <form action="${pageContext.request.contextPath}/admin/users/role/update"
                                              method="post" style="display:inline;"
                                              onsubmit="return confirm('Demote this librarian to regular user?');">
                                            <input type="hidden" name="userId" value="${user.userId}"/>
                                            <input type="hidden" name="roleId" value="2"/>
                                            <button type="submit" class="btn-small btn-secondary" title="Demote to User">
                                                ↓ User
                                            </button>
                                        </form>
                                    </c:when>
                                </c:choose>
                                <!-- Delete -->
                                <form action="${pageContext.request.contextPath}/admin/users/delete"
                                      method="post" style="display:inline;"
                                      onsubmit="return confirm('Are you sure you want to delete this user?');">
                                    <input type="hidden" name="userId" value="${user.userId}"/>
                                    <button type="submit" class="btn-small btn-danger">Delete</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p class="no-data">No users found.</p>
    </c:otherwise>
</c:choose>

<jsp:include page="../common/footer.jsp"/>
