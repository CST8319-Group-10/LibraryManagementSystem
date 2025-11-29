<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ac.cst8319.lms.model.UserAccount" %>
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
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1 class="welcome">👤 Member Dashboard</h1>
            <div class="nav">
                <%
                    UserAccount user = (UserAccount) session.getAttribute("user");
                    if (user != null) {
                %>
                    <span>Welcome, <%= user.getFirstName() %> <%= user.getLastName() %>!</span>
                <%
                    }
                %>
                <a href="<%= request.getContextPath() %>/logout">🚪 Logout</a>
            </div>
        </div>

        <div class="user-info">
            <h3>👤 Account Information</h3>
            <%
                if (user != null) {
            %>
                <p><strong>Username:</strong> <%= user.getUsername() %></p>
                <p><strong>Email:</strong> <%= user.getEmail() %></p>
                <p><strong>Role:</strong> <%= user.getRole().getName() %></p>
                <p><strong>Phone:</strong> <%= user.getPhoneNumber() != null ? user.getPhoneNumber() : "Not provided" %></p>
            <%
                }
            %>
        </div>

        <div class="dashboard-cards">
            <div class="card">
                <h3>📚 Book Management</h3>
                <div class="features">
                    <div class="feature-item" onclick="alert('Search Books feature coming soon')">Search Books</div>
                    <div class="feature-item" onclick="alert('Browse Catalog feature coming soon')">Browse Catalog</div>
                    <div class="feature-item" onclick="alert('View Availability feature coming soon')">View Availability</div>
                </div>
            </div>

            <div class="card">
                <h3>📖 My Library</h3>
                <div class="features">
                    <div class="feature-item" onclick="alert('Borrowed Books feature coming soon')">Borrowed Books</div>
                    <div class="feature-item" onclick="alert('Reading History feature coming soon')">Reading History</div>
                    <div class="feature-item" onclick="alert('Favorites feature coming soon')">Favorites</div>
                </div>
            </div>

            <div class="card">
                <h3>⏰ Reservations & Holds</h3>
                <div class="features">
                    <div class="feature-item" onclick="alert('Place Holds feature coming soon')">Place Holds</div>
                    <div class="feature-item" onclick="alert('View Waitlist feature coming soon')">View Waitlist</div>
                    <div class="feature-item" onclick="alert('Reservation Status feature coming soon')">Reservation Status</div>
                </div>
            </div>

            <div class="card">
                <h3>👤 Account Management</h3>
                <div class="features">
                    <div class="feature-item" onclick="alert('Profile Settings feature coming soon')">Profile Settings</div>
                    <div class="feature-item" onclick="alert('Change Password feature coming soon')">Change Password</div>
                    <div class="feature-item" onclick="alert('Notification Preferences feature coming soon')">Notification Preferences</div>
                </div>
            </div>
        </div>

        <div style="margin-top: 30px; text-align: center; opacity: 0.8;">
            <p>Group 1: Authentication Successful | Role: MEMBER</p>
        </div>
    </div>
</body>
</html>