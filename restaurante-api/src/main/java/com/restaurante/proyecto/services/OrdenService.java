package com.restaurante.proyecto.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.proyecto.dtos.ItemMenuResponse;
import com.restaurante.proyecto.dtos.ItemOrdenActualizarRequest;
import com.restaurante.proyecto.dtos.ItemOrdenAgregadoResponse;
import com.restaurante.proyecto.dtos.ItemOrdenListado;
import com.restaurante.proyecto.dtos.ItemOrdenListar;
import com.restaurante.proyecto.dtos.ItemOrdenRequest;
import com.restaurante.proyecto.dtos.ItemOrdenResponse;
import com.restaurante.proyecto.dtos.OrdenAsignadoResponse;
import com.restaurante.proyecto.dtos.OrdenListadoResponse;
import com.restaurante.proyecto.dtos.OrdenListarResponse;
import com.restaurante.proyecto.dtos.OrdenLlenarRequest;
import com.restaurante.proyecto.dtos.OrdenRequest;
import com.restaurante.proyecto.models.entity.Categoria;
import com.restaurante.proyecto.models.entity.Cocinero;
import com.restaurante.proyecto.models.entity.Empleado;
import com.restaurante.proyecto.models.entity.ItemMenu;
import com.restaurante.proyecto.models.entity.ItemOrden;
import com.restaurante.proyecto.models.entity.Mesero;
import com.restaurante.proyecto.models.entity.Orden;
import com.restaurante.proyecto.models.entity.User;
import com.restaurante.proyecto.repositories.ICocineroRepository;
import com.restaurante.proyecto.repositories.IEmpleadoRepository;
import com.restaurante.proyecto.repositories.IItemMenuRepository;
import com.restaurante.proyecto.repositories.IItemOrdenRepository;
import com.restaurante.proyecto.repositories.IMeseroRepository;
import com.restaurante.proyecto.repositories.IOrdenRepository;
import com.restaurante.proyecto.security.filters.AuthSocketInterceptor;

@Service
public class OrdenService {
	
	public static BigDecimal iva = new BigDecimal(0.16);

	@Autowired
	private IMeseroRepository meseroRepository;
	@Autowired
	private IOrdenRepository ordenRepository;
	@Autowired
	private IItemOrdenRepository itemOrdenRepository;
	@Autowired
	private ItemOrdenService itemOrdenService;
	

	public Map<String, Object> crearOrden(OrdenRequest ordenRequest) {
		Orden orden = new Orden();
		
		ordenRequest.items().forEach(x->{
			System.out.println(x.idItemMenu());
		});
		
		List<ItemOrden> itemsOrden = itemOrdenService.crearItemsOrden(ordenRequest.items());
		BigDecimal subtotal = calcularSubtotal(itemsOrden);

		
		// ya esta el trigger que usa el procedimiento almacenado para actualizar la orden una vez que los items sean insertadas
		// por lo que calcular el total, subtotal y el iva está de más
		orden.setSubtotal(subtotal);
		orden.setIva(subtotal.multiply(iva));
		orden.setTotal( subtotal.add(orden.getIva()) );
		Mesero mesero = asignarMesero();
		orden.setMesero(mesero);
		orden = ordenRepository.save(orden);
		asignarOrdenAItems(itemsOrden, orden);
		itemOrdenRepository.saveAll(itemsOrden);
		Map<String, Object> ordenEItems = new HashMap<String, Object>();
		ordenEItems.put("orden", orden);
		ordenEItems.put("items", itemsOrden);
		return ordenEItems;
	}
	
	
	
