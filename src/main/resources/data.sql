-- Sample Data for Library Management System
-- Note: Passwords are BCrypt hashed
-- admin123 -> $2a$10$xqxQ8Z9X8Z9X8Z9X8Z9X8OqKqKqKqKqKqKqKqKqKqKqKqKqKqKqKq
-- user123 -> $2a$10$yqyQ8Z9X8Z9X8Z9X8Z9X8OqKqKqKqKqKqKqKqKqKqKqKqKqKqKqKq

-- Insert default admin user
INSERT IGNORE INTO users (id, name, email, password, role) VALUES 
(1, 'Admin User', 'admin@library.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN');

-- Insert sample regular users
INSERT IGNORE INTO users (id, name, email, password, role) VALUES 
(2, 'John Doe', 'user@library.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER'),
(3, 'Jane Smith', 'jane@library.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER'),
(4, 'Bob Johnson', 'bob@library.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER');

-- Insert sample books
INSERT IGNORE INTO books (id, title, author, category, quantity, available_quantity, isbn, publisher, published_year) VALUES 
(1, 'Clean Code', 'Robert C. Martin', 'Programming', 5, 5, '978-0132350884', 'Prentice Hall', 2008),
(2, 'Design Patterns', 'Gang of Four', 'Programming', 3, 3, '978-0201633612', 'Addison-Wesley', 1994),
(3, 'Head First Java', 'Kathy Sierra', 'Programming', 4, 4, '978-0596009205', 'O Reilly', 2005),
(4, 'Spring in Action', 'Craig Walls', 'Programming', 3, 3, '978-1617294945', 'Manning', 2018),
(5, 'Effective Java', 'Joshua Bloch', 'Programming', 4, 4, '978-0134685991', 'Addison-Wesley', 2017),
(6, 'The Pragmatic Programmer', 'Andrew Hunt', 'Programming', 3, 3, '978-0135957059', 'Addison-Wesley', 2019),
(7, 'Introduction to Algorithms', 'Thomas H. Cormen', 'Computer Science', 2, 2, '978-0262033848', 'MIT Press', 2009),
(8, 'Database System Concepts', 'Abraham Silberschatz', 'Database', 3, 3, '978-0078022159', 'McGraw-Hill', 2019),
(9, 'Computer Networks', 'Andrew S. Tanenbaum', 'Networking', 2, 2, '978-0132126953', 'Pearson', 2010),
(10, 'Operating System Concepts', 'Abraham Silberschatz', 'Operating Systems', 3, 3, '978-1118063330', 'Wiley', 2012);
