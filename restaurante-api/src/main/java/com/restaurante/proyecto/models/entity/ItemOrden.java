package com.restaurante.proyecto.models.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="itemsorden")
public class ItemOrden {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idItemOrden")
	private Long idItemOrden;
	
	
	@ManyToOne
	@JoinColumn(name="idItemMenu")
	private ItemMenu itemMenu;
	
	@ManyToOne
	@JoinColumn(name="idOrden")
	private Orden orden;
	
	@ManyToOne
	@JoinColumn(name="idCocinero")
	private Cocinero cocinero;
	
	@Column(name = "estado",length = 20)
	private String estado = "PENDIENTE";
	
	@Column(name="cantidad")
	private Integer cantidad;
	
	@Column(name="suma" )
	private BigDecimal suma;
	

	public Long getIdItemOrden() {
		return idItemOrden;
	}

	public void setIdItemOrden(Long idItemOrden) {
		this.idItemOrden = idItemOrden;
	}

	public Orden getOrden() {
		return orden;
	}

	public void setOrden(Orden orden) {
		this.orden = orden;
	}

	public ItemMenu getItemMenu() {
		return itemMenu;
	}

	public void setItemMenu(ItemMenu itemMenu) {
		this.itemMenu = itemMenu;
	}

	public Cocinero getCocinero() {
		return cocinero;
	}

	public void setCocinero(Cocinero cocinero) {
		this.cocinero = cocinero;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getSuma() {
		return suma;
	}

	public void setSuma(BigDecimal suma) {
		this.suma = suma;
	}
	
	
	
	
	
	
	
	
	
	
	
}
