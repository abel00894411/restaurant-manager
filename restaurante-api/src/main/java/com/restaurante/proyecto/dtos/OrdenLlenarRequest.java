package com.restaurante.proyecto.dtos;

import java.util.List;

public record OrdenLlenarRequest(
		
		Long idOrden,
		List<ItemOrdenRequest> items
		
		) {

}
