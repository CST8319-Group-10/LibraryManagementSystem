<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password - Library Management System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .container {
            max-width: 600px;
            width: 100%;
        }
        .content-wrapper {
            background: white;
            border-radius: 15px;
            box-shadow: 0 8px 32px rgba(0,0,0,0.1);
            padding: 40px;
        }
        h1 {
            color: #333;
            margin-bottom: 10px;
            font-size: 2em;
            text-align: center;
        }
        .subtitle {
            text-align: center;
            color: #666;
            margin-bottom: 30px;
            font-size: 0.95em;
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
        .form-group {
            margin-bottom: 25px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 600;
            font-size: 0.95em;
        }
        input[type="password"] {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 1em;
            font-family: inherit;
            transition: border-color 0.3s, box-shadow 0.3s;
        }
        input:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }
        small {
            display: block;
            margin-top: 5px;
            color: #666;
            font-size: 0.85em;
        }
        .password-requirements {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            border-left: 4px solid #667eea;
        }
        .password-requirements h3 {
            font-size: 0.9em;
            color: #667eea;
            margin-bottom: 8px;
        }
        .password-requirements ul {
            list-style: none;
            padding-left: 0;
        }
        .password-requirements li {
            color: #666;
            font-size: 0.85em;
            padding: 3px 0;
            padding-left: 20px;
            position: relative;
        }
        .password-requirements li:before {
            content: "✓";
            position: absolute;
            left: 0;
            color: #667eea;
            font-weight: bold;
        }
        .form-actions {
            display: flex;
            gap: 15px;
            margin-top: 30px;
        }
        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 1em;
            font-weight: 600;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s;
            text-align: center;
            flex: 1;
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
        }
        .btn:not(.btn-primary) {
            background: #f5f5f5;
            color: #333;
            border: 2px solid #e0e0e0;
        }
        .btn:not(.btn-primary):hover {
            background: #e0e0e0;
        }
        .security-notice {
            background: #fff3cd;
            border-left: 4px solid #ffc107;
            padding: 12px;
            border-radius: 4px;
            margin-bottom: 20px;
            font-size: 0.9em;
            color: #856404;
        }
        @media (max-width: 768px) {
            .content-wrapper {
                padding: 25px;
            }
            h1 {
                font-size: 1.5em;
            }
            .form-actions {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="content-wrapper">
            <h1>🔒 Change Password</h1>
            <p class="subtitle">Update your account password</p>

            <c:if test="${not empty success}">
                <div class="message success">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="message error">${error}</div>
            </c:if>

            <div class="security-notice">
                <strong>Security Tip:</strong> Choose a strong password that you don't use for other accounts.
            </div>

            <div class="password-requirements">
                <h3>Password Requirements:</h3>
                <ul>
                    <li>At least 8 characters long</li>
                    <li>Contains at least one uppercase letter (A-Z)</li>
                    <li>Contains at least one lowercase letter (a-z)</li>
                    <li>Contains at least one number (0-9)</li>
                </ul>
            </div>

            <form action="${pageContext.request.contextPath}/user/change-password" method="post">
                <div class="form-group">
                    <label for="oldPassword">Current Password: *</label>
                    <input type="password" id="oldPassword" name="oldPassword" required
                           placeholder="Enter your current password">
                </div>

                <div class="form-group">
                    <label for="newPassword">New Password: *</label>
                    <input type="password" id="newPassword" name="newPassword" required
                           placeholder="Enter your new password">
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Confirm New Password: *</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required
                           placeholder="Re-enter your new password">
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Change Password</button>
                    <a href="${pageContext.request.contextPath}/dashboard" class="btn">Cancel</a>
                </div>
            </form>
        </div>
    </div>

    <script>
        // Client-side password confirmation validation
        document.querySelector('form').addEventListener('submit', function(e) {
            var newPassword = document.getElementById('newPassword').value;
            var confirmPassword = document.getElementById('confirmPassword').value;

            if (newPassword !== confirmPassword) {
                e.preventDefault();
                alert('New passwords do not match!');
                return false;
            }

            // Basic password strength check
            if (newPassword.length < 8) {
                e.preventDefault();
                alert('Password must be at least 8 characters long!');
                return false;
            }

            if (!/[A-Z]/.test(newPassword)) {
                e.preventDefault();
                alert('Password must contain at least one uppercase letter!');
                return false;
            }

            if (!/[a-z]/.test(newPassword)) {
                e.preventDefault();
                alert('Password must contain at least one lowercase letter!');
                return false;
            }

            if (!/[0-9]/.test(newPassword)) {
                e.preventDefault();
                alert('Password must contain at least one number!');
                return false;
            }
        });

        // Real-time password match indicator
        document.getElementById('confirmPassword').addEventListener('input', function() {
            var newPassword = document.getElementById('newPassword').value;
            var confirmPassword = this.value;

            if (confirmPassword && newPassword !== confirmPassword) {
                this.style.borderColor = '#dc3545';
            } else if (confirmPassword && newPassword === confirmPassword) {
                this.style.borderColor = '#28a745';
            } else {
                this.style.borderColor = '#e0e0e0';
            }
        });
    </script>
</body>
</html>
