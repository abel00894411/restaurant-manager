package com.restaurante.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import com.restaurante.proyecto.dtos.ItemOrdenRequest;
import com.restaurante.proyecto.dtos.OrdenRequest;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH})
public class WebSocketController {

	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	//ELIMINAR, es de prueba
	@MessageMapping("/chat")// app/chat
	@SendTo("/topic/messages")
	public String prueba(String message) {
		System.out.println("se envió le mensaje "+message);
		enviarA("/topic/ordenes"+"/2", "otro mensaje");
		return message;
	}
	
	
	
	@SubscribeMapping("/topic/ordenes/listadas")
	public Object suscribirAordenesListadas() {
		
		return null;
	}
	
	@MessageMapping("/ordenes/nueva")
	@SendTo("/topic/ordenes/creadas")
	public String mostrarTodasLasOrdenes(OrdenRequest items) {
		
		System.out.println(items);
		enviarA("/topic/ordenes"+"/2", "Simon jala para el replicado");
		return "simon, jaló";
	}
	
	
	
	private void enviarA(String destino, Object mensaje) {
		messagingTemplate.convertAndSend(destino, mensaje);
	}
	
}
