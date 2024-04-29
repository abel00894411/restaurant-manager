package com.restaurante.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.restaurante.proyecto.models.entity.Mesero;

@Repository
public interface IMeseroRepository  extends JpaRepository<Mesero, Long>{

	
	
	
	
	@Query(
			"""
			
			SELECT m FROM Mesero m
			INNER JOIN Orden o ON o.mesero.idMesero.idEmpleado = m.idMesero.idEmpleado
			INNER JOIN ItemOrden i ON i.orden.idOrden = o.idOrden
			WHERE o.idOrden=:idOrden
			"""
			)
	Mesero encontrarPorIdOrden(Long idOrden);

}
