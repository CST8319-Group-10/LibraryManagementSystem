<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile Settings - Library Management System</title>
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
        }
        .container {
            max-width: 800px;
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
            margin-bottom: 10px;
            font-size: 2em;
        }
        .subtitle {
            color: #666;
            margin-bottom: 30px;
            font-size: 0.95em;
        }
        .info-banner {
            background-color: #e7f3ff;
            border-left: 4px solid #2196F3;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 4px;
            color: #1976D2;
            font-size: 0.9em;
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
        .profile-info {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 30px;
        }
        .profile-info h3 {
            color: #667eea;
            margin-bottom: 15px;
            font-size: 1.1em;
        }
        .info-row {
            display: grid;
            grid-template-columns: 150px 1fr;
            padding: 8px 0;
            border-bottom: 1px solid #e0e0e0;
        }
        .info-row:last-child {
            border-bottom: none;
        }
        .info-label {
            font-weight: 600;
            color: #555;
        }
        .info-value {
            color: #333;
        }
        .badge {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 12px;
            font-size: 0.85em;
            font-weight: 600;
        }
        .badge-admin {
            background-color: #dc3545;
            color: white;
        }
        .badge-librarian {
            background-color: #fd7e14;
            color: white;
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
        .form-group {
            margin-bottom: 25px;
        }
        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin-bottom: 25px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 600;
            font-size: 0.95em;
        }
        input[type="text"],
        input[type="tel"],
        input[type="email"],
        textarea {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 1em;
            font-family: inherit;
            transition: border-color 0.3s, box-shadow 0.3s;
        }
        input:focus,
        textarea:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }
        input:disabled {
            background-color: #f5f5f5;
            color: #999;
            cursor: not-allowed;
        }
        textarea {
            resize: vertical;
            min-height: 80px;
        }
        .form-actions {
            display: flex;
            gap: 15px;
            margin-top: 30px;
            padding-top: 25px;
            border-top: 1px solid #e0e0e0;
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
        .readonly-hint {
            font-size: 0.85em;
            color: #999;
            font-style: italic;
            margin-top: 5px;
        }
        @media (max-width: 768px) {
            .content-wrapper {
                padding: 25px;
            }
            .form-row {
                grid-template-columns: 1fr;
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
            <h1>👤 Profile Settings</h1>
            <p class="subtitle">Manage your personal information</p>

            <c:if test="${not empty success}">
                <div class="message success">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="message error">${error}</div>
            </c:if>

            <div class="info-banner">
                <strong>Note:</strong> You can update your name, phone number, and address.
                Email and role cannot be changed through this form. Contact an administrator if you need to change your email.
            </div>

            <!-- Read-only Account Information -->
            <div class="profile-info">
                <h3>Account Information (Read-only)</h3>
                <div class="info-row">
                    <div class="info-label">User ID:</div>
                    <div class="info-value">${user.userId}</div>
                </div>
                <div class="info-row">
                    <div class="info-label">Email:</div>
                    <div class="info-value">${user.email}</div>
                </div>
                <div class="info-row">
                    <div class="info-label">Role:</div>
                    <div class="info-value">
                        <c:choose>
                            <c:when test="${user.roleId == 4}">
                                <span class="badge badge-admin">Administrator</span>
                            </c:when>
                            <c:when test="${user.roleId == 3}">
                                <span class="badge badge-librarian">Librarian</span>
                            </c:when>
                            <c:when test="${user.roleId == 2}">
                                <span class="badge badge-user">Member</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge">Guest</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="info-row">
                    <div class="info-label">Account Standing:</div>
                    <div class="info-value">
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
                                <span class="badge">Pending</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="info-row">
                    <div class="info-label">Member Since:</div>
                    <div class="info-value">${user.createdAt.toString().substring(0, 10)}</div>
                </div>
            </div>

            <!-- Editable Profile Form -->
            <form action="${pageContext.request.contextPath}/user/profile" method="post">
                <h3 style="margin-bottom: 20px; color: #667eea;">Update Your Information</h3>

                <div class="form-row">
                    <div class="form-group">
                        <label for="firstName">First Name: *</label>
                        <input type="text" id="firstName" name="firstName" required
                               value="<c:out value='${user.firstName}'/>"
                               placeholder="First name">
                    </div>

                    <div class="form-group">
                        <label for="lastName">Last Name: *</label>
                        <input type="text" id="lastName" name="lastName" required
                               value="<c:out value='${user.lastName}'/>"
                               placeholder="Last name">
                    </div>
                </div>

                <div class="form-group">
                    <label for="phone">Phone Number:</label>
                    <input type="tel" id="phone" name="phone"
                           value="<c:out value='${user.phone}'/>"
                           placeholder="(optional)">
                </div>

                <div class="form-group">
                    <label for="address">Address:</label>
                    <textarea id="address" name="address"
                              placeholder="Your address (optional)"><c:out value='${user.address}'/></textarea>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                    <a href="${pageContext.request.contextPath}/dashboard" class="btn">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
