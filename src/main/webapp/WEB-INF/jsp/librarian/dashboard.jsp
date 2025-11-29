<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Librarian Dashboard - Library Management System"/>
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
h1 { font-size: 1.8rem !important; font-weight: 700 !important; color: #000 !important; letter-spacing: 0.5px; border-bottom: 3px solid #000 !important; }
</style>

<h1>Librarian Dashboard</h1>

<jsp:include page="../common/messages.jsp"/>

<div class="dashboard-grid">
    <!-- Statistics Cards -->
    <div class="stats-container">
        <div class="stat-card">
            <h3>Total Books</h3>
            <p class="stat-number">${totalBooks}</p>
        </div>

        <div class="stat-card">
            <h3>Total Copies</h3>
            <p class="stat-number">${totalCopies}</p>
        </div>

        <div class="stat-card">
            <h3>Active Checkouts</h3>
            <p class="stat-number">${activeCheckouts}</p>
        </div>

        <div class="stat-card">
            <h3>Overdue Items</h3>
            <p class="stat-number" style="color: ${overdueCheckouts > 0 ? '#e74c3c' : '#3498db'}">
                ${overdueCheckouts}
            </p>
        </div>

        <div class="stat-card">
            <h3>Total Members</h3>
            <p class="stat-number">${totalMembers}</p>
        </div>

        <div class="stat-card">
            <h3>Total Checkouts</h3>
            <p class="stat-number">${totalCheckouts}</p>
        </div>
    </div>

    <!-- Quick Actions -->
    <div class="quick-actions">
        <h2>Quick Actions</h2>
        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/librarian/checkout" class="btn btn-primary">
                Checkout Book
            </a>
            <a href="${pageContext.request.contextPath}/librarian/return" class="btn btn-primary">
                Return Book
            </a>
            <a href="${pageContext.request.contextPath}/librarian/books" class="btn btn-secondary">
                Manage Books
            </a>
            <a href="${pageContext.request.contextPath}/librarian/members" class="btn btn-secondary">
                Manage Members
            </a>
        </div>
    </div>

    <!-- Recent Checkouts -->
    <div class="recent-activity">
        <h2>Recent Checkouts</h2>
        <c:choose>
            <c:when test="${not empty recentCheckouts}">
                <table>
                    <thead>
                        <tr>
                            <th>Checkout ID</th>
                            <th>Member ID</th>
                            <th>Book Copy ID</th>
                            <th>Checkout Date</th>
                            <th>Due Date</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${recentCheckouts}" var="checkout">
                            <tr>
                                <td>${checkout.checkoutId}</td>
                                <td>${checkout.loanedTo}</td>
                                <td>${checkout.bookCopyId}</td>
                                <td>${checkout.checkoutDate}</td>
                                <td>${checkout.dueDate}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${checkout.returnDate != null}">
                                            <span class="badge badge-success">Returned</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-info">Active</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p class="no-data">No checkouts yet.</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
