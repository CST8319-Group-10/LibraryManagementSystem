<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Audit Logs - Library Management System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #4CA1AF 0%, #2C3E50 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .container {
            max-width: 1800px;
            margin: 0 auto;
        }
        .content-wrapper {
            background: white;
            border-radius: 15px;
            box-shadow: 0 8px 32px rgba(0,0,0,0.1);
            padding: 40px;
            margin-top: 20px;
        }
        h1 {
            color: #333;
            margin-bottom: 30px;
            font-size: 2em;
        }
        h2 {
            color: #333;
            margin-bottom: 15px;
            font-size: 1.3em;
        }
        .message {
            padding: 15px 20px;
            margin-bottom: 20px;
            border-radius: 8px;
            font-weight: 500;
        }
        .message.success {
            background-color: #d4edda;
            color: #155724;
            border-left: 4px solid #28a745;
        }
        .message.error {
            background-color: #f8d7da;
            color: #721c24;
            border-left: 4px solid #dc3545;
        }
        .filter-form {
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            padding: 25px;
            border-radius: 10px;
            margin-bottom: 25px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            border: 1px solid #dee2e6;
        }
        .filter-row {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 15px;
            margin-bottom: 15px;
        }
        .form-group {
            display: flex;
            flex-direction: column;
        }
        .form-group label {
            margin-bottom: 5px;
            font-weight: 600;
            color: #495057;
            font-size: 0.9em;
        }
        .form-group select,
        .form-group input[type="date"] {
            padding: 10px;
            border: 2px solid #ced4da;
            border-radius: 6px;
            font-size: 0.95em;
            transition: all 0.3s;
        }
        .form-group select:focus,
        .form-group input[type="date"]:focus {
            outline: none;
            border-color: #4CA1AF;
            box-shadow: 0 0 0 3px rgba(76, 161, 175, 0.1);
        }
        .filter-actions {
            display: flex;
            gap: 10px;
            margin-top: 15px;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 0.95em;
            font-weight: 500;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s;
        }
        .btn-primary {
            background: linear-gradient(135deg, #4CA1AF 0%, #2C3E50 100%);
            color: white;
        }
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(76, 161, 175, 0.4);
        }
        .btn-secondary {
            background: linear-gradient(135deg, #6c757d 0%, #495057 100%);
            color: white;
        }
        .btn-secondary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(108, 117, 125, 0.4);
        }
        .stats {
            background: linear-gradient(135deg, #e7f5ff 0%, #d0ebff 100%);
            padding: 15px 20px;
            border-left: 4px solid #4CA1AF;
            margin-bottom: 20px;
            border-radius: 6px;
            font-size: 0.95em;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
            background: white;
        }
        thead {
            background: linear-gradient(135deg, #4CA1AF 0%, #2C3E50 100%);
            color: white;
        }
        th {
            padding: 15px;
            text-align: left;
            font-weight: 600;
            font-size: 0.95em;
        }
        tbody tr {
            border-bottom: 1px solid #e0e0e0;
            transition: background-color 0.2s;
        }
        tbody tr:hover {
            background-color: #f8f9fa;
        }
        td {
            padding: 15px;
            color: #333;
        }
        td small {
            color: #6c757d;
            font-size: 0.85em;
        }
        .badge {
            display: inline-block;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 0.85em;
            font-weight: 600;
        }
        .badge-admin {
            background-color: #dc3545;
            color: white;
        }
        .badge-librarian {
            background-color: #fd7e14;
            color: white;
        }
        .badge-user {
            background-color: #0dcaf0;
            color: white;
        }
        .badge-guest {
            background-color: #6c757d;
            color: white;
        }
        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 8px;
            margin-top: 30px;
        }
        .pagination a,
        .pagination span {
            padding: 8px 12px;
            border: 2px solid #4CA1AF;
            text-decoration: none;
            color: #4CA1AF;
            border-radius: 6px;
            transition: all 0.3s;
            font-weight: 500;
        }
        .pagination a:hover {
            background: linear-gradient(135deg, #4CA1AF 0%, #2C3E50 100%);
            color: white;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(76, 161, 175, 0.3);
        }
        .pagination .current {
            background: linear-gradient(135deg, #4CA1AF 0%, #2C3E50 100%);
            color: white;
            font-weight: bold;
            border-color: #4CA1AF;
        }
        .no-data {
            text-align: center;
            padding: 60px 20px;
            color: #666;
            font-size: 1.1em;
        }
        .back-button {
            margin-top: 30px;
            text-align: center;
        }
        @media (max-width: 768px) {
            .content-wrapper {
                padding: 20px;
            }
            table {
                font-size: 0.85em;
            }
            th, td {
                padding: 10px;
            }
            h1 {
                font-size: 1.5em;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="content-wrapper">
            <h1>Audit Logs</h1>

            <c:if test="${not empty success}">
                <div class="message success">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="message error">${error}</div>
            </c:if>

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
                    <table>
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

            <div class="back-button">
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-primary">Back to Dashboard</a>
            </div>
        </div>
    </div>
</body>
</html>
