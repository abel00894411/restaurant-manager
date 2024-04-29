package com.restaurante.proyecto.dtos;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.restaurante.proyecto.models.entity.ItemOrden;
import com.restaurante.proyecto.models.entity.Orden;

public class OrdenDTO {

	private Long idOrden ;
	private String fecha ;
	private BigDecimal subtotal ;
	private String estado ;
	private List<ItemOrdenResponse> items ;

	public OrdenDTO(Long idOrden, String fechaFormateada, BigDecimal subtotal, String estado,
			List<ItemOrdenResponse> items) {
		this.idOrden = idOrden;
		this.fecha = fechaFormateada;
		this.subtotal = subtotal;
		this.estado=estado;
		this.items = items;
	}

	public Long getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(Long idOrden) {
		this.idOrden = idOrden;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<ItemOrdenResponse> getItems() {
		return items;
	}

	public void setItems(List<ItemOrdenResponse> items) {
		this.items = items;
	}
	
	
	

//	public static OrdenDTO getOrdenResponse(Orden orden, List<ItemOrden> itemsOrden) {
//
//		List<ItemOrdenResponse> itemsOrdenResponse = ItemOrdenResponse.getItemsOrdenResposne(itemsOrden);
//
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		String fechaFormateada = orden.getFecha().format(formatter);
//
//		OrdenDTO ordenDTO = new OrdenDTO(
//				orden.getIdOrden(),
//				fechaFormateada,
//				orden.getSubtotal(),
//				orden.getEstado(),
//				itemsOrdenResponse);
//
//		return ordenDTO;
//	}

}
