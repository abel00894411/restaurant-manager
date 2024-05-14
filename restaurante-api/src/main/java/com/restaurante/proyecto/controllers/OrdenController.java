package com.restaurante.proyecto.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.restaurante.proyecto.dtos.OrdenListarResponse;
import com.restaurante.proyecto.services.OrdenService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class OrdenController {
	
	@Autowired
	private OrdenService ordenService;

	@GetMapping("/ordenes")
	public ResponseEntity<?> getMethodName(
			@RequestParam(value = "dateMin", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateMin,
			@RequestParam(value = "dateMax", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateMax) {

		if (dateMin == null) {
			dateMin = LocalDate.now();
			System.out.println("valor por defecto: " + dateMin.toString());
		}
		if (dateMax == null) {
			dateMax = LocalDate.now();
		}
		
		List<OrdenListarResponse> resultados = ordenService.listarOrdenesPorFecha(dateMin, dateMax);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("ordenes", resultados);

		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/orden/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
		
		OrdenListarResponse resultado = ordenService.obtenerPorId(id).get(0);
		Map<String , Object> response = new HashMap<String, Object>();
		response.put("orden", resultado);
		
		return ResponseEntity.ok(response);

	}
	
	
	@DeleteMapping("/orden/{id}")
	public ResponseEntity<?> eliminarPorId(@PathVariable Long id){
		ordenService.eliminarPorId(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}

