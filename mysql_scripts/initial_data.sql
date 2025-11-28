-- Library Management System - Initial Reference Data
-- This script populates the reference tables with initial data

USE lms;

-- Insert Roles
INSERT IGNORE INTO Role (RoleID, Name, Description) VALUES
(1, 'guest', 'Unauthenticated user'),
(2, 'registered_user', 'Registered library member'),
(3, 'librarian', 'Library staff'),
(4, 'administrator', 'System administrator');

-- Insert Account Standing Types
INSERT IGNORE INTO AccountStanding (StandingID, Name, Description) VALUES
(1, 'Good Standing', 'Account active with no restrictions'),
(2, 'Suspended', 'Account temporarily suspended'),
(3, 'Banned', 'Account permanently banned'),
(4, 'Pending Approval', 'Awaiting admin approval');

-- Insert Action Categories
INSERT IGNORE INTO ActionCategory (CategoryID, Category) VALUES
(1, 'Authentication'),
(2, 'User Management'),
(3, 'Role Management'),
(4, 'Account Standing');

-- Insert Audit Log Actions
INSERT IGNORE INTO AuditLogAction (AuditActionID, Action, CategoryID, Description) VALUES
-- Authentication actions (1-9)
(1, 'USER_LOGIN', 1, 'User logged in'),
(2, 'USER_LOGOUT', 1, 'User logged out'),
(3, 'LOGIN_FAILED', 1, 'Failed login attempt'),
(4, 'USER_REGISTERED', 1, 'New user registered'),
(5, 'PASSWORD_CHANGED', 1, 'Password changed'),
-- User management actions (10-19)
(10, 'USER_CREATED', 2, 'Admin created user'),
(11, 'USER_UPDATED', 2, 'User info updated'),
(12, 'USER_DELETED', 2, 'User deleted'),
-- Role management actions (20-29)
(20, 'ROLE_ASSIGNED', 3, 'Role assigned to user'),
(21, 'ROLE_REVOKED', 3, 'Role revoked from user'),
-- Account standing actions (30-39)
(30, 'STANDING_UPDATED', 4, 'Account standing updated'),
(31, 'ACCOUNT_SUSPENDED', 4, 'Account suspended'),
(32, 'ACCOUNT_REINSTATED', 4, 'Account reinstated to good standing');

-- Create a default administrator account
-- Password: Admin123! (BCrypt hashed)
-- This should be changed after first login
INSERT IGNORE INTO UserAccount (
    UserID,
    Email,
    PasswordHash,
    FirstName,
    LastName,
    Phone,
    Address,
    RoleID,
    AccountStanding
) VALUES (
    1,
    'admin@library.com',
    -- This is a placeholder - will be replaced with actual BCrypt hash during first run
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYIR.yd1pKK', -- Admin123!
    'System',
    'Administrator',
    '555-0000',
    'System',
    4, -- Administrator role
    1  -- Good Standing
);

-- Verify the data was inserted
SELECT 'Roles inserted:' AS Info;
SELECT * FROM Role;

SELECT 'Account Standing types inserted:' AS Info;
SELECT * FROM AccountStanding;

SELECT 'Action Categories inserted:' AS Info;
SELECT * FROM ActionCategory;

SELECT 'Audit Log Actions inserted:' AS Info;
SELECT * FROM AuditLogAction ORDER BY AuditActionID;

SELECT 'Default admin account created:' AS Info;
SELECT UserID, Email, FirstName, LastName, RoleID FROM UserAccount WHERE UserID = 1;
