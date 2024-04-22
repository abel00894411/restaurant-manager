package com.restaurante.proyecto.security.filters.strategiesRequest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.restaurante.proyecto.services.JwtService;
import com.restaurante.proyecto.utils.RoutesApp;

@Component
public class SendStrategy implements IStrategy{
	
	@Autowired
	private JwtService jwtService;

	@Override
	public boolean doSomething(Map<String, String> empleados, Message<?> message) {
		String request = obtainRequest(message);
		System.out.println("\t\t\tSend strategy");
		if(!this.resquetHandler(request, "SEND")) {
			return false;
		}
		String route = obtainNativeHeader(message, "destination").get(0);
		String token = this.obtainToken(message);
		String id = jwtService.extractIdUsuario(token); //arroja excepcion si no es valido el token
		String role = jwtService.extractRole( token );

		if(! empleados.containsKey(id) ) {
			System.out.println("entro al if de send strategy");
			throw new RuntimeException("Remitente desconocido");
		}
		System.out.println(role);
		if(! isRouteMessageAllowed(route, role)) {
			System.out.println("no tiene permiso suficiente para mandar mensajes a este endpoint");
			throw new RuntimeException("No tiene el permiso suficiente para mandar mensajes a este endpoint");
		}
		
		return true;
	}
	
	
	



}
