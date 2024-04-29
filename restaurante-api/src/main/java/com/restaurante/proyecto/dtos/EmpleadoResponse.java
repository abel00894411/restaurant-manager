package com.restaurante.proyecto.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.restaurante.proyecto.models.entity.Empleado;

public record EmpleadoResponse(
		
		Long id,
		String nombre,
		String apellido,
		String puesto,
		String rfc,
		BigDecimal sueldo,
		String fechaContratacion
		
		) {
	
	// si no se ocupa hay que borrar este metodo o clase

//	public static EmpleadoResponse createEmpleado(Empleado e) {
//		
//		return new EmpleadoResponse(
//				
//				e.getIdEmpleado(),
//				e.getNombre(),
//				e.getApellido(),
//				"",
//				e.getRfc(),
//				e.getSueldo(),
//				e.getFechaContratacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString()
//				
//				);
//	}

}
