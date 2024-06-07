package com.restaurante.proyecto.controllers; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.restaurante.proyecto.security.filters.AuthSocketInterceptor;

@RestController()
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class ActiveUsersController {
	
	
	@GetMapping("/activos")
	public ResponseEntity<?> empleadosActivos() {
		
		List<Map<String, Object>> response =  AuthSocketInterceptor.empleadosActivos.stream().map( e->{
			Map<String, Object> r = new HashMap<String, Object>();
			r.put("id", e.getId());
			return r;
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok(response);
	}

	
	
}
