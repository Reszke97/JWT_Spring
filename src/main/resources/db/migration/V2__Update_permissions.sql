-- V2__Update_permissions.sql

ALTER TABLE permissions
ADD COLUMN description_polish VARCHAR(255) DEFAULT NULL,
ADD COLUMN description_english VARCHAR(255) DEFAULT NULL;