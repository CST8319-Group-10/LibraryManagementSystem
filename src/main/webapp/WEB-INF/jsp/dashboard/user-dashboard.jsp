<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Member Dashboard - Library Management System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            color: white;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }
        .welcome {
            font-size: 2em;
        }
        .nav a {
            color: white;
            text-decoration: none;
            margin-left: 20px;
            padding: 10px 20px;
            border: 1px solid white;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .nav a:hover {
            background-color: rgba(255,255,255,0.2);
        }
        .dashboard-cards {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        .card {
            background: rgba(255,255,255,0.1);
            padding: 25px;
            border-radius: 10px;
            backdrop-filter: blur(10px);
        }
        .card h3 {
            margin-top: 0;
            color: #fff;
        }
        .features {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 15px;
            margin-top: 20px;
        }
        .feature-item {
            background: rgba(255,255,255,0.2);
            padding: 15px;
            border-radius: 8px;
            text-align: center;
            cursor: pointer;
            transition: transform 0.2s;
            text-decoration: none;
            color: white;
            display: block;
        }
        .feature-item:hover {
            transform: translateY(-2px);
            background: rgba(255,255,255,0.3);
        }
        .user-info {
            background: rgba(255,255,255,0.1);
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .message {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 8px;
            background: rgba(255,255,255,0.2);
        }
        .message.success {
            background: rgba(76, 175, 80, 0.3);
            border-left: 4px solid #4CAF50;
        }
        .message.error {
            background: rgba(244, 67, 54, 0.3);
            border-left: 4px solid #f44336;
        }
        .badge {
            padding: 5px 10px;
            border-radius: 5px;
            font-weight: bold;
        }
        .badge-success {
            background: rgba(76, 175, 80, 0.3);
            border: 1px solid #4CAF50;
        }
        .badge-warning {
            background: rgba(255, 193, 7, 0.3);
            border: 1px solid #FFC107;
        }
        .badge-danger {
            background: rgba(244, 67, 54, 0.3);
            border: 1px solid #f44336;
        }
        .badge-info {
            background: rgba(3, 169, 244, 0.3);
            border: 1px solid #03A9F4;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1 class="welcome">👤 Member Dashboard</h1>
            <div class="nav">
                <c:if test="${not empty sessionScope.currentUser}">
                    <span>Welcome, ${sessionScope.currentUser.firstName} ${sessionScope.currentUser.lastName}!</span>
                </c:if>
                <a href="${pageContext.request.contextPath}/logout">🚪 Logout</a>
            </div>
        </div>

        <!-- Messages -->
        <c:if test="${not empty success}">
            <div class="message success">✅ ${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="message error">❌ ${error}</div>
        </c:if>

        <div class="user-info">
            <h3>👤 Account Information</h3>
            <c:if test="${not empty sessionScope.currentUser}">
                <p><strong>Username:</strong> ${sessionScope.currentUser.email}</p>
                <p><strong>Email:</strong> ${sessionScope.currentUser.email}</p>
                <p><strong>Role:</strong>
                    <c:choose>
                        <c:when test="${sessionScope.currentUser.roleId == 4}">Administrator</c:when>
                        <c:when test="${sessionScope.currentUser.roleId == 3}">Librarian</c:when>
                        <c:when test="${sessionScope.currentUser.roleId == 2}">Registered User</c:when>
                        <c:otherwise>Guest</c:otherwise>
                    </c:choose>
                </p>
                <p><strong>Phone:</strong> ${sessionScope.currentUser.phone != null && sessionScope.currentUser.phone != '' ? sessionScope.currentUser.phone : 'Not provided'}</p>
                <p><strong>Account Standing:</strong>
                    <c:choose>
                        <c:when test="${sessionScope.currentUser.accountStanding == 1}">
                            <span class="badge badge-success">Good Standing</span>
                        </c:when>
                        <c:when test="${sessionScope.currentUser.accountStanding == 2}">
                            <span class="badge badge-warning">Suspended</span>
                        </c:when>
                        <c:when test="${sessionScope.currentUser.accountStanding == 3}">
                            <span class="badge badge-danger">Banned</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-info">Pending Approval</span>
                        </c:otherwise>
                    </c:choose>
                </p>
            </c:if>
        </div>

        <div class="dashboard-cards">
            <div class="card">
                <h3>📚 Book Management</h3>
                <div class="features">
                    <a href="${pageContext.request.contextPath}/user/searchForBook" class="feature-item">Search Books</a>
                    <a href="${pageContext.request.contextPath}/user/checkBookAvailability" class="feature-item">Check Availability</a>
                    <a href="${pageContext.request.contextPath}/user/browseByGenre" class="feature-item">Browse Catalog</a>
                </div>
            </div>

            <div class="card">
                <h3>📖 My Library</h3>
                <div class="features">
                    <a href="${pageContext.request.contextPath}/member/borrowed" class="feature-item">Borrowed Books</a>
                    <a href="${pageContext.request.contextPath}/member/history" class="feature-item">Reading History</a>
                    <div class="feature-item" onclick="alert('Favorites feature coming soon')">Favorites</div>
                </div>
            </div>

            <div class="card">
                <h3>⏰ Reservations & Holds</h3>
                <div class="features">
                    <a href="${pageContext.request.contextPath}/user/placeHoldOnBook" class="feature-item">Place Hold on Book</a>
                    <div class="feature-item" onclick="alert('View Waitlist feature coming soon')">View Waitlist</div>
                    <div class="feature-item" onclick="alert('Reservation Status feature coming soon')">Reservation Status</div>
                </div>
            </div>

            <div class="card">
                <h3>👤 Account Management</h3>
                <div class="features">
                    <a href="${pageContext.request.contextPath}/user/profile" class="feature-item">Profile Settings</a>
                    <a href="${pageContext.request.contextPath}/user/change-password" class="feature-item">Change Password</a>
                    <div class="feature-item" onclick="alert('Notification Preferences feature coming soon')">Notification Preferences</div>
                </div>
            </div>
        </div>

        <div style="margin-top: 30px; text-align: center; opacity: 0.8;">
            <p>Member Role | All member features operational</p>
        </div>
    </div>
</body>
</html>
