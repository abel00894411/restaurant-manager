package com.restaurante.proyecto.dtos;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioActualizar(
		
		@NotBlank
		String nombre,
		@NotBlank
	    String apellido,
	    @NotBlank
	    String rfc,
	    @NotNull
	    BigDecimal sueldo,
	    List<String> categorias

		
		) {

}
