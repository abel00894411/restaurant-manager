package com.restaurante.proyecto.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.restaurante.proyecto.dtos.EmpleadoResponse;
import com.restaurante.proyecto.models.entity.Empleado;
import com.restaurante.proyecto.services.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController // no se agrega la ruta '/usuarios' o '/usuario' porque no todos tienen el '/usuario' algunos son '/usuarios' con s.
// entonces toca poner la ruta a cada controlador a mano
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/usuarios") //recuerda que aqui se pone esta ruta, pero si se repiten en todos los controladores, se puede poner en restcontroller
	//(en la anotacion de arriba del nombre de la clase) @RestController("/usuarios"), y todos los controladores aqui dentro ya tendrán esa
	//ruta diferente, y ya solo cambias si reciben parametros o no, si son get,post,put,etc
	//en este caso cada controlador va a tener su prefijo ya sea /usuarios o /usuario, porque no pusimos bien los nombres jsjs
	//checa el documento con las tablitas
	public ResponseEntity<?> obtenerUsuarios() {
		// no te preocupes por los demas estados de 401,403,etc. de eso me encargo jsjs
		List<Empleado> resultado = usuarioService.obtenerUsuarios();
		
		//aqui tambien pudo ser 
		//Map<String,Object> ya que todo las clases son Object (incluso String, menos int, char, double: son primitivos)
		Map<String, List<Empleado>> respuesta = new HashMap<String, List<Empleado>>();
		respuesta.put("usuarios", resultado);
		return ResponseEntity.ok(respuesta);
	}
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable("id") Long id) {
		// no te preocupes por los demas estados de 401,403,etc. de eso me encargo jsjs
		List<Object[]> resultado = usuarioService.obtenerPorId(id);
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		for(Object[] obj : resultado) {
			response.put("id", obj[0]);
			response.put("nombre", obj[1]);
			response.put("apellido", obj[2]);
			response.put("puesto", obj[3]);
			response.put("rfc", obj[4]);
			response.put("sueldo", obj[5]);
			response.put("fechaContratacion", obj[6]);
		}
		
		return ResponseEntity.ok(response);
	}
}
