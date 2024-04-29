package com.restaurante.proyecto.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.proyecto.dtos.ItemOrdenActualizarRequest;
import com.restaurante.proyecto.dtos.ItemOrdenListado;
import com.restaurante.proyecto.dtos.ItemOrdenRequest;
import com.restaurante.proyecto.models.entity.Categoria;
import com.restaurante.proyecto.models.entity.Cocinero;
import com.restaurante.proyecto.models.entity.ItemMenu;
import com.restaurante.proyecto.models.entity.ItemOrden;
import com.restaurante.proyecto.models.entity.User;
import com.restaurante.proyecto.repositories.ICocineroRepository;
import com.restaurante.proyecto.repositories.IItemMenuRepository;
import com.restaurante.proyecto.repositories.IItemOrdenRepository;
import com.restaurante.proyecto.security.filters.AuthSocketInterceptor;

@Service
public class ItemOrdenService {
	
	@Autowired
	private IItemOrdenRepository itemOrdenRepository;
	@Autowired
	private IItemMenuRepository itemMenuRepository;
	@Autowired
	private ICocineroRepository cocineroRepository;
	
	
	

	public List<ItemOrdenListado> listadoDeItems(Long id) {

		List<ItemOrdenListado> itemsOrdenListado = ItemOrdenListado
				.crearListado(itemOrdenRepository.obtenerItemsPendientesActuales(id));
		return itemsOrdenListado;
	}

	public ItemOrden actualizarItemOrden(ItemOrdenActualizarRequest itemOrdenActualizarRequest) {

		if (!(itemOrdenActualizarRequest.estado().equals("PENDIENTE") || 
			 itemOrdenActualizarRequest.estado().equals("PREPARADO")  ||
			 itemOrdenActualizarRequest.estado().equals("SERVIDO"))) {
			throw new RuntimeException("No se puede establecer ese estado a un item");
		}

		ItemOrden item = itemOrdenRepository.findById(itemOrdenActualizarRequest.idItemOrden())
															.orElseThrow( ()-> new RuntimeException("No existe ese itemorden")  );
		
		item.setEstado(itemOrdenActualizarRequest.estado());
		item =itemOrdenRepository.save(item);
		
		return item;
	}
	
	
	
	public List<ItemOrden> crearItemsOrden( List<ItemOrdenRequest> itemsOrdenRequest){
		List<ItemOrden> itemsOrden = new ArrayList<ItemOrden>();
		for (ItemOrdenRequest item : itemsOrdenRequest) {
			System.out.println("se buscará en el repositorio de itemsMenu el id: "+item.idItemMenu());
			ItemMenu itemMenu = itemMenuRepository.findById(item.idItemMenu()).orElseThrow( ()-> new RuntimeException("No se encontró ese itemmenu") );
			System.out.println("itemMenu: "+itemMenu.getCategoria().getIdCategoria());
			ItemOrden itemOrden = new ItemOrden();
			itemOrden.setCantidad(item.cantidad());
			itemOrden.setItemMenu(itemMenu);
			itemOrden.setSuma(BigDecimal.valueOf(itemOrden.getCantidad()).multiply(itemMenu.getPrecio()));
			Cocinero cocinero = asignarCocinero(itemMenu);
			itemOrden.setCocinero(cocinero);
			itemsOrden.add(itemOrden);

		}
		return itemsOrden;
	}
	
	
	public Cocinero asignarCocinero(ItemMenu itemMenu) {
		List<User> cocinerosEspecializados = new ArrayList<User>();
		User usuarioAsignado;
		System.out.println("item a buscar cocineros"+itemMenu.getCategoria().getCategoria());
		for (User cocinero : AuthSocketInterceptor.cocinerosActivos) {
			//se asume que existen las categorias ya que se encuentra en la lista de cocineros
			List<Categoria> categorias = (List<Categoria>) cocinero.getEspecialidad();
			System.out.println("\n\t\tCATEGORIAS DEL USER");
			categorias.forEach(cat -> System.out.println(cat.getCategoria()));
			System.out.println("Categorias del cocinero:>"+cocinero.getSession()+"contiene la categoria? "+categorias.contains(itemMenu.getCategoria())+" ");
			if (categorias.contains(itemMenu.getCategoria())) {
				
				int indice = AuthSocketInterceptor.empleadosActivos.indexOf(cocinero);
				cocinerosEspecializados.add(AuthSocketInterceptor.empleadosActivos.get(indice));
			}
		}
		usuarioAsignado = cocinerosEspecializados.get(0); //si está vacia ya lanza el error
		usuarioAsignado.setNumeroTareas(usuarioAsignado.numeroTareas() + 1);
		for (User cocinero : cocinerosEspecializados) {
			if (cocinero.numeroTareas() < usuarioAsignado.numeroTareas()) {
				usuarioAsignado.setNumeroTareas(usuarioAsignado.numeroTareas() - 1);
				usuarioAsignado = cocinero;
				usuarioAsignado.setNumeroTareas( usuarioAsignado.getNumeroTareas() + 1 );
			}
		}
		return cocineroRepository.findById(Long.valueOf(usuarioAsignado.getId())).get();

	}

}
