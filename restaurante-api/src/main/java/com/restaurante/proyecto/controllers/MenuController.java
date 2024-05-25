package com.restaurante.proyecto.controllers;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.proyecto.dtos.ItemMenuCrear;
import com.restaurante.proyecto.dtos.ItemMenuResponse;
import com.restaurante.proyecto.models.entity.ItemMenu;
import com.restaurante.proyecto.services.MenuService;
import com.restaurante.proyecto.utils.ImageHelper;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	
	@GetMapping("/menu")
	public ResponseEntity<?> obtenerMenu() {
//		 HttpHeaders headers = new HttpHeaders();
//		 headers.setContentType(MediaType.IMAGE_JPEG); 
//		return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
		List<ItemMenuResponse> itemsMenuResponse = menuService.obtenerMenu();
		Map<String, Object> response = new HashMap<String, Object>();
		
		response.put("itemsMenu", itemsMenuResponse);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/menu")
	public ResponseEntity<?> crearItemMenu(@RequestBody ItemMenuCrear itemMenuCrear) {
		ItemMenu itemMenu = menuService.crearItemMenu(itemMenuCrear);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("itemMenu", itemMenu);
		return ResponseEntity.ok("simon");
	}
	
	
	@PutMapping("/menu/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ItemMenuCrear itemMenuActualizar) {
		
		menuService.actualizarItemMenu(id,itemMenuActualizar);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	
	@DeleteMapping("/menu/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		
		menuService.eliminarItemMenu(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
