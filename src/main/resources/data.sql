-- Insert default account standings
INSERT IGNORE INTO account_standing (id, name, description) VALUES 
(1, 'ACTIVE', 'Account is active and can use all privileges'),
(2, 'SUSPENDED', 'Account is temporarily suspended'),
(3, 'DISABLED', 'Account is permanently disabled'),
(4, 'PENDING_VERIFICATION', 'Account awaiting email verification');

-- Insert default roles
INSERT IGNORE INTO role (id, name, description) VALUES 
(1, 'MANAGER', 'System administrator with full access'),
(2, 'LIBRARIAN', 'Library staff with operational privileges'),
(3, 'MEMBER', 'Registered library member');

-- Insert default manager account (password: admin123)
INSERT IGNORE INTO user_account (id, username, password, email, first_name, last_name, role_id, account_standing_id) 
VALUES (1, 'admin', '$2a$10$HcC49oym.vEcEIB/hkqEh.FgjSi5jmGTMIjBAeBruhc0dKyUwjqpq', 'admin@library.com', 'System', 'Administrator', 1, 1);

-- Insert sample librarian (password: lib123)
INSERT IGNORE INTO user_account (id, username, password, email, first_name, last_name, role_id, account_standing_id) 
VALUES (2, 'librarian1', '$2a$10$HcC49oym.vEcEIB/hkqEh.FgjSi5jmGTMIjBAeBruhc0dKyUwjqpq', 'librarian@library.com', 'Jane', 'Smith', 2, 1);

-- Insert sample member (password: member123)
INSERT IGNORE INTO user_account (id, username, password, email, first_name, last_name, role_id, account_standing_id) 
VALUES (3, 'testmember', '$2a$10$HcC49oym.vEcEIB/hkqEh.FgjSi5jmGTMIjBAeBruhc0dKyUwjqpq', 'member@test.com', 'John', 'Doe', 3, 1);

-- Insert manager details
INSERT IGNORE INTO manager (user_id, department) 
VALUES (1, 'Administration');

-- Insert librarian details
INSERT IGNORE INTO librarian (user_id, education, experience) 
VALUES (2, 'Masters in Library Science', '5 years experience');

-- Insert member details
INSERT IGNORE INTO member (user_id, address) 
VALUES (3, '123 Main St, City, State');