	public List<Object> listadoDeOrdenes(){
		
		List<Orden> ordenes = ordenRepository.findAll();
		List<Object> ordenesResponse = new ArrayList<Object>();
		for(Orden orden: ordenes) {
			List<ItemOrden> itemsOrden = itemOrdenRepository.buscarPorIdOrden(orden.getIdOrden());
			if(orden.getEstado().equals("ACTIVA")) {
				OrdenAsignadoResponse ordenAsignadoResponse = new OrdenAsignadoResponse(orden, itemsOrden);
				ordenesResponse.add(ordenAsignadoResponse);
			}else {
				OrdenListadoResponse ordenListadoResponse = new OrdenListadoResponse(orden,itemsOrden);
				ordenesResponse.add(ordenListadoResponse);
			}
		}
		return ordenesResponse;
	}
	
	
	public Map<String, Object> llenar(OrdenLlenarRequest ordenLlenarRequest){
		
		Orden orden = ordenRepository.findById(ordenLlenarRequest.idOrden()).orElseThrow( ()->new RuntimeException("Orden inexistente"));
		
		if(!orden.getEstado().equals("ACTIVA")) {
			throw new RuntimeException("La orden ya esta despachada, no se pueden agregar maas items");
		}
		
		List<ItemOrden> itemsOrden = itemOrdenService.crearItemsOrden(ordenLlenarRequest.items());
		asignarOrdenAItems(itemsOrden, orden);
		
		Map<String, Object> response = new HashMap<String, Object>();
		itemsOrden = itemOrdenRepository.saveAll(itemsOrden);
		response.put("items", itemsOrden); // se envian los items normales y en el controlador se le da la forma a la respuesta
										// tanto para el mesero como a los cocineros
		response.put("idOrden", orden.getIdOrden());

		return response;
	}
	
	
	public Orden terminarOrden(Long idOrden) {
		
		Orden orden = ordenRepository.findById(idOrden).orElseThrow(()->new RuntimeException("No exisite esa orden a terminar"));
		orden.setIdOrden(idOrden);
		List<ItemOrden> itemsOrden= itemOrdenRepository.buscarPorIdOrden(idOrden);
		
		itemsOrden.forEach( item ->{
			if(item.getEstado().equals("PENDIENTE")) {
				throw new RuntimeException("No se puede terminar la orden, aun hay items PENDIENTES");
			}
		});
		orden.setEstado("DESPACHADA");
		orden = ordenRepository.save(orden);
		return orden;
	}
	
	
	// OrdenController (rest)
	
	
	public List<OrdenListarResponse> listarOrdenesPorFecha(LocalDate dateMin, LocalDate dateMax) {
		List<Orden> ordenes = ordenRepository.findByfechaBetween(dateMin.atStartOfDay(), dateMax.atTime( LocalTime.of(23, 59, 0)));
		List<OrdenListarResponse> resultados = crearOrdenesEnlistarResponse(ordenes);		
		return resultados;
		
	}
	
	


	public List<OrdenListarResponse> obtenerPorId(Long id) {
		
		Orden orden = ordenRepository.findById(id).orElseThrow(()->new RuntimeException("No existe esa orden"));
		List<OrdenListarResponse> resultados = crearOrdenesEnlistarResponse(Arrays.asList(orden));		
		return resultados;
	}
	
	
	
	public void eliminarPorId(Long id) {
		ordenRepository.deleteById(id);
	}
	
	
	
	
	private List<OrdenListarResponse> crearOrdenesEnlistarResponse(List<Orden> ordenes){
		List<OrdenListarResponse> resultados = new ArrayList<OrdenListarResponse>();
		for(Orden orden:ordenes) {	
			List<ItemOrden> itemsOrden = itemOrdenRepository.buscarPorIdOrden(orden.getIdOrden());
			List<ItemOrdenListar> items = itemsOrden.stream().map(i->{
				
				ItemOrdenListar item = ItemOrdenListar.obtenerItem(i);
				
				return item;
			}).collect(Collectors.toList());
			
			OrdenListarResponse ordenListarResponse = OrdenListarResponse.obtenerOrden(orden, items);
			resultados.add(ordenListarResponse);
		}
		return resultados;
	}
	
	
	//////// helpers
	
	public void asignarOrdenAItems( List<ItemOrden> itemsOrden, Orden orden) {
		for (ItemOrden itemOrden : itemsOrden) {
			itemOrden.setOrden(orden);
		}
	}

	///// refactorizar

	//// refatorizar (asignarItemACocinero)
	
	private Mesero asignarMesero() {
		User usuarioAsignado;
		usuarioAsignado = AuthSocketInterceptor.meserosActivos.get(0);
		usuarioAsignado.setNumeroTareas(usuarioAsignado.numeroTareas() + 1);
		for (User mesero : AuthSocketInterceptor.meserosActivos) {

			if (mesero.numeroTareas() < usuarioAsignado.numeroTareas()) {
				usuarioAsignado.setNumeroTareas(usuarioAsignado.numeroTareas() - 1);
				usuarioAsignado = mesero;
				usuarioAsignado.setNumeroTareas(usuarioAsignado.numeroTareas() + 1);
			}
		}
		Mesero mesero = null;
		mesero = meseroRepository.findById(Long.parseLong(usuarioAsignado.getId())).get();
		return mesero;
	}


	private BigDecimal calcularSubtotal(List<ItemOrden> itemsOrden) {
		
		BigDecimal subtotal = new BigDecimal(0);
		
		for(ItemOrden item: itemsOrden) {
			subtotal = subtotal.add(item.getSuma());
		}		
		return subtotal;
	}


	
}
