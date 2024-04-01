package com.restaurante.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.proyecto.models.entity.Administrador;
import com.restaurante.proyecto.models.entity.Empleado;
import com.restaurante.proyecto.repositories.IAdministradorRepository;

@RestController
public class PruebaController {

	@Autowired
	private IAdministradorRepository administradorRepository;
	
	@GetMapping("/prueba")
	public ResponseEntity<?> getMethodName() {
		//para que cales los ejemplos agrega inserts en empleado y en administrador
		
		
//		List<Administrador> a= administradorRepository.buscarPorEjemplo("Juan",4); //ejemplo, si quieres calalo
		Administrador a = administradorRepository.ejemploBuscarPorRFC("IJKL345678"); // debes poner aqui el rfc que quieres
		System.out.println(a.getIdAdministrador().getIdEmpleado()); // el id de un administrador es un empleado, esta chistoso pero la culpa la tiene gerry
		
		return ResponseEntity.ok(a);
	}
	
	
}
