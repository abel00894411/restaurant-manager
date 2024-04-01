package com.restaurante.proyecto.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="administradores")
public class Administrador {

	
    @Id
    @Column(name = "idAdministrador")
    private Long adminId;
	
    @MapsId
	@OneToOne
//	@Id
	@JoinColumn(name = "idAdministrador")
	private Empleado idAdministrador;

	public Empleado getIdAdministrador() {
		return idAdministrador;
	}

	public void setIdAdministrador(Empleado idAdministrador) {
		this.idAdministrador = idAdministrador;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
	
	
	
	
	
	
}
