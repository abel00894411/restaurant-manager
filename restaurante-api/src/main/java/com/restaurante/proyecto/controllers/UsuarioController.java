package com.restaurante.proyecto.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.restaurante.proyecto.dtos.EmpleadoResponse;
import com.restaurante.proyecto.dtos.UsuarioActualizar;
import com.restaurante.proyecto.dtos.UsuarioCambiarPassword;
import com.restaurante.proyecto.dtos.UsuarioCrear;
import com.restaurante.proyecto.models.entity.Empleado;
import com.restaurante.proyecto.services.JwtService;
import com.restaurante.proyecto.services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private JwtService jwtService;
	
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
		System.out.println("===");
		return ResponseEntity.ok(responseBody);
	}
	
	@PostMapping("/usuarios")
	public ResponseEntity<?> crearUsuario(@RequestBody UsuarioCrear usuarioCrear) {
		
		Map<String, Object> response = usuarioService.crearEmpleado(usuarioCrear);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	@PutMapping("/usuario/{id}")
	public ResponseEntity<?> putMethodName(@PathVariable Long id, @RequestBody UsuarioActualizar actualizar) {
		
		usuarioService.actualizarEmpleado( actualizar, id);	
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	
	@PatchMapping("/usuario/{id}")
	public ResponseEntity<?> actualizarPassword(@PathVariable("id") Long id,
			@RequestBody UsuarioCambiarPassword cambiarPassword,
			HttpServletRequest request
			){
		String token = request.getHeader("Authorization").replace("Bearer ", "");
		String idFromToken = jwtService.extractIdUsuario(token);
		
		if( !idFromToken.equals(id.toString()) ) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		usuarioService.cambiarPassword(cambiarPassword.password(), id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	
	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
		usuarioService.eliminarEmpleado(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}

