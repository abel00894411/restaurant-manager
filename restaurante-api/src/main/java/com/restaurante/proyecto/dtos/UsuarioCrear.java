package com.restaurante.proyecto.dtos;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioCrear(
		
		@NotBlank
		String nombre,
		@NotBlank
	    String apellido,
	    @NotBlank
	    String rfc,
	    @NotNull
	    BigDecimal sueldo,
	    @NotBlank
	    String rol,
	    List<String> categorias

		
		
		) {

}
