package com.restaurante.proyecto.models.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="idMesero")
	private Mesero mesero;
	
	@JoinColumn(name="fecha")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fecha;
	private BigDecimal subtotal;
	private BigDecimal iva;
	private BigDecimal total;
	private String estado="ACTIVA";

	
	
	@PrePersist
	private void crearFecha() {
		if(fecha==null) {
			ZoneId zoneId = ZoneId.of("America/Mexico_City");
	        
	        // Obtiene la fecha y hora actual en la zona horaria especificada
	        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
	        
	        // Convierte ZonedDateTime a LocalDateTime
	        LocalDateTime l = zonedDateTime.toLocalDateTime();
	        
	        this.fecha = l;
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

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
