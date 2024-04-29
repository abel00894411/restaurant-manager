package com.restaurante.proyecto.dtos;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.restaurante.proyecto.models.entity.ItemOrden;
import com.restaurante.proyecto.models.entity.Orden;

public class OrdenAsignadoResponse extends OrdenDTO{
		
	
	public OrdenAsignadoResponse(Long idOrden,
			String fecha,
			BigDecimal subtotal,
			String estado,
			List<ItemOrdenResponse> items) {
		
		super(idOrden, fecha, subtotal, estado, items);
	}
	
	public OrdenAsignadoResponse(Orden orden, List<ItemOrden> itemsOrden) {
		
		super(orden.getIdOrden(), 
				orden.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), 
				orden.getSubtotal(), 
				orden.getEstado(), 
				ItemOrdenResponse.getItemsOrdenResposne(itemsOrden));
		
	}
	
	

	
	
}
