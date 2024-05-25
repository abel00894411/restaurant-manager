package com.restaurante.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.proyecto.models.entity.Categoria;
import com.restaurante.proyecto.repositories.ICategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private ICategoriaRepository categoriaRepository;

	public List<Categoria> findAll() {

		return categoriaRepository.findAll();
	}

	public Categoria save(Categoria cat) {

		Categoria categoria = new Categoria();
		categoria.setCategoria(cat.getCategoria());
		
		categoria = categoriaRepository.save(categoria);
		
		return categoria;
	}

	public void update(Long id, String categoria) {
		
		Categoria cat = categoriaRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontro esa cateogira"));
		cat.setCategoria(categoria);
		categoriaRepository.save(cat);
	}

	public void delete(Long id) {
		categoriaRepository.deleteById(id);
	}

}
