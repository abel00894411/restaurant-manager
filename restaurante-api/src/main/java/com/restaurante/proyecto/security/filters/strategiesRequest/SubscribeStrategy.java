package com.restaurante.proyecto.security.filters.strategiesRequest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.restaurante.proyecto.models.entity.User;
import com.restaurante.proyecto.services.JwtService;

@Component
public class SubscribeStrategy implements IStrategy{

	@Autowired
	private JwtService jwtService;
	
	@Override
	public boolean doSomething(List<User> empleados, Message<?> message) {
		String request = obtainRequest(message);
		System.out.println("\t\t\tSubscribe strategy");
		if(!this.resquetHandler(request, "SUBSCRIBE")) {
			return false;
		}
		
		String route = obtainNativeHeader(message, "destination").get(0);
		String token = this.obtainToken(message);
		String id = jwtService.extractIdUsuario(token); //arroja excepcion si no es valido el token
		String role = jwtService.extractRole( token );

		if(! empleados.contains( new User(token, null, id, 0)  ) ) {
			System.out.println("entro al if de send strategy");
			throw new RuntimeException("Remitente desconocido");
		}
		
		System.out.println(role);
		if(! isChannelSubscriptionAllowed(route, role, id)) {
			System.out.println("no tiene permiso suficiente para suscribirse");
			throw new RuntimeException("No tiene el permiso suficiente");
		}
		
		return true;
	}

	
	
}
