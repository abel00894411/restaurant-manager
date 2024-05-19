package com.restaurante.proyecto.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.restaurante.proyecto.dtos.FacturaDTO;
import com.restaurante.proyecto.dtos.FacturaResponse;
import com.restaurante.proyecto.services.FacturaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class FacturaController {

	private final SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ADMINISTRADOR");
	@Autowired
	private FacturaService facturaService;

	@GetMapping("/facturas")
	public ResponseEntity<?> obtenerFacturas(@RequestParam(required = false) @Email String email) {
		System.out.println(email);

		Map<String, Object> response = new HashMap<String, Object>();

		if (email != null) {
			List<FacturaDTO> facturas = facturaService.getFacturas(email);
			response.put("facturas", facturas);
			return ResponseEntity.ok(response);

		}
		if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(adminAuthority)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		List<FacturaDTO> facturas = facturaService.getAll();
		response.put("facturas", facturas);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/facturas")
	public ResponseEntity<?> crearFactura(@RequestBody @Valid FacturaDTO facturaDTO) {

		Map<String, Object> response = new HashMap<String, Object>();

		try {
			FacturaResponse createdFactura = facturaService.createFactura(facturaDTO);

			response.put("mensaje", "Factura creada con exito");
			response.put("factura", createdFactura);

			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			response.put("mensaje", "Peticion incorrecta");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@DeleteMapping("/factura/{id}")
	public ResponseEntity<?> eliminarFactura(@PathVariable Long id) {

		try {
			facturaService.deleteFactura(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "Peticion incorrecta");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

	}

}
