-- Script de inicialización para crear las bases de datos de los microservicios
CREATE DATABASE autorizacion_db;
CREATE DATABASE publicaciones_db;
CREATE DATABASE catalogo_db;
CREATE DATABASE notifications;

-- Otorgar permisos al usuario admin en todas las bases de datos
GRANT ALL PRIVILEGES ON DATABASE autorizacion_db TO admin;
GRANT ALL PRIVILEGES ON DATABASE publicaciones_db TO admin;
GRANT ALL PRIVILEGES ON DATABASE catalogo_db TO admin;
GRANT ALL PRIVILEGES ON DATABASE notifications TO admin;