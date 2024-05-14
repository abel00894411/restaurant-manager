package com.restaurante.proyecto.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.proyecto.models.entity.Categoria;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long>{

	
	public Optional<Categoria> findBycategoria(String categoria);
}
