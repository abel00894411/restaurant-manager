package com.restaurante.proyecto.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="meseros")
public class Mesero {

	
	@Id
	@Column(name="idMesero")
	private Long meseroId;
	
	@MapsId
	@OneToOne
//	@Id
	@JoinColumn(name = "`idMesero`")
	private Empleado idMesero;

	public Empleado getIdMesero() {
		return idMesero;
	}

	public void setIdMesero(Empleado idMesero) {
		this.idMesero = idMesero;
	}

	public Long getMeseroId() {
		return meseroId;
	}

	public void setMeseroId(Long meseroId) {
		this.meseroId = meseroId;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
