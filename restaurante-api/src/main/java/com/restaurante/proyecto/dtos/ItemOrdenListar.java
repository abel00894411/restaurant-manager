package com.restaurante.proyecto.dtos;

import java.math.BigDecimal;

import com.restaurante.proyecto.models.entity.ItemOrden;

public record ItemOrdenListar(
		
		
	     Long id,
	     Long idItemMenu,
	     Long idCocinero,
	     String producto,
	     Integer cantidad,
	     BigDecimal costo,
	     String estado
		
		
		
		) {


	public static ItemOrdenListar obtenerItem(ItemOrden i) {
		
		return new ItemOrdenListar(
	            i.getIdItemOrden(),
	            i.getItemMenu().getIdItemMenu(),
	            i.getCocinero().getCocineroId(),
	            i.getItemMenu().getProducto(),
	            i.getCantidad(),
	            i.getSuma(),
	            i.getEstado()
	        );
	}
	
	
	
	

}
