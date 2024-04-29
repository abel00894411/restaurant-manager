package com.restaurante.proyecto.dtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.restaurante.proyecto.models.entity.ItemOrden;


public record ItemOrdenAgregadoResponse(
		
		
		Long idItemOrden,
        Long idItemMenu,
        Long idCocinero,
        Integer cantidad,
        BigDecimal costo,
        String estado

		
		) {
	
	
	public static List<ItemOrdenAgregadoResponse> obtenerItems(List<ItemOrden> items){
		
		return items.stream().map( x->{
			return new ItemOrdenAgregadoResponse(x.getIdItemOrden(),
												x.getItemMenu().getIdItemMenu(),
												x.getCocinero().getCocineroId(), 
												x.getCantidad(), 
												x.getItemMenu().getPrecio(),
												x.getEstado());
			
		} ).collect(Collectors.toList());
		
	}

}
