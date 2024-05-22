package com.restaurante.proyecto.controllers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import com.restaurante.proyecto.dtos.ItemOrdenActualizarRequest;
import com.restaurante.proyecto.dtos.ItemOrdenAgregadoResponse;
import com.restaurante.proyecto.dtos.ItemOrdenListado;
import com.restaurante.proyecto.dtos.ItemOrdenRequest;
import com.restaurante.proyecto.dtos.OrdenRequest;
import com.restaurante.proyecto.dtos.OrdenAsignadoResponse;
import com.restaurante.proyecto.dtos.OrdenListadoResponse;
import com.restaurante.proyecto.dtos.OrdenLlenarRequest;
import com.restaurante.proyecto.models.entity.ItemOrden;
import com.restaurante.proyecto.models.entity.Mesero;
import com.restaurante.proyecto.models.entity.Orden;
import com.restaurante.proyecto.repositories.IEmpleadoRepository;
import com.restaurante.proyecto.repositories.IItemOrdenRepository;
import com.restaurante.proyecto.repositories.IMeseroRepository;
import com.restaurante.proyecto.repositories.IOrdenRepository;
import com.restaurante.proyecto.security.filters.AuthSocketInterceptor;
import com.restaurante.proyecto.services.ItemOrdenService;
import com.restaurante.proyecto.services.JwtService;
import com.restaurante.proyecto.services.OrdenService;
import com.restaurante.proyecto.services.UsuarioService;
import com.restaurante.proyecto.utils.RoutesApp;
import com.restaurante.proyecto.utils.RoutesTopic;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.DELETE,
		RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH })
