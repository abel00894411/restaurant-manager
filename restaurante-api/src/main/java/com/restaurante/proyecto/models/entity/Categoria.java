package com.restaurante.proyecto.models.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="categoria")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCategoria")
	private Long idCategoria;
	
	@ManyToMany(mappedBy = "categorias")
	private List<Cocinero> cocineros;

	public Long getIdCategoria() {
		return idCategoria;
	}
 
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public List<Cocinero> getCocineros() {
		return cocineros;
	}

	public void setCocineros(List<Cocinero> cocineros) {
		this.cocineros = cocineros;
	} 
	
	
	
	
	
	
	
	
	
	
	
	
	
}
