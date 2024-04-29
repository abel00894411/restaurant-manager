package com.restaurante.proyecto.dtos;

import java.util.ArrayList;
import java.util.List;

import com.restaurante.proyecto.models.entity.ItemOrden;
import com.restaurante.proyecto.models.entity.Orden;

public record ItemOrdenResponse(
		
		Long idItemOrden,
		Long idItemMenu,
		Long idCocinero,
		Integer cantidad,
		String estado
		
		) {

	
	public static List<ItemOrdenResponse> getItemsOrdenResposne(List<ItemOrden> itemsOrden) {
		List<ItemOrdenResponse> itemsOrdenResponse = new ArrayList<ItemOrdenResponse>();

		for (ItemOrden itemOrden : itemsOrden) {
			ItemOrdenResponse itemOrdenResponse = new ItemOrdenResponse(itemOrden.getIdItemOrden(),
					itemOrden.getItemMenu().getIdItemMenu(), itemOrden.getCocinero().getIdCocinero().getIdEmpleado(),
					itemOrden.getCantidad(), itemOrden.getEstado());
			itemsOrdenResponse.add(itemOrdenResponse);
		}
		return itemsOrdenResponse;
	}
	
	
}
