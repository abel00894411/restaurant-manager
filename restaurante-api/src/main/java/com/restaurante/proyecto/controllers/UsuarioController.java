package com.restaurante.proyecto.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.restaurante.proyecto.dtos.EmpleadoResponse;
import com.restaurante.proyecto.models.entity.Empleado;
import com.restaurante.proyecto.services.UsuarioService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/usuarios") 
	public ResponseEntity<?> obtenerUsuarios() {
		List<EmpleadoResponse> resultado = usuarioService.obtenerUsuarios();
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("usuarios", resultado);
		return ResponseEntity.ok(respuesta);
	}
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable("id") Long id) {
		Map<String, Object>  response = usuarioService.obtenerPorId(id);
		Map<String, Object> responseBody = new HashMap<String, Object>();
		responseBody.put("usuario", response);
		
		return ResponseEntity.ok(responseBody);
	}
}
