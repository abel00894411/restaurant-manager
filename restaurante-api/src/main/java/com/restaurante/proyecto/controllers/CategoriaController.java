package com.restaurante.proyecto.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.proyecto.dtos.CategoriaRequest;
import com.restaurante.proyecto.models.entity.Categoria;
import com.restaurante.proyecto.services.CategoriaService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class CategoriaController {
	

    @Autowired
    private CategoriaService categoriaService;
	
    
    @GetMapping("/categorias")
    public ResponseEntity<?> obtenerCategorias() {
        List<Categoria> categorias = categoriaService.findAll();
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("categorias", categorias);
        return ResponseEntity.ok(response);
    }
    
    
    @PostMapping("/categorias")
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria) {
        
    	Categoria nuevaCategoria = categoriaService.save(categoria);
    	Map<String, Object> response = new HashMap<String, Object>();
    	Map<String, Object> cat = new HashMap<String, Object>();
    	
    	cat.put("id", nuevaCategoria.getIdCategoria());
    	cat.put("categoria", nuevaCategoria.getCategoria());
        response.put("categoria", cat);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    
    
    
    @PutMapping("/categoria/{id}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaRequest categoria) {
        
        categoriaService.update(id, categoria.categoria());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);        
    }
    
    @DeleteMapping("/categoria/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) {
        categoriaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    
    
    
    
	
}
















