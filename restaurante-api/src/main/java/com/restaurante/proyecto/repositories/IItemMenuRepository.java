package com.restaurante.proyecto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.proyecto.models.entity.ItemMenu;

@Repository
public interface IItemMenuRepository  extends JpaRepository<ItemMenu, Long>{

}
