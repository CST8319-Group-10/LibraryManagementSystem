<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create User - Library Management System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #4CA1AF 0%, #2C3E50 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .container {
            max-width: 900px;
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
        input[type="email"],
        input[type="password"],
        input[type="tel"],
        textarea,
        select {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 1em;
            font-family: inherit;
            transition: border-color 0.3s, box-shadow 0.3s;
        }
        input:focus,
        textarea:focus,
        select:focus {
            outline: none;
            border-color: #4CA1AF;
            box-shadow: 0 0 0 3px rgba(76, 161, 175, 0.1);
        }
        textarea {
            resize: vertical;
        }
        small {
            display: block;
            margin-top: 5px;
            color: #666;
            font-size: 0.85em;
        }
        .form-actions {
            display: flex;
            gap: 15px;
            margin-top: 35px;
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
            background: linear-gradient(135deg, #4CA1AF 0%, #2C3E50 100%);
            color: white;
        }
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(76, 161, 175, 0.4);
        }
        .btn:not(.btn-primary) {
            background: #f5f5f5;
            color: #333;
            border: 2px solid #e0e0e0;
        }
        .btn:not(.btn-primary):hover {
            background: #e0e0e0;
        }
        @media (max-width: 768px) {
            .content-wrapper {
                padding: 20px;
            }
            .form-row {
                grid-template-columns: 1fr;
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
            <h1>Create New User</h1>

            <c:if test="${not empty success}">
                <div class="message success">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="message error">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/admin/users/create" method="post">
                <div class="form-group">
                    <label for="email">Email Address: *</label>
                    <input type="email" id="email" name="email" required
                           value="<c:out value='${param.email}'/>"
                           placeholder="user@example.com">
                </div>

                <div class="form-group">
                    <label for="password">Initial Password: *</label>
                    <input type="password" id="password" name="password" required
                           placeholder="Min 8 chars, uppercase, lowercase, number">
                    <small>Password must be at least 8 characters and contain uppercase, lowercase, and a number.</small>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="firstName">First Name: *</label>
                        <input type="text" id="firstName" name="firstName" required
                               value="<c:out value='${param.firstName}'/>"
                               placeholder="First name">
                    </div>

                    <div class="form-group">
                        <label for="lastName">Last Name: *</label>
                        <input type="text" id="lastName" name="lastName" required
                               value="<c:out value='${param.lastName}'/>"
                               placeholder="Last name">
                    </div>
                </div>

                <div class="form-group">
                    <label for="phone">Phone Number:</label>
                    <input type="tel" id="phone" name="phone"
                           value="<c:out value='${param.phone}'/>"
                           placeholder="(optional)">
                </div>

                <div class="form-group">
                    <label for="address">Address:</label>
                    <textarea id="address" name="address" rows="3"
                              placeholder="User address (optional)"><c:out value='${param.address}'/></textarea>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="roleId">Role: *</label>
                        <select id="roleId" name="roleId" required>
                            <c:forEach items="${roles}" var="role">
                                <option value="${role.roleId}" ${param.roleId == role.roleId ? 'selected' : ''}>
                                    <c:out value="${role.name}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="standingId">Account Standing: *</label>
                        <select id="standingId" name="standingId" required>
                            <c:forEach items="${standings}" var="standing">
                                <option value="${standing.standingId}"
                                        ${param.standingId == standing.standingId ? 'selected' : (standing.standingId == 1 ? 'selected' : '')}>
                                    <c:out value="${standing.name}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Create User</button>
                    <a href="${pageContext.request.contextPath}/admin/users" class="btn">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
