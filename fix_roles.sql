USE LostAndFound;

-- First, let's see what role values currently exist
SELECT DISTINCT role FROM user;

-- Check for any problematic role values
SELECT * FROM user WHERE role NOT IN ('ADMIN', 'STAFF', 'USER');

-- Update any 'ROLE_ADMIN' to 'ADMIN'
UPDATE user SET role = 'ADMIN' WHERE role = 'ROLE_ADMIN';

-- Update any 'ROLE_STAFF' to 'STAFF'  
UPDATE user SET role = 'STAFF' WHERE role = 'ROLE_STAFF';

-- Update any 'ROLE_USER' to 'USER'
UPDATE user SET role = 'USER' WHERE role = 'ROLE_USER';

-- Update any empty or null roles to 'USER' as default
UPDATE user SET role = 'USER' WHERE role IS NULL OR role = '';

-- Show the final result
SELECT username, role FROM user;

-- Count users by role
SELECT role, COUNT(*) as count FROM user GROUP BY role;
