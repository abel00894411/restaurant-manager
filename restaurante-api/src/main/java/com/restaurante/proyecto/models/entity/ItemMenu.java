package com.restaurante.proyecto.models.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="itemsmenu")
public class ItemMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idItemMenu")
	private Long idItemMenu;
	
	private String producto;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="idCategoria")
	private Categoria categoria;
	
	
	@Column(name="precio")
	private BigDecimal precio=new BigDecimal(0);
	
	@Column(name="descripcion")
	private String descripcion;
	
	private String imagen;

	public Long getIdItemMenu() {
		return idItemMenu;
	}

	public void setIdItemMenu(Long idItemMenu) {
		this.idItemMenu = idItemMenu;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
