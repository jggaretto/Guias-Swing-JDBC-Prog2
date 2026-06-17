-- =============================================
-- Script de configuración de base de datos
-- Para los ejercicios JDBC (5, 6 y 7)
-- =============================================

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS empresa_db;
USE empresa_db;

-- =============================================
-- Ejercicios 5 y 6: tabla simple
-- =============================================
CREATE TABLE IF NOT EXISTS empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    departamento VARCHAR(50) NOT NULL
);

-- Datos de prueba para ejercicios 5 y 6
INSERT INTO empleados (nombre, departamento) VALUES
('Ana García', 'Recursos Humanos'),
('Carlos López', 'Sistemas'),
('María Fernández', 'Finanzas');

-- =============================================
-- Ejercicio 7: tablas relacionales
-- =============================================

-- Primero eliminar la tabla de empleados anterior si existe
-- y crear las nuevas con clave foránea

DROP TABLE IF EXISTS empleados;

CREATE TABLE IF NOT EXISTS departamentos (
    id_depto INT AUTO_INCREMENT PRIMARY KEY,
    nombre_depto VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    id_departamento INT NOT NULL,
    ruta_foto VARCHAR(255),
    FOREIGN KEY (id_departamento) REFERENCES departamentos(id_depto)
);

-- Datos de prueba para ejercicio 7
INSERT INTO departamentos (nombre_depto) VALUES
('Recursos Humanos'),
('Sistemas'),
('Finanzas'),
('Marketing');

INSERT INTO empleados (nombre, id_departamento, ruta_foto) VALUES
('Ana García', 1, NULL),
('Carlos López', 2, NULL);
