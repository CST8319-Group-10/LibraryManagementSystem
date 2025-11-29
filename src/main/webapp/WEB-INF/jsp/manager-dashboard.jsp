<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ac.cst8319.lms.model.UserAccount" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manager Dashboard - Library Management System</title>
    <style>
        body { 
            font-family: Arial, sans-serif; 
            margin: 0; 
            padding: 0; 
            background: linear-gradient(135deg, #4CA1AF 0%, #2C3E50 100%);
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
        .admin-section {
            background: rgba(255,255,255,0.15);
            padding: 20px;
            border-radius: 10px;
            margin-top: 30px;
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
            <h1 class="welcome">⚙️ Manager Dashboard</h1>
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
                <h3>👥 Staff Management</h3>
                <div class="features">
                    <div class="feature-item" onclick="alert('Add Librarians feature coming soon')">Add Librarians</div>
                    <div class="feature-item" onclick="alert('Manage Staff Accounts feature coming soon')">Manage Staff Accounts</div>
                    <div class="feature-item" onclick="alert('Role Assignments feature coming soon')">Role Assignments</div>
                </div>
            </div>

            <div class="card">
                <h3>📊 System Reports</h3>
                <div class="features">
                    <div class="feature-item" onclick="alert('Usage Analytics feature coming soon')">Usage Analytics</div>
                    <div class="feature-item" onclick="alert('Financial Reports feature coming soon')">Financial Reports</div>
                    <div class="feature-item" onclick="alert('Performance Metrics feature coming soon')">Performance Metrics</div>
                </div>
            </div>

            <div class="card">
                <h3>🔐 System Administration</h3>
                <div class="features">
                    <div class="feature-item" onclick="alert('Audit Logs feature coming soon')">Audit Logs</div>
                    <div class="feature-item" onclick="alert('System Settings feature coming soon')">System Settings</div>
                    <div class="feature-item" onclick="alert('Backup Management feature coming soon')">Backup Management</div>
                </div>
            </div>

            <div class="card">
                <h3>📚 Library Operations</h3>
                <div class="features">
                    <div class="feature-item" onclick="alert('Inventory Management feature coming soon')">Inventory Management</div>
                    <div class="feature-item" onclick="alert('Policy Configuration feature coming soon')">Policy Configuration</div>
                    <div class="feature-item" onclick="alert('Budget Planning feature coming soon')">Budget Planning</div>
                </div>
            </div>
        </div>

        <div class="admin-section">
            <h3>🔍 Administrative Tools</h3>
            <div class="features">
                <div class="feature-item" onclick="location.href='<%= request.getContextPath() %>/users'">View All User Accounts</div>
                <div class="feature-item" onclick="alert('System Audit Trail feature coming soon')">System Audit Trail</div>
                <div class="feature-item" onclick="alert('Database Management feature coming soon')">Database Management</div>
                <div class="feature-item" onclick="alert('Security Settings feature coming soon')">Security Settings</div>
            </div>
        </div>

        <div style="margin-top: 30px; text-align: center; opacity: 0.8;">
            <p>Group 1: Authentication Successful | Role: MANAGER | Full System Access</p>
        </div>
    </div>
</body>
</html>