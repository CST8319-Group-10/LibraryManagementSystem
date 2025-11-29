<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - Library Management System</title>
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
            max-width: 500px; 
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
        }
        label { 
            display: block; 
            margin-bottom: 5px; 
            color: #555; 
            font-weight: bold;
        }
        input[type="text"], input[type="email"], input[type="password"] { 
            width: 100%; 
            padding: 12px; 
            border: 2px solid #ddd; 
            border-radius: 8px; 
            box-sizing: border-box;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        input:focus {
            border-color: #667eea;
            outline: none;
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
        .form-row {
            display: flex;
            gap: 15px;
        }
        .form-row .form-group {
            flex: 1;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>📝 Register New Account</h2>
        
        <!-- Display error message -->
        <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="message error">
                ❌ <%= request.getAttribute("errorMessage") %>
            </div>
        <% } %>
        
        <!-- Display success message -->
        <% if (request.getParameter("success") != null) { %>
            <div class="message success">
                ✅ <%= request.getParameter("success") %>
            </div>
        <% } %>
        
        <!-- Traditional form submission -->
        <form action="<%= request.getContextPath() %>/register" method="post">
            <div class="form-row">
                <div class="form-group">
                    <label for="firstName">First Name *</label>
                    <input type="text" id="firstName" name="firstName" required placeholder="Enter your first name">
                </div>
                <div class="form-group">
                    <label for="lastName">Last Name *</label>
                    <input type="text" id="lastName" name="lastName" required placeholder="Enter your last name">
                </div>
            </div>
            
            <div class="form-group">
                <label for="username">Username *</label>
                <input type="text" id="username" name="username" required placeholder="Choose a username">
            </div>
            
            <div class="form-group">
                <label for="email">Email *</label>
                <input type="email" id="email" name="email" required placeholder="Enter your email">
            </div>
            
            <div class="form-group">
                <label for="phoneNumber">Phone Number</label>
                <input type="text" id="phoneNumber" name="phoneNumber" placeholder="Enter your phone number">
            </div>
            
            <div class="form-group">
                <label for="address">Address</label>
                <input type="text" id="address" name="address" placeholder="Enter your address">
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="password">Password *</label>
                    <input type="password" id="password" name="password" required placeholder="Create a password">
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Confirm Password *</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required placeholder="Confirm your password">
                </div>
            </div>
            
            <!-- Hidden field for role -->
            <input type="hidden" name="role" value="MEMBER">
            
            <button type="submit">Create Account</button>
        </form>
        
        <div class="links">
            <a href="<%= request.getContextPath() %>/login">Already have an account? Login</a>
            <a href="<%= request.getContextPath() %>/home">Back to Home</a>
        </div>
    </div>

    <script>
        // Client-side password confirmation
        document.querySelector('form').addEventListener('submit', function(e) {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if (password !== confirmPassword) {
                e.preventDefault();
                alert('❌ Passwords do not match!');
                return false;
            }
            
            if (password.length < 8) {
                e.preventDefault();
                alert('❌ Password must be at least 8 characters long!');
                return false;
            }
        });
        
        // Auto-focus on first name field
        document.addEventListener('DOMContentLoaded', function() {
            document.getElementById('firstName').focus();
        });
    </script>
</body>
</html>