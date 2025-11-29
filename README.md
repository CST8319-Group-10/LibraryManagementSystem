# Library Management System

This is a comprehensive Library Management System created for learning purposes for students in Algonquin College for course CST8319. The goal of this project is to demonstrate the implementation of a complete web application using Spring Boot, JSP, Servlets, and MySQL database with role-based authentication.

## Project Overview

The Library Management System provides a complete solution for managing library operations with three distinct user roles:

- **Manager**: Full system administration access
- **Librarian**: Library staff with operational privileges  
- **Member**: Library patrons with borrowing privileges

## Technology Stack

- **Backend**: Spring Boot, Java Servlets, JPA/Hibernate
- **Frontend**: JSP, HTML5, CSS3, JavaScript
- **Database**: MySQL with JPA
- **Authentication**: Session-based with BCrypt password encryption
- **Build Tool**: Maven

## Features

### Authentication & Authorization
- Role-based access control (Manager, Librarian, Member)
- Secure password hashing with BCrypt
- Session management
- Account standing management (Active, Suspended, Disabled)

### User Management
- User registration and profile management
- Role-specific dashboards
- Account locking after failed login attempts
- Password strength validation

### Role-Based Dashboards
- **Manager Dashboard**: Staff management, system reports, administration tools
- **Librarian Dashboard**: Circulation desk, catalog management, member management
- **Member Dashboard**: Book search, borrowing history, reservations

## Build & Run

### Prerequisites
- JDK 17+ with JAVA_HOME configured
- Maven 3.6+
- MySQL 8.0+
- Git

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/library-management-system.git
   cd library-management-system
   ```

2. **Set up MySQL database**
   ```bash
   # Create database
   mysql -u root -p -e "CREATE DATABASE library_management;"
   
   # The application will automatically create tables using JPA ddl-auto=update
   ```

3. **Configure database connection**
   Update `src/main/resources/application.yaml` with your MySQL credentials:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/library_management
       username: your_username
       password: your_password
   ```

4. **Build the application**
   ```bash
   mvn clean package
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

6. **Access the application**
   Open your browser and navigate to: `http://localhost:8080`

### Default Test Accounts
- **Manager**: `admin` / `test123`
- **Librarian**: `librarian1` / `test123`
- **Member**: `testmember` / `test123`

## Project Structure

```
src/main/java/com/ac/cst8319/lms/
├── model/          # Entity classes (UserAccount, Role, etc.)
├── repository/     # Spring Data JPA repositories
├── service/        # Business logic (Authentication, PasswordUtil)
└── servlet/        # Web controllers

src/main/webapp/WEB-INF/jsp/
├── home.jsp        # Landing page
├── login.jsp       # Login form
├── register.jsp    # User registration
└── *-dashboard.jsp # Role-specific dashboards
```

## Key Learning Objectives

This project demonstrates:
1. **Spring Boot Application Architecture**
2. **JSP and Servlet-based Web Development**
3. **Database Design with JPA/Hibernate**
4. **Session-based Authentication & Authorization**
5. **Role-Based Access Control (RBAC)**
6. **Password Security with BCrypt**
7. **MVC Pattern Implementation**
8. **Database Relationship Mapping**

## Development Phases

1. **Database Design**: Entity relationships and JPA mapping
2. **Authentication System**: User registration and login
3. **Role Management**: Different access levels and dashboards
4. **Session Management**: Secure user state handling

## Contributing

Contributions to this project are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/new-feature`
3. Commit your changes: `git commit -m "Add new feature"`
4. Push to the branch: `git push origin feature/new-feature`
5. Open a pull request

## License

This project is created for educational purposes as part of Algonquin College's CST8319 course.

*Note: This is a learning project demonstrating enterprise application development concepts and should not be used in production environments without proper security reviews and enhancements.*
