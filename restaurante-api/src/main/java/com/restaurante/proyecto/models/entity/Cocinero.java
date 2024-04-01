package com.restaurante.proyecto.models.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="cocineros")
public class Cocinero {

	@Id
	@Column(name="idCocinero")
	private Long cocineroId;;
	
	@MapsId
	@OneToOne
//	@Id
	@JoinColumn(name="idCocinero")
	private Empleado idCocinero;
	
	@ManyToMany
	@JoinTable(
			
			name = "cocineros_categorias",
			joinColumns = @JoinColumn(name="idCocinero"),
			inverseJoinColumns = @JoinColumn(name="idCategoria")
			
			)
	private List<Categoria> categorias;

	public Empleado getIdCocinero() {
		return idCocinero;
	}

	public void setIdCocinero(Empleado idCocinero) {
		this.idCocinero = idCocinero;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Long getCocineroId() {
		return cocineroId;
	}

	public void setCocineroId(Long cocineroId) {
		this.cocineroId = cocineroId;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
