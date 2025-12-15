<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Member Account - Library Management System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #ff7e5f 0%, #feb47b 100%);
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
            border-color: #ff7e5f;
            box-shadow: 0 0 0 3px rgba(255, 126, 95, 0.1);
        }
        textarea {
            resize: vertical;
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
            background: linear-gradient(135deg, #ff7e5f 0%, #feb47b 100%);
            color: white;
        }
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(255, 126, 95, 0.4);
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
            <h1>Edit Member Account</h1>

            <c:if test="${not empty error}">
                <div class="message error">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/librarian/users/edit" method="post">
                <input type="hidden" name="userId" value="${user.userId}"/>

                <div class="form-group">
                    <label for="email">Email Address: *</label>
                    <input type="email" id="email" name="email" required
                           value="<c:out value='${user.email}'/>"
                           placeholder="user@example.com">
                </div>

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
                    <textarea id="address" name="address" rows="3"
                              placeholder="Member address (optional)"><c:out value='${user.address}'/></textarea>
                </div>

                <div class="form-group">
                    <label for="standingId">Account Standing: *</label>
                    <select id="standingId" name="standingId" required>
                        <c:forEach items="${standings}" var="standing">
                            <option value="${standing.standingId}" ${standing.standingId == user.accountStanding ? 'selected' : ''}>
                                <c:out value="${standing.name}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Update Member Account</button>
                    <a href="${pageContext.request.contextPath}/librarian/users" class="btn">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
