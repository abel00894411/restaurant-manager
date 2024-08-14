package com.restaurante.proyecto.models.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name ="empleados")
public class Empleado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEmpleado")
	private Long idEmpleado;
	
	private String nombre;
	
	private String apellido;
	
	private String password;
	
	@Column(unique = true)
	private String rfc;
	
	private BigDecimal sueldo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="fechaContratacion")
	private Date fechaContratacion;
	
	
	@PrePersist
	void createAt(){
		this.fechaContratacion = new Date();
	}


	public Long getIdEmpleado() {
		return idEmpleado;
	}


	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRfc() {
		return rfc;
	}


	public void setRfc(String rfc) {
		this.rfc = rfc;
	}


	public BigDecimal getSueldo() {
		return sueldo;
	}


	public void setSueldo(BigDecimal sueldo) {
		this.sueldo = sueldo;
	}


	public Date getFechaContratacion() {
		return fechaContratacion;
	}


	public void setFechaContratacion(Date fechaContratacion) {
		this.fechaContratacion = fechaContratacion;
	}
	
	
	
	
}
