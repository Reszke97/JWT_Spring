-- V1__Create_users_and_departments_with_permissions_tables.sql

-- Create Enum Types
CREATE TYPE user_role AS ENUM ('EMPLOYEE', 'MANAGER', 'ADMIN', 'IT');
CREATE TYPE user_theme AS ENUM ('DARK', 'LIGHT');

-- Create Departments Sequence
CREATE SEQUENCE departments_seq START 1;

-- Create Departments Table
CREATE TABLE departments (
    id INT PRIMARY KEY DEFAULT NEXTVAL('departments_seq'),
    code VARCHAR(255) NOT NULL,
    label VARCHAR(255) NOT NULL,
    supervisor_id INT,
    allow_helpdesk BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Users Sequence
CREATE SEQUENCE app_users_seq START 1;

-- Create Users Table
CREATE TABLE app_users (
    id INT PRIMARY KEY DEFAULT NEXTVAL('app_users_seq'),
    username VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id INT NOT NULL,
    department_id INT NOT NULL,
    is_active BOOLEAN DEFAULT FALSE,
    theme user_theme DEFAULT 'LIGHT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Roles Sequence
CREATE SEQUENCE roles_seq START 1;

-- Create Roles Table
CREATE TABLE roles (
    id INT PRIMARY KEY DEFAULT NEXTVAL('roles_seq'),
    name user_role NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Permissions Sequence
CREATE SEQUENCE permissions_seq START 1;

-- Create Permissions Table
CREATE TABLE permissions (
    id INT PRIMARY KEY DEFAULT NEXTVAL('permissions_seq'),
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Permission Groups Sequence
CREATE SEQUENCE permission_groups_seq START 1;

-- Create Permission Groups Table
CREATE TABLE permission_groups (
    id INT PRIMARY KEY DEFAULT NEXTVAL('permission_groups_seq'),
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Permissions Assigned to Groups Sequence
CREATE SEQUENCE permissions_assigned_to_groups_seq START 1;

-- Create Permissions Assigned to Groups Table
CREATE TABLE permissions_assigned_to_groups (
    id INT PRIMARY KEY DEFAULT NEXTVAL('permissions_assigned_to_groups_seq'),
    permission_group_id INT,
    permission_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create User Permission Groups Sequence
CREATE SEQUENCE user_permission_groups_seq START 1;

-- Create User Permission Groups Table
CREATE TABLE user_permission_groups (
    id INT PRIMARY KEY DEFAULT NEXTVAL('user_permission_groups_seq'),
    user_id INT,
    permission_group_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Function to Update Timestamp
CREATE OR REPLACE FUNCTION update_timestamp()
    RETURNS TRIGGER AS $$
        BEGIN
            NEW.updated_at = CURRENT_TIMESTAMP;
            RETURN NEW;
        END;
    $$ LANGUAGE plpgsql;

-- Create Triggers for Tables
CREATE TRIGGER update_users_timestamp_trigger
    BEFORE UPDATE ON app_users
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER update_roles_timestamp_trigger
    BEFORE UPDATE ON roles
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER update_departments_timestamp_trigger
    BEFORE UPDATE ON departments
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER update_permissions_timestamp_trigger
    BEFORE UPDATE ON permissions
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER update_permission_groups_timestamp_trigger
    BEFORE UPDATE ON permission_groups
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER update_permissions_assigned_to_groups_timestamp_trigger
    BEFORE UPDATE ON permissions_assigned_to_groups
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER update_user_permission_groups_timestamp_trigger
    BEFORE UPDATE ON user_permission_groups
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- Add Foreign Key Constraints
ALTER TABLE app_users
    ADD CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES departments(id),
    ADD CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id);

ALTER TABLE departments
    ADD CONSTRAINT fk_supervisor FOREIGN KEY (supervisor_id) REFERENCES app_users(id);

ALTER TABLE user_permission_groups
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES app_users(id),
    ADD CONSTRAINT fk_permission_group FOREIGN KEY (permission_group_id) REFERENCES permission_groups(id);

ALTER TABLE permissions_assigned_to_groups
    ADD CONSTRAINT fk_permission FOREIGN KEY (permission_id) REFERENCES permissions(id),
    ADD CONSTRAINT fk_permission_group FOREIGN KEY (permission_group_id) REFERENCES permission_groups(id);

-- Add Casts
CREATE CAST (character varying AS user_role) WITH INOUT AS IMPLICIT;
CREATE CAST (character varying AS user_theme) WITH INOUT AS IMPLICIT;