public class WebSocketController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private OrdenService ordenService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private ItemOrdenService itemOrdenService;
	@Autowired
	private IMeseroRepository meseroRepository;
	@Autowired
	private IOrdenRepository ordenRepository;
	@Autowired
	private IItemOrdenRepository itemOrdenRepository;



	// ELIMINAR, es de prueba  ChannelInterceptor
	@MessageMapping("/chat") // app/chat
	@SendTo("/topic/messages")
	public String prueba(String mensaje, Message<?> message) {
		System.out.println(message);
		sendToRoute("/topic/ordenes" + "/2", "otro mensaje");
		return mensaje;
	}


	@MessageMapping("/ordenes/nueva")
	public void nuevaOrden(OrdenRequest ordenRequest, Message<?> message) {
		Map<String, Object> ordenEItems = ordenService.crearOrden(ordenRequest);
		Orden orden = (Orden) ordenEItems.get("orden");
		List<ItemOrden> itemsOrden = (List<ItemOrden>) ordenEItems.get("items");
		Map<String, Object> ordenResponse = new HashMap<String, Object>();
		Map<String, Object> creadasResponse = new HashMap<String, Object>();
		String id = jwtService.extractIdUsuario( jwtService.obtainTokenFromNativeHeader(message) );
		enviarTrabajoCocineros(itemsOrden);
		ordenResponse.put("orden", new OrdenAsignadoResponse(orden, itemsOrden));
		sendToRoute(RoutesTopic.topicOrdenesAsignados.getPath() + "/" + orden.getMesero().getMeseroId(), ordenResponse);
		creadasResponse.put("idMesero", orden.getMesero().getMeseroId());
		creadasResponse.put("mesero", usuarioService.obtenerPorId( orden.getMesero().getMeseroId() ).get("nombre"));
		sendToRoute(RoutesTopic.topicOrdenesCreadas.getPath()+"/"+id, creadasResponse);
		System.out.println(message);
	}
	
	
	@MessageMapping("/ordenes/enlistar") // app/ordenes/enlistar
	public void enlistarOrdenes(Message<?> message) {
		String id = jwtService.extractIdUsuario( jwtService.obtainTokenFromNativeHeader(message) );
		Map<String, Object> response = new HashMap<String, Object>();
		List<Object> ordenesListadoResposne = ordenService.listadoDeOrdenes();
		response.put("ordenes", ordenesListadoResposne);
		sendToRoute(RoutesTopic.topixOrdenesListados.getPath()+"/"+id, response);	
	}
	
	
	@MessageMapping("/ordenes/llenar")
	public void llenar(OrdenLlenarRequest ordenLlenarRequest, Message<?> message) {
		String id = jwtService.extractIdUsuario( jwtService.obtainTokenFromNativeHeader(message) );

		Map<String, Object> resultado = ordenService.llenar(ordenLlenarRequest);
		List<ItemOrden> itemsOrden = (List<ItemOrden>)resultado.get("items");
		
		List<ItemOrdenAgregadoResponse> itemsOrdenAgregadoResponse = ItemOrdenAgregadoResponse.obtenerItems(itemsOrden);
		resultado.put("items",itemsOrdenAgregadoResponse );
		sendToRoute(RoutesTopic.topicItemsAgregados.getPath()+"/"+id, resultado);
		enviarTrabajoCocineros(itemsOrden);
		
	}
	
	
	
	@MessageMapping("/ordenes/terminar")
	public void terminarOrden(HashMap<String, Long> request, Message<?> message) {
		Long idOrden = request.get("idOrden");
		
		String id = jwtService.extractIdUsuario( jwtService.obtainTokenFromNativeHeader(message) );
		Orden resultado= ordenService.terminarOrden(idOrden);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("subtotal", resultado.getSubtotal());
		response.put("iva", resultado.getIva());
		response.put("total", resultado.getTotal());
		response.put("idOrden", resultado.getIdOrden());
		sendToRoute(RoutesTopic.topicOrdenesTerminadas.getPath()+"/"+id, response);
	}
	
	@MessageMapping("/items/enlistar")
	public void enlistarItems(Message<?> message) {
		String id = jwtService.extractIdUsuario( jwtService.obtainTokenFromNativeHeader(message) );
		
		
		
		List<ItemOrdenListado> resultados = itemOrdenService.listadoDeItems( Long.valueOf(id) );
		
		List<Object> res = resultados.stream().map(x->{
			
			ItemOrden itemOrden = itemOrdenRepository.findById(x.idItemOrden()).get();
			Orden orden = ordenRepository.findById(itemOrden.getOrden().getIdOrden()).get();
			
			HashMap<String, Object> u = new HashMap<String, Object>();
			
			u.put("idItemOrden", x.idItemOrden());
			u.put("idItemMenu", x.idItemMenu());
			u.put("cantidad", x.cantidad());
			u.put("estado", x.estado());
			u.put("fecha", orden.getFecha().format(  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") )    );
			return u;
			
		}).collect(Collectors.toList());
		
		
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("items", res);
		sendToRoute(RoutesTopic.topicItemsListados.getPath()+"/"+id, response);
	}
	
	 
	@MessageMapping("/items/actualizar")
	public void actualizarItem(ItemOrdenActualizarRequest itemOrdenActualizarRequest,Message<?> message) {
		String id = jwtService.extractIdUsuario( jwtService.obtainTokenFromNativeHeader(message) );
		ItemOrden resultado = itemOrdenService.actualizarItemOrden(itemOrdenActualizarRequest);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("idOrden", resultado.getOrden().getIdOrden());
		response.put("idItemOrden", resultado.getIdItemOrden());
		response.put("estado", resultado.getEstado());
		
		Mesero mesero = meseroRepository.encontrarPorIdOrden(resultado.getOrden().getIdOrden());
		
		
		sendToRoute(RoutesTopic.topicItemsActualizaciones.getPath()+"/"+resultado.getCocinero().getCocineroId(), response);
		sendToRoute(RoutesTopic.topicItemsActualizaciones.getPath()+"/"+mesero.getMeseroId(), response);
		
	}
	
	
	/////////
	

	private void enviarTrabajoCocineros(List<ItemOrden> itemsOrden) {
		for (ItemOrden item : itemsOrden) {
			Map<String, Object> itemsResponse = new HashMap<String, Object>();
			Map<String, Object> itemResponse = new HashMap<String, Object>();
			itemResponse.put("idItemOrden", item.getIdItemOrden());
			itemResponse.put("idItemMenu", item.getItemMenu().getIdItemMenu());
			itemResponse.put("cantidad", item.getCantidad());
			itemResponse.put("estado", item.getEstado());
			itemResponse.put("fecha", item.getOrden().getFecha().format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") )  );
			
			itemsResponse.put("items", itemResponse);

			sendToRoute(RoutesTopic.topicItemsAsignados.getPath() + "/"
					+ item.getCocinero().getIdCocinero().getIdEmpleado(), itemsResponse);
		}
	}
	
	
	
	
	
	
	
	/////////////////////////////
	
	private void sendToRoute(String destino, Object mensaje) {
		System.out.println("se envió un mensaje a:"+destino);
		try {
			messagingTemplate.convertAndSend(destino, mensaje);
		}catch (Exception e) {
			System.out.println("error al enviar mensaje: "+e.getMessage());
			throw new RuntimeException("Ha ocurrido un error al enviar el mensaje: "+e.getMessage());
		}

	}



}
