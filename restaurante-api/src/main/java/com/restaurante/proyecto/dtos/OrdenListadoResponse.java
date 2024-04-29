package com.restaurante.proyecto.dtos;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.restaurante.proyecto.models.entity.ItemOrden;
import com.restaurante.proyecto.models.entity.Orden;

public class OrdenListadoResponse extends OrdenDTO{
	

	private BigDecimal total;
	private BigDecimal iva;

	public OrdenListadoResponse(Long idOrden, String fechaFormateada, BigDecimal subtotal, String estado,
			List<ItemOrdenResponse> items, BigDecimal total, BigDecimal iva) {
		super(idOrden, fechaFormateada, subtotal, estado, items);
		this.total = total;
		this.iva = iva;
		
	}

	public OrdenListadoResponse(Orden orden, List<ItemOrden> itemsOrden) {
		super(orden.getIdOrden(),
				orden.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
				orden.getSubtotal(),
				orden.getEstado(),
				ItemOrdenResponse.getItemsOrdenResposne(itemsOrden));
		this.total = orden.getTotal();
		this.iva=orden.getIva();
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}
	
	
}
