package com.restaurante.proyecto.repositories;

import java.util.List;

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
	
}
