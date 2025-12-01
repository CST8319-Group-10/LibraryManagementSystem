<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Librarian Dashboard - Library Management System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #ff7e5f 0%, #feb47b 100%);
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
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1 class="welcome">📚 Librarian Dashboard</h1>
            <div class="nav">
                <c:if test="${not empty sessionScope.user}">
                    <span>Welcome, ${sessionScope.user.firstName} ${sessionScope.user.lastName}!</span>
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
            <c:if test="${not empty sessionScope.user}">
                <p><strong>Username:</strong> ${sessionScope.user.username}</p>
                <p><strong>Email:</strong> ${sessionScope.user.email}</p>
                <p><strong>Role:</strong> ${sessionScope.user.role.name}</p>
                <p><strong>Phone:</strong> ${sessionScope.user.phoneNumber != null ? sessionScope.user.phoneNumber : 'Not provided'}</p>
            </c:if>
        </div>

        <div class="dashboard-cards">
            <div class="card">
                <h3>📖 Circulation Desk</h3>
                <div class="features">
                    <a href="${pageContext.request.contextPath}/librarian/checkout" class="feature-item">Check Out Books</a>
                    <a href="${pageContext.request.contextPath}/librarian/return" class="feature-item">Process Returns</a>
                    <a href="${pageContext.request.contextPath}/librarian/members" class="feature-item">View Members</a>
                </div>
            </div>

            <div class="card">
                <h3>📚 Catalog Management</h3>
                <div class="features">
                    <a href="${pageContext.request.contextPath}/librarian/books/new" class="feature-item">Add New Books</a>
                    <a href="${pageContext.request.contextPath}/librarian/books" class="feature-item">Manage Books</a>
                    <a href="${pageContext.request.contextPath}/librarian/books" class="feature-item">Update Book Details</a>
                </div>
            </div>

            <div class="card">
                <h3>👥 Member Management</h3>
                <div class="features">
                    <a href="${pageContext.request.contextPath}/librarian/members" class="feature-item">View Member Accounts</a>
                    <a href="${pageContext.request.contextPath}/admin/users" class="feature-item">Manage User Accounts</a>
                    <a href="${pageContext.request.contextPath}/librarian/members/activate" class="feature-item">Activate/Deactivate Members</a>
                    <a href="${pageContext.request.contextPath}/librarian/fees" class="feature-item">Process Fee Payments</a>
                </div>
            </div>

            <div class="card">
                <h3>⏰ Reservation System</h3>
                <div class="features">
                    <div class="feature-item" onclick="alert('Manage Holds feature coming soon')">Manage Holds</div>
                    <div class="feature-item" onclick="alert('Waitlist Management feature coming soon')">Waitlist Management</div>
                    <div class="feature-item" onclick="alert('Notify Members feature coming soon')">Notify Members</div>
                </div>
            </div>
        </div>

        <div style="margin-top: 30px; text-align: center; opacity: 0.8;">
            <p>Librarian Role | All circulation features operational</p>
        </div>
    </div>
</body>
</html>
