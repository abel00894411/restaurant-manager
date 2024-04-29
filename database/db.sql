CREATE DATABASE restaurante_analisis;

-- creación de tablaspruebaaa
CREATE TABLE `restaurante_analisis`.`empleados` (
  `idEmpleado` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `apellido` VARCHAR(50) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `rfc` VARCHAR(13) NOT NULL UNIQUE,
  `sueldo` DECIMAL(10,2) NOT NULL,
  `fechaContratacion` DATE NOT NULL,
  PRIMARY KEY (`idEmpleado`)
);

CREATE TABLE `restaurante_analisis`.`administradores` (
  `idAdministrador` INT NOT NULL,
  PRIMARY KEY (`idAdministrador`),
  CONSTRAINT `fk_empleado_administrador`
    FOREIGN KEY (`idAdministrador`)
    REFERENCES `restaurante_analisis`.`empleados` (`idEmpleado`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE `restaurante_analisis`.`cocineros` (
  `idCocinero` INT NOT NULL,
  PRIMARY KEY (`idCocinero`),
  CONSTRAINT `fk_empleado_cocinero`
    FOREIGN KEY (`idCocinero`)
    REFERENCES `restaurante_analisis`.`empleados` (`idEmpleado`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);    


CREATE TABLE  `restaurante_analisis`.`categorias` (
  `idCategoria` INT NOT NULL AUTO_INCREMENT,
  `categoria` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idCategoria`)
);

CREATE TABLE `restaurante_analisis`.`cocineros_categorias` (
  `idCocineroCategoria` INT NOT NULL AUTO_INCREMENT,
  `idCocinero` INT NOT NULL,
  `idCategoria` INT NOT NULL,
  PRIMARY KEY (`idCocineroCategoria`),
  CONSTRAINT `fk_cocinero_categoria`
    FOREIGN KEY (`idCocinero`)
    REFERENCES `restaurante_analisis`.`cocineros` (`idCocinero`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_categoria_cocinero`
    FOREIGN KEY (`idCategoria`)
    REFERENCES `restaurante_analisis`.`categorias` (`idCategoria`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE TABLE  `restaurante_analisis`.`meseros` (
  `idMesero` INT NOT NULL,
  PRIMARY KEY (`idMesero`),
  CONSTRAINT `fk_empleado_mesero`
    FOREIGN KEY (`idMesero`)
    REFERENCES `restaurante_analisis`.`empleados` (`idEmpleado`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);    
    
CREATE TABLE `restaurante_analisis`.`ordenes` (
  `idOrden` INT NOT NULL AUTO_INCREMENT,
  `idMesero` INT NOT NULL,
  `fecha` DATETIME NOT NULL,
  `subtotal` DECIMAL(10,2) NOT NULL,
  `iva` DECIMAL(10,2) NOT NULL,
  `total` DECIMAL(10,2) NOT NULL,
  `estado` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`idOrden`),
  CONSTRAINT `fk_orden_mesero`
    FOREIGN KEY (`idMesero`)
    REFERENCES `restaurante_analisis`.`meseros` (`idMesero`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);     
 
CREATE TABLE  `restaurante_analisis`.`itemsMenu` (
  `idItemMenu` INT NOT NULL AUTO_INCREMENT,
  `producto` VARCHAR(100) NOT NULL,
  `idCategoria` INT NOT NULL,
  `precio` DECIMAL(10,2) NOT NULL,
  `descripcion` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`idItemMenu`),
  CONSTRAINT `fk_itemsMenu_categoria`
  FOREIGN KEY (`idCategoria`)
  REFERENCES `restaurante_analisis`.`categorias` (`idCategoria`)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);   
CREATE TABLE `restaurante_analisis`.`itemsOrden` (
  `idItemOrden` INT NOT NULL AUTO_INCREMENT,
  `idItemMenu` INT NOT NULL,
  `idOrden` INT NOT NULL,
  `idCocinero` INT NOT NULL,
  `estado` VARCHAR(20) NOT NULL,
  `cantidad` INT NOT NULL,
  `suma` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`idItemOrden`),
  CONSTRAINT `fk_itemOrden_itemMenu`
    FOREIGN KEY (`idItemMenu`)
    REFERENCES `restaurante_analisis`.`itemsMenu` (`idItemMenu`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_itemOrden_orden`
    FOREIGN KEY (`idOrden`)
    REFERENCES `restaurante_analisis`.`ordenes` (`idOrden`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_itemOrden_cocinero`
    FOREIGN KEY (`idCocinero`)
    REFERENCES `restaurante_analisis`.`cocineros` (`idCocinero`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE TABLE `restaurante_analisis`.`facturas` (
  `idFactura` INT NOT NULL AUTO_INCREMENT,
  `idOrden` INT NOT NULL,
  `fechaEmision` DATETIME NOT NULL,
  `correo` VARCHAR(100) NOT NULL,
  `direccion` VARCHAR(200) NOT NULL,
  `total` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`idFactura`),
  CONSTRAINT `fk_factura_orden`
    FOREIGN KEY (`idOrden`)
    REFERENCES `restaurante_analisis`.`ordenes` (`idOrden`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);   



DELIMITER //
CREATE PROCEDURE recalcularSUBTOTAL_IVA_TOTAL(idOrden_procedure INT)
BEGIN
    
    DECLARE nuevo_subtotal DECIMAL(10,2);
    DECLARE nuevo_total DECIMAL(10,2);
    
    -- Calcula el nuevo subtotal
    SELECT SUM(suma) INTO nuevo_subtotal
    FROM itemsOrden 
    WHERE idOrden = idOrden_procedure;
    
    -- Actualiza el subtotal, el IVA y el total de la orden
    UPDATE ordenes 
    SET subtotal = nuevo_subtotal,
        iva = nuevo_subtotal * 0.16,
        total = nuevo_subtotal + (nuevo_subtotal * 0.16)
    WHERE idOrden = idOrden_procedure;
    
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER actualizar_itemsOrden_insert 
AFTER INSERT ON itemsOrden
FOR EACH ROW
BEGIN
	CALL recalcularSUBTOTAL_IVA_TOTAL(NEW.idOrden);
END;
//
DELIMITER ;






