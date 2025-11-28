<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Admin Dashboard - Library Management System"/>
</jsp:include>

<style>
.stat-card { background: #ffffff !important; color: #000 !important; border: 2px solid #e0e0e0 !important; border-left: 4px solid #000 !important; transition: all 0.3s; box-shadow: 0 2px 4px rgba(0,0,0,0.05); }
.stat-card:hover { transform: translateY(-3px); box-shadow: 0 4px 12px rgba(0,0,0,0.1); border-left-color: #666; }
.stat-card h3 { color: #666 !important; font-size: 0.85rem !important; text-transform: uppercase; letter-spacing: 1.5px; font-weight: 600; }
.stat-number { font-size: 2.5rem !important; color: #000 !important; font-weight: 700; margin-top: 0.5rem; }
.quick-actions { background: #fafafa !important; border: 1px solid #e0e0e0 !important; border-top: 3px solid #000 !important; }
.quick-actions h2 { color: #000 !important; font-weight: 600; }
.action-buttons .btn { padding: 0.8rem 1.5rem !important; font-weight: 600; transition: all 0.2s; border: 2px solid #000 !important; }
.action-buttons .btn-primary { background: #000 !important; color: #fff !important; }
.action-buttons .btn-primary:hover { background: #333 !important; transform: translateY(-2px); }
.action-buttons .btn-secondary { background: #fff !important; color: #000 !important; }
.action-buttons .btn-secondary:hover { background: #f5f5f5 !important; transform: translateY(-2px); }
.recent-activity { border: 1px solid #e0e0e0 !important; border-top: 3px solid #000 !important; }
.recent-activity h2 { color: #000 !important; font-weight: 600; }
h1 { font-size: 1.8rem !important; font-weight: 700 !important; color: #000 !important; letter-spacing: 0.5px; border-bottom: 3px solid #000 !important; }
</style>

<h1>Administrator Dashboard</h1>

<jsp:include page="../common/messages.jsp"/>

<div class="dashboard-grid">
    <!-- Statistics Cards -->
    <div class="stats-container">
        <div class="stat-card">
            <h3>Total Users</h3>
            <p class="stat-number">${totalUsers}</p>
        </div>

        <div class="stat-card">
            <h3>Administrators</h3>
            <p class="stat-number">${adminCount}</p>
        </div>

        <div class="stat-card">
            <h3>Librarians</h3>
            <p class="stat-number">${librarianCount}</p>
        </div>

        <div class="stat-card">
            <h3>Regular Users</h3>
            <p class="stat-number">${userCount}</p>
        </div>
    </div>

    <!-- Quick Actions -->
    <div class="quick-actions">
        <h2>Quick Actions</h2>
        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-primary">
                Manage Users
            </a>
            <a href="${pageContext.request.contextPath}/admin/audit-logs" class="btn btn-primary">
                View Audit Logs
            </a>
            <a href="${pageContext.request.contextPath}/admin/users/create" class="btn btn-secondary">
                Create New User
            </a>
        </div>
    </div>

    <!-- Recent Activity -->
    <div class="recent-activity">
        <h2>Recent System Activity</h2>
        <c:choose>
            <c:when test="${not empty recentLogs}">
                <table>
                    <thead>
                        <tr>
                            <th>Time</th>
                            <th>User ID</th>
                            <th>Action ID</th>
                            <th>Details</th>
                            <th>IP Address</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${recentLogs}" var="log">
                            <tr>
                                <td>
                                    ${log.createdAt.toString().replace('T', ' ').replace('Z', '')}
                                </td>
                                <td>${log.userId}</td>
                                <td>${log.actionId}</td>
                                <td><c:out value="${log.details}"/></td>
                                <td>${log.ipAddress}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p>No recent activity.</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
