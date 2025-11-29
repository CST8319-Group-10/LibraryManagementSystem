<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Library Management System</title>
    <style>
        body { 
            font-family: Arial, sans-serif; 
            margin: 0; 
            padding: 20px; 
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .container { 
            max-width: 400px; 
            width: 100%;
            background: white; 
            padding: 40px; 
            border-radius: 15px; 
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
        }
        h2 { 
            text-align: center; 
            color: #333; 
            margin-bottom: 30px;
        }
        .form-group { 
            margin-bottom: 20px; 
            position: relative;
        }
        label { 
            display: block; 
            margin-bottom: 5px; 
            color: #555; 
            font-weight: bold;
        }
        input[type="text"], input[type="password"] { 
            width: 100%; 
            padding: 12px 40px 12px 12px; /* Add right padding for eye icon */
            border: 2px solid #ddd; 
            border-radius: 8px; 
            box-sizing: border-box;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        input[type="text"]:focus, input[type="password"]:focus {
            border-color: #667eea;
            outline: none;
        }
        .password-container {
            position: relative;
        }
        .toggle-password {
            position: absolute;
            right: 12px;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            cursor: pointer;
            color: #666;
            font-size: 20px;
            padding: 0;
            width: 24px;
            height: 24px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .toggle-password:hover {
            color: #333;
        }
        button { 
            width: 100%; 
            padding: 15px; 
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); 
            color: white; 
            border: none; 
            border-radius: 8px; 
            cursor: pointer; 
            font-size: 16px;
            font-weight: bold;
            transition: transform 0.2s;
        }
        button:hover { 
            transform: translateY(-2px);
        }
        .message { 
            margin-top: 15px; 
            text-align: center; 
            padding: 10px;
            border-radius: 5px;
        }
        .error { 
            color: #dc3545; 
            background: #f8d7da;
            border: 1px solid #f5c6cb;
        }
        .success { 
            color: #28a745; 
            background: #d4edda;
            border: 1px solid #c3e6cb;
        }
        .links { 
            text-align: center; 
            margin-top: 20px; 
        }
        .links a { 
            color: #667eea; 
            text-decoration: none; 
            margin: 0 10px; 
            font-weight: bold;
        }
        .links a:hover { 
            text-decoration: underline; 
        }
        .test-accounts {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            margin-top: 20px;
            font-size: 14px;
        }
        .test-accounts h4 {
            margin-top: 0;
            color: #495057;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>🔐 Login to Library System</h2>
        
        <!-- Display error message if present -->
        <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="message error">
                ❌ <%= request.getAttribute("errorMessage") %>
            </div>
        <% } %>
        
        <!-- Login form -->
        <form action="<%= request.getContextPath() %>/login" method="post">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required placeholder="Enter your username">
            </div>
            
            <div class="form-group">
                <label for="password">Password:</label>
                <div class="password-container">
                    <input type="password" id="password" name="password" required placeholder="Enter your password">
                    <!-- Static eye icon positioned at far right of password field -->
                    <button type="button" class="toggle-password">👁</button>
                </div>
            </div>
            
            <button type="submit">Login</button>
        </form>

        <!-- Test accounts information -->
        <div class="test-accounts">
            <h4>🧪 Test Accounts:</h4>
            <p><strong>Manager:</strong> admin / test123</p>
            <p><strong>Librarian:</strong> librarian1 / test123</p>
            <p><strong>Member:</strong> testmember / test123</p>
        </div>
        
        <!-- Navigation links -->
        <div class="links">
            <a href="<%= request.getContextPath() %>/register">Create Account</a>
            <a href="<%= request.getContextPath() %>/home">Back to Home</a>
        </div>
    </div>

    <script>
        // Toggle password visibility when eye icon is clicked
        document.querySelector('.toggle-password').addEventListener('click', function() {
            const passwordInput = document.getElementById('password');
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
            } else {
                passwordInput.type = 'password';
            }
        });
        
        // Auto-focus on username field when page loads
        document.addEventListener('DOMContentLoaded', function() {
            document.getElementById('username').focus();
        });
        
        // Clear error message when user starts typing in username or password fields
        document.getElementById('username').addEventListener('input', clearError);
        document.getElementById('password').addEventListener('input', clearError);
        
        function clearError() {
            const errorMessage = document.querySelector('.message.error');
            if (errorMessage) {
                errorMessage.style.display = 'none';
            }
        }
    </script>
</body>
</html>