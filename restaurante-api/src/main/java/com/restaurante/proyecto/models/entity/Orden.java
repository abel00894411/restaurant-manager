package com.restaurante.proyecto.models.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "ordenes")
public class Orden {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idOrden")
	private Long idOrden;
	
	@ManyToOne
	@JoinColumn(name="idMesero")
	private Mesero mesero;
	
	
	
	@JoinColumn(name="fecha")
	private LocalDateTime fecha;

	
	@PrePersist
	void asignarFecha(){
		
		if(this.fecha == null) {
			this.fecha = LocalDateTime.now();
		}
	}
	
	public Long getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(Long idOrden) {
		this.idOrden = idOrden;
	}

	public Mesero getMesero() {
		return mesero;
	}

	public void setMesero(Mesero mesero) {
		this.mesero = mesero;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
