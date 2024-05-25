package com.restaurante.proyecto.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.proyecto.models.entity.Factura;

@Repository
public interface IFacturaRepository extends JpaRepository<Factura, Long>{
	
	
	  List<Factura> findByCorreo(String correo);
}
