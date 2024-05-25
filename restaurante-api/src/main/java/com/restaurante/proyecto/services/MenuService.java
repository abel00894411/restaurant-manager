package com.restaurante.proyecto.services;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.proyecto.dtos.ItemMenuCrear;
import com.restaurante.proyecto.dtos.ItemMenuResponse;
import com.restaurante.proyecto.models.entity.Categoria;
import com.restaurante.proyecto.models.entity.ItemMenu;
import com.restaurante.proyecto.repositories.ICategoriaRepository;
import com.restaurante.proyecto.repositories.IItemMenuRepository;
import com.restaurante.proyecto.utils.ImageHelper;

@Service
public class MenuService {

	private final String imagesPath = "src\\main\\resources\\images\\itemsMenu\\";
	
	@Autowired
	private ICategoriaRepository categoriaRepository;
	@Autowired
	private IItemMenuRepository itemMenuRepository;
	
	
	public ItemMenu crearItemMenu(ItemMenuCrear itemMenuCrear){
		
		String nombre = itemMenuCrear.producto()+LocalTime.now().format( DateTimeFormatter.ofPattern("HH:mm:ss") ).replace(":", "_");
		
		ImageHelper.crearImgen(nombre, imagesPath, itemMenuCrear.imagen());
		
		ItemMenu itemMenu = new ItemMenu();
		
		Categoria categoria = categoriaRepository.findById(itemMenuCrear.idCategoria())
												.orElseThrow( ()-> new RuntimeException("No existe esa categoria") );
		itemMenu.setCategoria(categoria);
		itemMenu.setDescripcion(itemMenuCrear.descripcion());
		itemMenu.setImagen(nombre);
		itemMenu.setPrecio(itemMenuCrear.precio());
		itemMenu.setProducto(itemMenuCrear.producto());
		
		itemMenuRepository.save(itemMenu);
		
		return itemMenu;
	}


	public List<ItemMenuResponse> obtenerMenu() {

		List<ItemMenuResponse> itemsMenu = itemMenuRepository.findAll().stream().map(  item->{
			byte[] imagen =ImageHelper.obtenerImagenBytes(item.getImagen(), imagesPath);
			
			return ItemMenuResponse.crearItem(item, imagen);
		}).collect(Collectors.toList());
		return itemsMenu;
	}


	public void actualizarItemMenu(Long id, ItemMenuCrear itemMenuActualizar) {
		
		ItemMenu itemMenu = itemMenuRepository.findById(id).orElseThrow(()-> new RuntimeException("No existe ese itemMenu"));
		Categoria categoria =  categoriaRepository.findById(itemMenuActualizar.idCategoria()).orElseThrow(()->new RuntimeException("No existe esa cateoria"));
		
		
		itemMenu.setCategoria(categoria);
		itemMenu.setDescripcion(itemMenuActualizar.descripcion());
		itemMenu.setPrecio(itemMenuActualizar.precio());
		itemMenu.setProducto(itemMenuActualizar.producto());
		
		itemMenuRepository.save(itemMenu);
		
	}


	public void eliminarItemMenu(Long id) {
		ItemMenu itemMenu = itemMenuRepository.findById(id).orElseThrow(()->new RuntimeException("No existe ese itemMenu"));
		ImageHelper.eliminarImagen(itemMenu.getImagen(), imagesPath);
		itemMenuRepository.deleteById(id);	
	}
	
}
