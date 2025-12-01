<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Library Management System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .container {
            max-width: 800px;
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            text-align: center;
        }
        h1 {
            color: #333;
            margin-bottom: 20px;
        }
        p {
            color: #666;
            margin-bottom: 30px;
            font-size: 18px;
        }
        .button-group {
            display: flex;
            gap: 20px;
            justify-content: center;
            margin-top: 30px;
        }
        .btn {
            padding: 15px 30px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: transform 0.2s;
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        .btn:hover {
            transform: translateY(-2px);
        }
        .features {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-top: 40px;
        }
        .feature {
            padding: 20px;
            background: #f8f9fa;
            border-radius: 8px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>📚 Welcome to Library Management System</h1>
        <p>Manage your library efficiently with our comprehensive system</p>

        <div class="button-group">
            <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">Login</a>
            <a href="${pageContext.request.contextPath}/register" class="btn btn-secondary">Register</a>
        </div>

        <div class="features">
            <div class="feature">
                <h3>🔐 Secure Authentication</h3>
                <p>Role-based access control</p>
            </div>
            <div class="feature">
                <h3>📖 Book Management</h3>
                <p>Complete catalog system</p>
            </div>
            <div class="feature">
                <h3>👥 User Management</h3>
                <p>Member and staff accounts</p>
            </div>
        </div>
    </div>
</body>
</html>
