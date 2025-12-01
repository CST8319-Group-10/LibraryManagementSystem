<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Member List - Library Management System</title>
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
            max-width: 1400px;
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
            background-color: #f8f9fa;
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
        .badge-success {
            background-color: #28a745;
            color: white;
        }
        .badge-danger {
            background-color: #dc3545;
            color: white;
        }
        .badge-warning {
            background-color: #ffc107;
            color: #333;
        }
        .overdue-count {
            color: #e74c3c;
            font-weight: bold;
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
        .btn-small {
            padding: 6px 14px;
            font-size: 0.85em;
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
        }
        .btn-danger {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
        }
        .btn-danger:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(245, 87, 108, 0.4);
        }
        .btn-secondary {
            background: linear-gradient(135deg, #ff7e5f 0%, #feb47b 100%);
            color: white;
        }
        .btn-secondary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(255, 126, 95, 0.4);
        }
        .no-data {
            text-align: center;
            padding: 60px 20px;
            color: #666;
            font-size: 1.1em;
        }
        .back-section {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #e0e0e0;
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
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="content-wrapper">
            <h1>Member List</h1>

            <c:if test="${not empty success}">
                <div class="message success">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="message error">${error}</div>
            </c:if>

            <c:choose>
                <c:when test="${not empty members}">
                    <table>
                        <thead>
                            <tr>
                                <th>User ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Account Standing</th>
                                <th>Overdue Count</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${members}" var="member">
                                <tr>
                                    <td>${member.userId}</td>
                                    <td>${member.firstName} ${member.lastName}</td>
                                    <td>${member.email}</td>
                                    <td>${member.phone}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${member.accountStanding == 1}">
                                                <span class="badge badge-success">Good Standing</span>
                                            </c:when>
                                            <c:when test="${member.accountStanding == 2}">
                                                <span class="badge badge-danger">Suspended</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge badge-warning">Unknown</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:set var="overdueCount" value="${overdueCountMap[member.userId]}"/>
                                        <c:choose>
                                            <c:when test="${overdueCount > 0}">
                                                <span class="overdue-count">${overdueCount}</span>
                                            </c:when>
                                            <c:otherwise>
                                                ${overdueCount}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="actions">
                                        <c:choose>
                                            <c:when test="${member.accountStanding == 1}">
                                                <form method="post" action="${pageContext.request.contextPath}/librarian/members/deactivate"
                                                      style="display: inline;"
                                                      onsubmit="return confirm('Are you sure you want to suspend this member?');">
                                                    <input type="hidden" name="id" value="${member.userId}">
                                                    <button type="submit" class="btn btn-small btn-danger">Suspend</button>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <form method="post" action="${pageContext.request.contextPath}/librarian/members/activate"
                                                      style="display: inline;"
                                                      onsubmit="return confirm('Are you sure you want to activate this member?');">
                                                    <input type="hidden" name="id" value="${member.userId}">
                                                    <button type="submit" class="btn btn-small btn-primary">Activate</button>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="no-data">
                        <p>No members found.</p>
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="back-section">
                <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">Back to Dashboard</a>
            </div>
        </div>
    </div>
</body>
</html>
