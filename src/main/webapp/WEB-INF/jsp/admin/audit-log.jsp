<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Audit Logs - Library Management System"/>
</jsp:include>

<style>
.filter-form { background: #fafafa; padding: 1.5rem; border: 1px solid #e0e0e0; border-top: 3px solid #000; margin-bottom: 2rem; border-radius: 4px; }
.filter-row { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1rem; margin-bottom: 1rem; }
.filter-actions { display: flex; gap: 1rem; }
.pagination { display: flex; justify-content: center; align-items: center; gap: 0.5rem; margin-top: 2rem; }
.pagination a, .pagination span { padding: 0.5rem 0.75rem; border: 1px solid #000; text-decoration: none; color: #000; }
.pagination a:hover { background: #000; color: #fff; }
.pagination .current { background: #000; color: #fff; font-weight: bold; }
.stats { background: #fff; padding: 1rem; border-left: 4px solid #000; margin-bottom: 1.5rem; }
h1 { font-size: 1.8rem !important; font-weight: 700 !important; color: #000 !important; letter-spacing: 0.5px; border-bottom: 3px solid #000 !important; }
</style>

<h1>Audit Logs</h1>

<jsp:include page="../common/messages.jsp"/>

<!-- Filters -->
<div class="filter-form">
    <h2>Filter Logs</h2>
    <form method="get" action="${pageContext.request.contextPath}/admin/audit-logs">
        <div class="filter-row">
            <div class="form-group">
                <label for="userId">User</label>
                <select name="userId" id="userId">
                    <option value="">All Users</option>
                    <c:forEach items="${users}" var="user">
                        <option value="${user.userId}"
                                ${filterUserId != null && filterUserId == user.userId ? 'selected' : ''}>
                            <c:out value="${user.email}"/> (${user.firstName} ${user.lastName})
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="roleId">User Role</label>
                <select name="roleId" id="roleId">
                    <option value="">All Roles</option>
                    <c:forEach items="${roles}" var="role">
                        <option value="${role.roleId}"
                                ${filterRoleId != null && filterRoleId == role.roleId ? 'selected' : ''}>
                            <c:out value="${role.name}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="actionId">Event Type</label>
                <select name="actionId" id="actionId">
                    <option value="">All Events</option>
                    <c:forEach items="${actions}" var="action">
                        <option value="${action.actionId}"
                                ${filterActionId != null && filterActionId == action.actionId ? 'selected' : ''}>
                            <c:out value="${action.action}"/> - <c:out value="${action.description}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="filter-row">
            <div class="form-group">
                <label for="startDate">Start Date</label>
                <input type="date" name="startDate" id="startDate"
                       value="${filterStartDate != null ? filterStartDate : ''}"/>
            </div>

            <div class="form-group">
                <label for="endDate">End Date</label>
                <input type="date" name="endDate" id="endDate"
                       value="${filterEndDate != null ? filterEndDate : ''}"/>
            </div>
        </div>

        <div class="filter-actions">
            <button type="submit" class="btn btn-primary">Apply Filters</button>
            <a href="${pageContext.request.contextPath}/admin/audit-logs" class="btn btn-secondary">Clear Filters</a>
        </div>
    </form>
</div>

<!-- Stats -->
<div class="stats">
    <strong>Total Results:</strong> ${totalCount} log entries found
    <c:if test="${totalPages > 1}">
        (Page ${currentPage} of ${totalPages})
    </c:if>
</div>

<!-- Audit Log Table -->
<c:choose>
    <c:when test="${not empty logs}">
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Timestamp</th>
                    <th>User</th>
                    <th>Role</th>
                    <th>Event</th>
                    <th>Details</th>
                    <th>IP Address</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${logs}" var="log">
                    <tr>
                        <td>${log.auditEntryId}</td>
                        <td>${log.createdAt.toString().replace('T', ' ').replace('Z', '').substring(0, 19)}</td>
                        <td>
                            <c:out value="${log.userEmail}"/><br/>
                            <small><c:out value="${log.userFirstName} ${log.userLastName}"/></small>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${log.roleId == 4}">
                                    <span class="badge badge-admin">Administrator</span>
                                </c:when>
                                <c:when test="${log.roleId == 3}">
                                    <span class="badge badge-librarian">Librarian</span>
                                </c:when>
                                <c:when test="${log.roleId == 2}">
                                    <span class="badge badge-user">User</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-guest">Guest</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <strong><c:out value="${log.actionName}"/></strong><br/>
                            <small><c:out value="${log.actionDescription}"/></small>
                        </td>
                        <td><c:out value="${log.details}"/></td>
                        <td>${log.ipAddress}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Pagination -->
        <c:if test="${totalPages > 1}">
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="?page=${currentPage - 1}&userId=${filterUserId}&roleId=${filterRoleId}&actionId=${filterActionId}&startDate=${filterStartDate}&endDate=${filterEndDate}">
                        &laquo; Previous
                    </a>
                </c:if>

                <c:forEach begin="1" end="${totalPages}" var="i">
                    <c:choose>
                        <c:when test="${i == currentPage}">
                            <span class="current">${i}</span>
                        </c:when>
                        <c:otherwise>
                            <a href="?page=${i}&userId=${filterUserId}&roleId=${filterRoleId}&actionId=${filterActionId}&startDate=${filterStartDate}&endDate=${filterEndDate}">
                                ${i}
                            </a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <a href="?page=${currentPage + 1}&userId=${filterUserId}&roleId=${filterRoleId}&actionId=${filterActionId}&startDate=${filterStartDate}&endDate=${filterEndDate}">
                        Next &raquo;
                    </a>
                </c:if>
            </div>
        </c:if>
    </c:when>
    <c:otherwise>
        <p class="no-data">No audit log entries found matching the selected filters.</p>
    </c:otherwise>
</c:choose>

<jsp:include page="../common/footer.jsp"/>
