<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Members - Library Management System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #ff7e5f 0%, #feb47b 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .container {
            max-width: 1600px;
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
        .page-actions {
            margin-bottom: 25px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
            background: white;
        }
        thead {
            background: linear-gradient(135deg, #ff7e5f 0%, #feb47b 100%);
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
            background-color: #fff8f0;
        }
        td {
            padding: 15px;
            color: #333;
        }
        .badge {
            display: inline-block;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 0.85em;
            font-weight: 600;
        }
        .badge-user {
            background-color: #0dcaf0;
            color: white;
        }
        .badge-success {
            background-color: #28a745;
            color: white;
        }
        .badge-warning {
            background-color: #ffc107;
            color: #333;
        }
        .badge-danger {
            background-color: #dc3545;
            color: white;
        }
        .badge-info {
            background-color: #17a2b8;
            color: white;
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
            background: #f5f5f5;
            color: #333;
        }
        .btn:hover {
            background: #e0e0e0;
        }
        .btn-small {
            padding: 6px 12px;
            font-size: 0.85em;
            margin-right: 5px;
            background: #f5f5f5;
            color: #333;
            border: 1px solid #ddd;
            text-decoration: none;
        }
        .btn-small:hover {
            background: #e0e0e0;
        }
        .btn-primary {
            background: linear-gradient(135deg, #ff7e5f 0%, #feb47b 100%);
            color: white;
        }
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(255, 126, 95, 0.4);
        }
        .btn-small.btn-warning {
            background: linear-gradient(135deg, #ffa726 0%, #fb8c00 100%);
            color: white;
            border: none;
        }
        .btn-small.btn-warning:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(255, 167, 38, 0.4);
        }
        .btn-small.btn-success {
            background: linear-gradient(135deg, #66bb6a 0%, #43a047 100%);
            color: white;
            border: none;
        }
        .btn-small.btn-success:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(102, 187, 106, 0.4);
        }
        .btn-small.btn-danger {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
            border: none;
        }
        .btn-small.btn-danger:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(245, 87, 108, 0.4);
        }
        .no-data {
            text-align: center;
            padding: 60px 20px;
            color: #666;
            font-size: 1.1em;
        }
        .actions {
            white-space: nowrap;
        }
        .search-box {
            margin-bottom: 25px;
        }
        .search-input {
            width: 100%;
            max-width: 500px;
            padding: 12px 20px;
            font-size: 1em;
            border: 2px solid #ced4da;
            border-radius: 8px;
            transition: all 0.3s;
        }
        .search-input:focus {
            outline: none;
            border-color: #ff7e5f;
            box-shadow: 0 0 0 3px rgba(255, 126, 95, 0.1);
        }
        .info-banner {
            background-color: #e7f3ff;
            border-left: 4px solid #2196F3;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 4px;
            color: #1976D2;
        }
        @media (max-width: 768px) {
            .content-wrapper {
                padding: 20px;
            }
            table {
                font-size: 0.9em;
            }
            th, td {
                padding: 10px;
            }
            h1 {
                font-size: 1.5em;
            }
            .btn-small {
                display: block;
                margin: 5px 0;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="content-wrapper">
            <h1>Manage Member Accounts</h1>

            <div class="info-banner">
                <strong>Note:</strong> As a librarian, you can view and manage regular member accounts.
                You can update their account standing (activate/suspend/ban) but cannot delete accounts or create new users.
            </div>

            <c:if test="${not empty success}">
                <div class="message success">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="message error">${error}</div>
            </c:if>

            <div class="search-box">
                <input type="text"
                       id="searchInput"
                       class="search-input"
                       placeholder="Search members by name or email..."
                       onkeyup="filterUsers()">
            </div>

            <div class="page-actions">
                <a href="${pageContext.request.contextPath}/librarian/users/create" class="btn btn-primary">
                    Create New Member
                </a>
                <div>
                    <span style="color: #666;">Total Members: <strong>${users.size()}</strong></span>
                    <a href="${pageContext.request.contextPath}/librarian/dashboard" class="btn" style="margin-left: 15px;">Back to Dashboard</a>
                </div>
            </div>

            <c:choose>
                <c:when test="${not empty users}">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Email</th>
                                <th>Name</th>
                                <th>Phone</th>
                                <th>Account Standing</th>
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
                                        <c:out value="${user.phone != null && user.phone != '' ? user.phone : 'N/A'}"/>
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
                                        <!-- Edit -->
                                        <a href="${pageContext.request.contextPath}/librarian/users/edit?userId=${user.userId}"
                                           class="btn-small">Edit</a>

                                        <!-- Account Standing Management -->
                                        <c:choose>
                                            <c:when test="${user.accountStanding == 1}">
                                                <!-- Good Standing -> Suspend -->
                                                <form action="${pageContext.request.contextPath}/librarian/members/deactivate"
                                                      method="post" style="display:inline;"
                                                      onsubmit="return confirm('Suspend this member account?');">
                                                    <input type="hidden" name="userId" value="${user.userId}"/>
                                                    <button type="submit" class="btn-small btn-warning" title="Suspend Account">
                                                        Suspend
                                                    </button>
                                                </form>
                                            </c:when>
                                            <c:when test="${user.accountStanding == 2}">
                                                <!-- Suspended -> Activate -->
                                                <form action="${pageContext.request.contextPath}/librarian/members/activate"
                                                      method="post" style="display:inline;">
                                                    <input type="hidden" name="userId" value="${user.userId}"/>
                                                    <button type="submit" class="btn-small btn-success" title="Activate Account">
                                                        Activate
                                                    </button>
                                                </form>
                                            </c:when>
                                            <c:when test="${user.accountStanding == 3}">
                                                <!-- Banned -> Activate -->
                                                <form action="${pageContext.request.contextPath}/librarian/members/activate"
                                                      method="post" style="display:inline;">
                                                    <input type="hidden" name="userId" value="${user.userId}"/>
                                                    <button type="submit" class="btn-small btn-success" title="Activate Account">
                                                        Activate
                                                    </button>
                                                </form>
                                            </c:when>
                                        </c:choose>

                                        <!-- Delete -->
                                        <form action="${pageContext.request.contextPath}/librarian/users/delete"
                                              method="post" style="display:inline;"
                                              onsubmit="return confirm('Are you sure you want to delete this member account? This action cannot be undone.');">
                                            <input type="hidden" name="userId" value="${user.userId}"/>
                                            <button type="submit" class="btn-small btn-danger">Delete</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p class="no-data">No member accounts found.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script>
        function filterUsers() {
            const searchInput = document.getElementById('searchInput');
            const filter = searchInput.value.toLowerCase();
            const table = document.querySelector('table');
            const tbody = table ? table.querySelector('tbody') : null;

            if (!tbody) return;

            const rows = tbody.getElementsByTagName('tr');

            for (let i = 0; i < rows.length; i++) {
                const row = rows[i];
                const email = row.cells[1] ? row.cells[1].textContent.toLowerCase() : '';
                const name = row.cells[2] ? row.cells[2].textContent.toLowerCase() : '';

                if (email.includes(filter) || name.includes(filter)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            }
        }
    </script>
</body>
</html>
