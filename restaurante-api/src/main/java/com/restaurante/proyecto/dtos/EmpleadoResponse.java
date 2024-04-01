package com.restaurante.proyecto.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public record EmpleadoResponse(
		
		Long id,
		String nombre,
		String apellido,
		String puesto,
		String rfc,
		BigDecimal sueldo,
		Date fechaContratacion
		
		) {

}
