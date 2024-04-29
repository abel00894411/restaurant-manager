package com.restaurante.proyecto.models.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="categorias")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCategoria")
	private Long idCategoria;
	
	@ManyToMany(mappedBy = "categorias")
	@JsonIgnore
	private List<Cocinero> cocineros;
	
	private String categoria;

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
	
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	

	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof Categoria) || obj==null) {
			return false;
		}
		Categoria categoria = (Categoria) obj;
//		System.out.println("\n\tRealizó comparacion con el emtodo equals en clase Categoria"
//				+ "\n"+categoria.getCategoria()+" == "+this.categoria
//				+ "\n\t");
		return this.categoria.equals(categoria.getCategoria());
	}
	
	
	
	
	
	
	
	
}
