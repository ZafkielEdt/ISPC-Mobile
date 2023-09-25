CREATE DATABASE IF NOT EXISTS gymapp;
USE gymapp;

CREATE TABLE IF NOT EXISTS roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    email VARCHAR(50),
    mail VARCHAR(100),
    contrasena VARCHAR(50),
    edad INT,
    peso_actual DECIMAL(3,2),
    meta_peso DECIMAL(3,2),
    rol_id INT,
    FOREIGN KEY (rol_id) REFERENCES roles(id),
    imc DECIMAL(5, 2),
    fecha_registro DATE,
    genero CHAR,
    estatura INT
);

CREATE TABLE IF NOT EXISTS seguimiento_peso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    fecha_fin DATE,
    fecha DATE,
    peso_actual DECIMAL(3,2),
    balance_hasta_la_fecha DECIMAL(5,2)
);



CREATE TABLE IF NOT EXISTS producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion VARCHAR(100),
    precio DECIMAL(10, 2)
);

CREATE TABLE IF NOT EXISTS compras (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    producto_id INT,
    FOREIGN KEY (producto_id) REFERENCES producto(id),
    cantidad INT,
    fecha_compra DATE
);

CREATE TABLE IF NOT EXISTS alimentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    kcal_por_porcion FLOAT
);

CREATE TABLE IF NOT EXISTS ejercicios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion VARCHAR(100),
    grupo_muscular VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS dietas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS comida_dieta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    alimento_id INT,
    FOREIGN KEY (alimento_id) REFERENCES alimentos(id),
    dieta_id INT,
    FOREIGN KEY (dieta_id) REFERENCES dietas(id),
    cant_porcion INT
);

CREATE TABLE IF NOT EXISTS rutinas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS registros_dieta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dieta_id INT,
    FOREIGN KEY (dieta_id) REFERENCES dietas(id),
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    fecha DATE,
    completado BOOLEAN
);

CREATE TABLE IF NOT EXISTS planes_dieta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    dieta_id INT,
    FOREIGN KEY (dieta_id) REFERENCES dietas(id),
    fecha_inicio DATE,
    fecha_fin DATE
);

CREATE TABLE IF NOT EXISTS planes_rutina (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rutina_id INT,
    FOREIGN KEY (rutina_id) REFERENCES rutinas(id),
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    fecha_inicio DATE,
    fecha_fin DATE
);

CREATE TABLE IF NOT EXISTS ejercicio_rutina (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rutina_id INT,
    FOREIGN KEY (rutina_id) REFERENCES rutinas(id),
    ejercicio_id INT,
    FOREIGN KEY (ejercicio_id) REFERENCES ejercicios(id),
    repeticiones_por_serie INT,
    series INT
);





