package com.restaurante.proyecto.dtos;

import java.math.BigDecimal;

import com.restaurante.proyecto.models.entity.ItemMenu;


public record ItemMenuResponse(
		
		
		 Long idItemMenu,
		
		 String producto,
		
		 Long categoria,
		
		 BigDecimal precio,
		
		 String descripcion,
		
		 byte[] imagen
		
		
		) {

	
	public static ItemMenuResponse crearItem(ItemMenu itemMenu, byte[] imagen) {
		
		
		return new ItemMenuResponse(itemMenu.getIdItemMenu()
									, itemMenu.getProducto()
									, itemMenu.getCategoria().getIdCategoria()
									, itemMenu.getPrecio()
									, itemMenu.getDescripcion()
									,imagen);
	}
	
	
}
