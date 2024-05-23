USE restaurante_analisis;

							         -- EMPLEADOS
-- administrador (id: 1)
INSERT INTO empleados(idEmpleado, nombre, apellido, `password`, rfc, sueldo, fechaContratacion)
VALUES(  1,
		'Carlos',
        'Ramirez Perez',
        '$2a$12$tkffTXR8k.tbHw2nDzB6S.Sm8QL2IXIhoRh9NqTAITocZKF7y7n7i',
        'RZPP750304HGT',
        8800.00,
        '2020-11-05');
        
-- mesero (id: 2)
INSERT INTO empleados(idEmpleado,nombre, apellido, `password`, rfc, sueldo, fechaContratacion)
VALUES(  2,
		'Juan',
        'Hernandez Camacho',
        '$2a$12$tkffTXR8k.tbHw2nDzB6S.Sm8QL2IXIhoRh9NqTAITocZKF7y7n7i',
        'FCDE042358GU2',
        3500.00,
        '2022-01-15');
        
-- cocinero 1, especialidad: Postres     (id: 3)
INSERT INTO empleados(idEmpleado,nombre, apellido, `password`, rfc, sueldo, fechaContratacion)
VALUES(  3,
		'Maria',
        'Gonzalez Lopez',
        '$2a$12$tkffTXR8k.tbHw2nDzB6S.Sm8QL2IXIhoRh9NqTAITocZKF7y7n7i',
        'GNLZ891012MSP',
        4500.00,
        '2021-07-22');

-- cocinero 2, especialidad: Bebidas    (id: 4)
INSERT INTO empleados(idEmpleado,nombre, apellido, `password`, rfc, sueldo, fechaContratacion)
VALUES(  4,
		'Ana',
        'Martinez Santos',
        '$2a$12$tkffTXR8k.tbHw2nDzB6S.Sm8QL2IXIhoRh9NqTAITocZKF7y7n7i',
        'MRZS801215LBC',
        4000.00,
        '2023-03-19');
        
-- cocinero 3, especialidad: Pozoles      (id: 5)
INSERT INTO empleados(idEmpleado,nombre, apellido, `password`, rfc, sueldo, fechaContratacion)
VALUES(  5,
		'Luis',
        'Fernandez Garcia',
        '$2a$12$tkffTXR8k.tbHw2nDzB6S.Sm8QL2IXIhoRh9NqTAITocZKF7y7n7i',
        'FRDG920504KTY',
        4400.00,
        '2019-09-30');

-- cocinero 4, especialidad: Tacos      (id: 6)
INSERT INTO empleados(idEmpleado,nombre, apellido, `password`, rfc, sueldo, fechaContratacion)
VALUES(  6,
		'Elena',
        'Lopez Ramirez',
        '$2a$12$tkffTXR8k.tbHw2nDzB6S.Sm8QL2IXIhoRh9NqTAITocZKF7y7n7i',
        'LPRZ910610MMX',
        4000.00,
        '2022-05-18');

-- cocinero 5, especialidad: Desayunos     (id: 7)
INSERT INTO empleados(idEmpleado,nombre, apellido, `password`, rfc, sueldo, fechaContratacion)
VALUES(  7,
		'Miguel',
        'Vargas Torres',
        '$2a$12$tkffTXR8k.tbHw2nDzB6S.Sm8QL2IXIhoRh9NqTAITocZKF7y7n7i',
        'VRTS880305RHS',
        4500.00,
        '2021-02-25');

							         -- ADMINISTRADORES
INSERT INTO administradores(idAdministrador) VALUES (1);
							         -- MESEROS
INSERT INTO meseros(idMesero) VALUES (2);
							         -- MESEROS
INSERT INTO cocineros(idCocinero) VALUES(3);
INSERT INTO cocineros(idCocinero) VALUES(4);
INSERT INTO cocineros(idCocinero) VALUES(5);
INSERT INTO cocineros(idCocinero) VALUES(6);
INSERT INTO cocineros(idCocinero) VALUES(7);

							         -- Categorias
