package com.restaurante.proyecto.controllers;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Email;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class FacturaController {

	private final SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ADMINISTRADOR");
	
	@GetMapping("/facturas")
	public Object obtenerFacturas(@RequestParam(required = false) @Email String email) {
		System.out.println(email);
		
		if(email!=null) {
			// que regrese un response entity con las factuaras del cliente
			
			//return ResponseEntity
			return ResponseEntity.ok().build();
		}
		if(!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(adminAuthority)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		// que regrese el listado de todas las facturas ya que si no 
		
			
		return null;
	}

	
	@PostMapping("/facturas")
	public String crearFactura(@RequestBody String entity) {
		
		// solo para crear facturas de ordens ya DESPACHADA
		return entity;
	}
	
	
	@DeleteMapping("/factura/{id}")
	public ResponseEntity<?> eliminarFactura(@RequestParam Long id){
		
		System.out.println(id);
		return null;
		
	}
	
}
