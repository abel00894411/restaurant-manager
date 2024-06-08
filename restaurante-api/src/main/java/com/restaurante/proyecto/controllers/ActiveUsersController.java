package com.restaurante.proyecto.controllers; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.proyecto.models.entity.Empleado;
import com.restaurante.proyecto.security.filters.AuthSocketInterceptor;
import com.restaurante.proyecto.services.UsuarioService;

@RestController()
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class ActiveUsersController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/usuarios/activos")
	public ResponseEntity<?> empleadosActivos() {
		
		List<Map<String, Object>> response =  AuthSocketInterceptor.empleadosActivos.stream().map( e->{
			Map<String, Object> r = new HashMap<String, Object>();
			var empleado = usuarioService.obtenerPorId(Long.parseLong(e.getId()));
			r.put("id", e.getId());
			r.put("tipo", empleado.get("puesto"));
			return r;
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok(response);
	}

	
	
}
