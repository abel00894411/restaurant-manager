package com.restaurante.proyecto.security.filters.strategiesRequest;

import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class DisconnectStrategy implements IStrategy{

	@Override
	public boolean doSomething(Map<String, String> empleados, Message<?> message) {
		String request = obtainRequest(message);
		System.out.println("\t\t\tDisconnect strategy");
		if(!this.resquetHandler(request, "DISCONNECT")) {
			return false;
		}
		String session = message.getHeaders().get("simpSessionId").toString();
		System.out.println("session removida: "+session);
		empleados.remove(session);
		
		return true;
	}

	

}
