package com.restaurante.proyecto.dtos;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.restaurante.proyecto.models.entity.Factura;
import com.restaurante.proyecto.models.entity.ItemOrden;
import com.restaurante.proyecto.models.entity.Orden;

public record FacturaResponse (
		
		Long id,
		String fechaEmision,
		String correo,
		String direccion,
		BigDecimal total,
		Map<String, Object> orden,
		String rfc
		
		
		){
	
	
	
	public static FacturaResponse crearFacturaResponse(Factura factura, List<ItemOrden> itemsOrden) {
		
		Map<String, Object> orden = new HashMap<String, Object>();
		List<ItemOrdenListar> itemsListar = itemsOrden.stream().map( ItemOrdenListar::obtenerItem ).collect(Collectors.toList());
		Orden o = factura.getOrden();
		
		orden.put("mesero", o.getMesero().getIdMesero().getNombre()+" "+o.getMesero().getIdMesero().getApellido());
		orden.put("fecha", o.getFecha().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		orden.put("subtotal", o.getSubtotal());
		orden.put("iva", o.getIva());
		orden.put("total", o.getTotal());
		orden.put("items", itemsListar);
		orden.put("idOrden", factura.getOrden().getIdOrden());
		
		FacturaResponse facturaResponse = new FacturaResponse(factura.getIdFactura()
																,factura.getFechaEmision().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
																,factura.getCorreo()
																,factura.getDireccion()
																,factura.getTotal()
																,orden
																,factura.getRfc()
																
				);
		
		return facturaResponse;
		
	}
	
	
	

}