INSERT INTO categorias(categoria) VALUES('Postres'); -- id:1
INSERT INTO categorias(categoria) VALUES('Bebidas'); -- id:2
INSERT INTO categorias(categoria) VALUES('Pozoles'); -- id:3
INSERT INTO categorias(categoria) VALUES('Tacos');   -- id:4
INSERT INTO categorias(categoria) VALUES('Desayunos');-- id:5

							         -- Cocineros-Categorias
INSERT INTO cocineros_categorias(idCocinero, idCategoria) VALUES(3,1);
INSERT INTO cocineros_categorias(idCocinero, idCategoria) VALUES(4,2);
INSERT INTO cocineros_categorias(idCocinero, idCategoria) VALUES(5,3);
INSERT INTO cocineros_categorias(idCocinero, idCategoria) VALUES(6,4);
INSERT INTO cocineros_categorias(idCocinero, idCategoria) VALUES(7,5);

							         -- Items-Menu
-- Postres (idCategoria: 1)
INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion, imagen)
VALUES('Pastel de Chocolate', 1, 55.00, 'Delicioso pastel de chocolate con cobertura de ganache, 1 rebanada', 'pastelChocolate');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion, imagen)
VALUES('Flan de Vainilla', 1, 35.00, 'Flan de vainilla casero con caramelo','flanVainilla');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion, imagen)
VALUES('Helado de Fresa', 1, 30.00, 'Helado de fresa natural','heladoFresa');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Cheesecake', 1, 60.00, 'Cheesecake con base de galleta y mermelada de frutos rojos','cheescake');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Brownie', 1, 40.00, 'Brownie de chocolate con nueces','brownie');

-- Bebidas (idCategoria: 2)
INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Limonada', 2, 25.00, 'Limonada fresca hecha con limones naturales','limonada');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Café Americano', 2, 20.00, 'Café americano caliente y aromático','cafeAmericano');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Jugo de Naranja', 2, 28.00, 'Jugo de naranja recién exprimido','jugoNaranja');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Té Verde', 2, 22.00, 'Té verde caliente','teVerde');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion, imagen)
VALUES('Chocolate Caliente', 2, 30.00, 'Chocolate caliente con leche','chocolateCaliente');

-- Pozoles (idCategoria: 3)
INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Pozole Rojo', 3, 80.00, 'Tradicional pozole rojo con carne de cerdo','pozoleRojo');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Pozole Verde', 3, 85.00, 'Pozole verde con pollo','pozoleVerde');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Pozole Blanco', 3, 75.00, 'Pozole blanco con carne de res','pozoleBlanco');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Pozole de Mariscos', 3, 95.00, 'Pozole con una mezcla de mariscos frescos','pozoleMariscos');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Pozole Vegetariano', 3, 70.00, 'Pozole vegetariano con granos de maíz y verduras','pozoleVegetariano');

-- Tacos (idCategoria: 4)
INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Tacos al Pastor', 4, 45.00, 'Tacos al pastor servidos con piña y cilantro','tacosPastor');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Tacos de Asada', 4, 50.00, 'Tacos de asada con cebolla y cilantro','tacosAsada');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Tacos de Pescado', 4, 55.00, 'Tacos de pescado con ensalada de col','tacosPescado');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Tacos de Barbacoa', 4, 52.00, 'Tacos de barbacoa de res con salsa verde','tacosBarbacoa');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Tacos de Pollo', 4, 48.00, 'Tacos de pollo con guacamole y pico de gallo','tacosPollo');

-- Desayunos (idCategoria: 5)
INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Huevos Rancheros', 5, 60.00, 'Huevos rancheros servidos con salsa roja y frijoles','huevosRancheros');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Chilaquiles Verdes', 5, 65.00, 'Chilaquiles verdes con pollo y crema','chilaquilesVerdes');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Hot Cakes', 5, 50.00, 'Hot cakes esponjosos con miel de maple','hotCakes');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Omelette de Jamón y Queso', 5, 55.00, 'Omelette relleno de jamón y queso','omeletteJamon');

INSERT INTO itemsmenu(producto, idCategoria, precio, descripcion,imagen)
VALUES('Molletes', 5, 45.00, 'Molletes con frijoles refritos y queso gratinado','molletess');






