CREATE DATABASE IF NOT EXISTS warehouse;
GRANT SELECT,INSERT,UPDATE,DELETE ON warehouse.* TO root@'%' IDENTIFIED BY 'root';
