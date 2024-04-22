package com.restaurante.proyecto.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.restaurante.proyecto.dtos.EmpleadoResponse;
import com.restaurante.proyecto.models.entity.Empleado;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, Long>{

	
	
	
	@Query("""
			
				SELECT 
					e.idEmpleado AS id,
				    e.nombre AS nombre,
				    e.apellido AS apellido,
				    'MESERO' AS puesto,
				    e.rfc AS rfc,
				    e.sueldo AS sueldo,
				    e.fechaContratacion AS fechaContratacion
				FROM Empleado e
				JOIN Administrador a ON e.idEmpleado = a.idAdministrador.idEmpleado
				WHERE a.idAdministrador.idEmpleado =:id
				
				UNION ALL
				
				SELECT 
					e.idEmpleado AS id,
				    e.nombre AS nombre,
				    e.apellido AS apellido,
				    'MESERO' AS puesto,
				    e.rfc AS rfc,
				    e.sueldo AS sueldo,
				    e.fechaContratacion AS fechaContratacion
				FROM Empleado e
				JOIN Mesero m ON e.idEmpleado = m.idMesero.idEmpleado
				WHERE m.idMesero.idEmpleado =:id
				
				UNION ALL
				
				SELECT 
					e.idEmpleado AS id,
				    e.nombre AS nombre,
				    e.apellido AS apellido,
				    'MESERO' AS puesto,
				    e.rfc AS rfc,
				    e.sueldo AS sueldo,
				    e.fechaContratacion AS fechaContratacion
				FROM Empleado e
				JOIN Cocinero c ON e.idEmpleado = c.idCocinero.idEmpleado
				WHERE c.idCocinero.idEmpleado =:id


			""")
	public List<Object[]> encontrarPorId(Long id);
	
	public Optional<Empleado> findByrfc(String rfc);

	
	@Query(
			
			"""
			
			SELECT 
				e.idEmpleado AS id,
			    e.nombre AS nombre,
			    e.apellido AS apellido,
			    'ADMINISTRADOR' AS puesto,
			    e.rfc AS rfc,
			    e.sueldo AS sueldo,
			    e.fechaContratacion AS fechaContratacion
			FROM Empleado e
			JOIN Administrador a ON e.idEmpleado = a.idAdministrador.idEmpleado
			
			UNION ALL
			
			SELECT 
				e.idEmpleado AS id,
			    e.nombre AS nombre,
			    e.apellido AS apellido,
			    'MESERO' AS puesto,
			    e.rfc AS rfc,
			    e.sueldo AS sueldo,
			    e.fechaContratacion AS fechaContratacion
			FROM Empleado e
			JOIN Mesero m ON e.idEmpleado = m.idMesero.idEmpleado
			
			UNION ALL
			
			SELECT 
				e.idEmpleado AS id,
			    e.nombre AS nombre,
			    e.apellido AS apellido,
			    'COCINERO' AS puesto,
			    e.rfc AS rfc,
			    e.sueldo AS sueldo,
			    e.fechaContratacion AS fechaContratacion
			FROM Empleado e
			JOIN Cocinero c ON e.idEmpleado = c.idCocinero.idEmpleado


		"""
			)
	List<Object[]> encontrarTodosTuplaObject();
	
	
	public default List<EmpleadoResponse> encontrarTodos(){
		
		List<Object[]> tuplas= encontrarTodosTuplaObject();
		List<EmpleadoResponse> response = new ArrayList<EmpleadoResponse>();
		for(Object[] tupla : tuplas) {
			EmpleadoResponse emp = new EmpleadoResponse
					((Long)tupla[0],
					(String) tupla[1],
					(String) tupla[2],
					(String) tupla[3],
					(String) tupla[4],
					(BigDecimal) tupla[5],
					((Date)  tupla[6]).toInstant()
					.atZone(ZoneId.systemDefault()   )
					.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))   .toString()  
					
					
					);
			response.add(emp);
		}
		
		return response;
	};
}
