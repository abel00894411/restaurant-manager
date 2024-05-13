package com.restaurante.proyecto.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.restaurante.proyecto.models.entity.Orden;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Long>{

	
	@Query(value="CALL prueba()",nativeQuery = true)
	List<Object[]> pruebaHola(); 
	
	
	List<Orden> findByfechaBetween(LocalDateTime dateMin, LocalDateTime dateMax);
	
	

}
