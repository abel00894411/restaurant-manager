package com.restaurante.proyecto.security.filters.strategiesRequest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.restaurante.proyecto.services.JwtService;

@Component
public class ConnectStrategy implements IStrategy{

	@Autowired
	private JwtService jwtService;
	
	@Override
	public boolean doSomething(Map<String, String> empleados, Message<?> message) {
		String request = obtainRequest(message);
		
		System.out.println("\t\t\tConnect strategy");
		if(!this.resquetHandler(request, "CONNECT")) {
			return false;
		}
		
		String token = obtainToken(message);
		if(!jwtService.isTokenValid(token)) {
			System.out.println("entro al token invalido connect strategy");
			throw new RuntimeException("Token no valido -- connectStrategy");
		}
		String id = jwtService.extractIdUsuario(token);

		String session = message.getHeaders().get("simpSessionId").toString();
		empleados.put(id, session);
		System.out.println("sesion agregada: "+session);
		return true;
	}

	
//	private boolean isUserActive(String id, Map<String, String> empleados) {
//		return empleados.containsKey(id);
//	}
}
