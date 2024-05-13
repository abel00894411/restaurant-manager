package com.restaurante.proyecto.dtos;

import java.math.BigDecimal;

public record ItemMenuCrear(
		
		
		String producto,
		BigDecimal precio,
		String descripcion,
		Long idCategoria,
		byte[] imagen
		
		) {

}
