package com.restaurante.proyecto.models.entity;

import java.util.List;

public class User {

	String token;
	String session;
	String id;
	int numeroTareas;
	Object especialidad;

	public User() {
	}

	public User(String token, String session, String id, int numeroTareas) {

		this.token = token;
		this.session = session;
		this.id = id;
		this.numeroTareas = numeroTareas;
	}

	public User(String token, String session, String id, int numeroTareas, Object especialidad) {
		this.token = token;
		this.session = session;
		this.id = id;
		this.numeroTareas = numeroTareas;
		this.especialidad = especialidad;
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof User) && obj == null) {
			return false;
		}
		User object = (User) obj;
		return object.id.equals(this.id);
	}

	public String token() {
		return this.token;
	}

	public String session() {
		return this.session;
	}

	public String id() {
		return this.id;
	}

	public int numeroTareas() {
		return this.numeroTareas;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNumeroTareas(int numeroTareas) {
		this.numeroTareas = numeroTareas;
	}

	public String getToken() {
		return token;
	}

	public String getSession() {
		return session;
	}

	public String getId() {
		return id;
	}

	public int getNumeroTareas() {
		return numeroTareas;
	}

	public Object getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Object especialidad) {
		this.especialidad = especialidad;
	}

}
