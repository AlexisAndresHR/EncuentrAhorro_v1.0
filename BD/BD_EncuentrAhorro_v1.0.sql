CREATE DATABASE IF NOT EXISTS encuentrahorro_v1;
USE encuentrahorro_v1;


CREATE TABLE IF NOT EXISTS usuarios (
    nombre_usuario varchar(20) primary key,
    email_usuario varchar(50) not null,
    contrasena_usuario varchar(20) not null,
    nombre varchar(30) not null,
    apellido_pat varchar(30) not null,
    apellido_mat varchar(30) not null,
    fotografia_usuario blob,
    promedio_evaluaciones float not null default 0,
    nivel_usuario varchar(20) not null default "Promedio"
);
DESCRIBE usuarios;

INSERT INTO usuarios (nombre_usuario, email_usuario, contrasena_usuario, nombre, apellido_pat, apellido_mat) VALUES 
	('AlexisHR', 'alexisandres006@gmail.com', 'password1', 'Alexis Andrés', 'Hernández', 'Ramírez'),
	('Angie11', '1717110255@utectulancingo.edu.mx', 'password2', 'Maria de los Angeles', 'Gayosso', 'Octaviano');


CREATE TABLE IF NOT EXISTS categorias_productos (
    id_categoria int auto_increment primary key,
    nombre_categoria varchar(40) not null
);
DESCRIBE categorias_productos;

INSERT INTO categorias_productos (nombre_categoria) VALUES 
	('Combustibles'),
	('Frutas y Vegetales');


CREATE TABLE IF NOT EXISTS tipos_productos (
    id_producto int auto_increment primary key,
    nombre_producto varchar(40) not null,
    imagen_producto blob,
    id_categoria int,
    foreign key (id_categoria) references categorias_productos(id_categoria)
);
DESCRIBE tipos_productos;

INSERT INTO tipos_productos (nombre_producto, id_categoria) VALUES 
	('Gas LP', 1),
	('Gasolina', 1),
	('Jitomates', 2),
	('Arándanos', 2);


CREATE TABLE IF NOT EXISTS recomendaciones (
    id_recomendacion int auto_increment primary key,
    fecha timestamp default now(),
    descripcion varchar(200) not null,
    precio float not null,
    latitud_ubi varchar(20),
    longitud_ubi varchar(20),
    duracion int not null,
    id_categoria int,
    id_producto int,
    nombre_usuario varchar(20),
    num_megusta int not null default 0,
    num_comentarios int not null default 0,
    promedio_evaluaciones float not null default 0,
    recomendacion_activa boolean not null default true,
    foreign key (id_categoria) references categorias_productos(id_categoria),
    foreign key (id_producto) references tipos_productos(id_producto),
    foreign key (nombre_usuario) references usuarios(nombre_usuario)
);
DESCRIBE recomendaciones;

INSERT INTO recomendaciones (descripcion, precio, latitud_ubi, longitud_ubi, duracion, id_categoria, id_producto, nombre_usuario) VALUES 
	('Litro de gasolina magna.', 17.68, '20.074047', '-98.351546', 2, 1, 2, 'AlexisHR'),
	('250 gramos de arándanos deshidratados.', 67, '20.082488', '-98.355372', 7, 2, 4, 'Angie11');


CREATE TABLE IF NOT EXISTS comentarios (
    id_comentario int auto_increment primary key,
    id_recomendacion int,
    nombre_usuario varchar(20),
    contenido varchar(200) not null,
    foreign key (id_recomendacion) references recomendaciones(id_recomendacion),
    foreign key (nombre_usuario) references usuarios(nombre_usuario)
);
DESCRIBE comentarios;

INSERT INTO comentarios (id_recomendacion, nombre_usuario, contenido) VALUES 
	(1, 'Angie11', 'Muy buen precio comparado con los demás establecimientos de Tulancingo. Recomendación  útil');


CREATE TABLE IF NOT EXISTS favoritos (
    id_favorito int auto_increment primary key,
    nombre_usuario varchar(20),
    id_recomendacion int,
    foreign key (nombre_usuario) references usuarios(nombre_usuario),
    foreign key (id_recomendacion) references recomendaciones(id_recomendacion)
);
DESCRIBE favoritos;

INSERT INTO favoritos (nombre_usuario, id_recomendacion) VALUES 
	('Angie11', 1);


CREATE TABLE IF NOT EXISTS evaluaciones (
	id_evaluacion int auto_increment primary key,
	calificacion int not null,
	id_recomendacion int,
	nombre_usuario varchar(20),
	foreign key (id_recomendacion) references recomendaciones(id_recomendacion),
    foreign key (nombre_usuario) references usuarios(nombre_usuario)
);
DESCRIBE evaluaciones;

INSERT INTO evaluaciones (calificacion, id_recomendacion, nombre_usuario) VALUES 
	(10, 1, 'Angie11');
