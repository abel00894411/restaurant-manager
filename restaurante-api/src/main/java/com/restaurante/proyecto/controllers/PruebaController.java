package com.restaurante.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.proyecto.models.entity.Administrador;
import com.restaurante.proyecto.models.entity.Empleado;
import com.restaurante.proyecto.repositories.IAdministradorRepository;
import com.restaurante.proyecto.repositories.IEmpleadoRepository;
import com.restaurante.proyecto.repositories.IMeseroRepository;

@RestController
public class PruebaController {

	@Autowired
	private IEmpleadoRepository empleadoRepository;
	@Autowired
	private IAdministradorRepository administradorRepository;
	@Autowired
	private IMeseroRepository meseroRepository;
	@Autowired
	private PasswordEncoder encoder;
	
	
	@GetMapping("/prueba")
	public ResponseEntity<?> getMethodName() {
		
		System.out.println(encoder.encode("1234"));
		Administrador a = administradorRepository.ejemploBuscarPorRFC("IJKL345678");
		System.out.println(a.getIdAdministrador().getIdEmpleado()); 
		
		return ResponseEntity.ok(a);
	}
	
	
	
	@GetMapping("/obtenerPorId/{id}")
	public Object getMethodName(@PathVariable Long id) {
		return administradorRepository.getById(id).getIdAdministrador();
	}
	
	@GetMapping("/obtenerTodosPrueba")
	public Object obtenerTodosPrueba() {
		
		return empleadoRepository.encontrarTodos();
	}
	
}
