package com.restaurante.proyecto.dtos;

import java.util.ArrayList;
import java.util.List;

public record ItemOrdenListado(
		
		Long idItemOrden,
		Long idItemMenu,
		Integer cantidad,
		String estado
		
		) {

	public static List<ItemOrdenListado> crearListado(List<Object[]> itemsTupla) {
		
		
		List<ItemOrdenListado> itemsOrdenListado = new ArrayList<ItemOrdenListado>();
		for(Object[] o :itemsTupla) {
			
			itemsOrdenListado.add( new ItemOrdenListado(Long.valueOf((Integer)o[0]), 
														Long.valueOf((Integer)o[1]),
														(Integer)o[2],
														(String)o[3]) 
														);

		}
		
		return itemsOrdenListado;
	}

}
