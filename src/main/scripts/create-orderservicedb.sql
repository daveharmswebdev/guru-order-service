DROP DATABASE IF EXISTS orderservicedb;
DROP USER IF EXISTS `orderserviceadmin`@`%`;
DROP USER IF EXISTS `orderserviceuser`@`%`;
CREATE DATABASE IF NOT EXISTS orderservicedb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `orderserviceadmin`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `orderservicedb`.* TO `orderserviceadmin`@`%`;
CREATE USER IF NOT EXISTS `orderserviceuser`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `orderservicedb`.* TO `orderserviceuser`@`%`;
FLUSH PRIVILEGES;