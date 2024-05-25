package com.restaurante.proyecto.dtos;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.restaurante.proyecto.models.entity.Orden;

public record OrdenListarResponse(
		
		Long id,
	    Long idMesero,
	    String nombreMesero,
	    String fecha,
	    String estado,
	    BigDecimal subtotal,
	    BigDecimal iva,
	    BigDecimal total,
	    List<ItemOrdenListar> items
		
		) {
	
    public static OrdenListarResponse obtenerOrden(Orden orden, List<ItemOrdenListar> items) {
        return new OrdenListarResponse(
            orden.getIdOrden(),
            orden.getMesero().getMeseroId(),
            orden.getMesero().getIdMesero().getNombre().concat(" ").concat(orden.getMesero().getIdMesero().getApellido()),
            orden.getFecha().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            orden.getEstado(),
            orden.getSubtotal(),
            orden.getIva(),
            orden.getTotal(),
            items
        );
    }
	

}
