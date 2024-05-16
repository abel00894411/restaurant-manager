package com.restaurante.proyecto.security.filters.strategiesRequest;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.restaurante.proyecto.models.entity.User;
import com.restaurante.proyecto.services.JwtService;
import com.restaurante.proyecto.utils.RoutesApp;
import com.restaurante.proyecto.utils.RoutesTopic;

@Component
public interface IStrategy {


	boolean doSomething(List<User> empleados, Message<?> message);

	default boolean resquetHandler(String request, String handler) {
		return request.equals(handler);
	};

	default String obtainRequest(Message<?> message) {
		return message.getHeaders().get("stompCommand").toString();
	}

	default String obtainToken(Message<?> message) {
		Map<String, List<String>> nativeHeaders = (Map<String, List<String>>) message.getHeaders().get("nativeHeaders");
		List<String> authorization = nativeHeaders.get("Authorization");
		if (authorization == null) {
			throw new RuntimeException("No contiene header authorization"); // o seria mejor lanzar excepcion ?
		}

		if (authorization.size() < 0) {
			throw new RuntimeException("Authorization vacio");
		}
		return authorization.get(0).replace("Bearer ", "");
	}

	default List<String> obtainNativeHeader(Message<?> message, String header) {
		Map<String, List<String>> nativeHeaders = (Map<String, List<String>>) message.getHeaders().get("nativeHeaders");
		List<String> nativeHeader = nativeHeaders.get(header);

		if (nativeHeader.size() < 0) {
			System.out.println("Native header no encontrado Istrategy");
			throw new RuntimeException("Native header no encontrado");
		}

		return nativeHeader;

	}

	default boolean isRouteMessageAllowed(String route, String role) {
		for (RoutesApp r : RoutesApp.values()) {
			if (r.getPath().equals(route)) {
				List<String> permitidos = r.getRoles();
				return permitidos.contains(role);
			}
		}
		return false;
	}

	default boolean isChannelSubscriptionAllowed(String routeOriginal, String role, String id) {
		String route = formatear(routeOriginal);
		
		System.out.println("\tid: "+id);
		
		for (RoutesTopic r : RoutesTopic.values()) {
			System.out.println(route + "  ===  " + r.getPath());
			if (r.getPath().equals(route)) {
				List<String> permitidos = r.getRoles();
				System.out.println(route + "  ===  " + permitidos);
				return permitidos.contains(role) && permitIdSuscribe(id, routeOriginal);
			}
		}
		return false;

	}

	private String formatear(String route) {

		String[] dividido = route.split("/");
		if (dividido[dividido.length - 1].matches("^[1-9]\\d*$")) {
			route = "";
			for (int i = 0; i < dividido.length; i++) {
				if (!(i == dividido.length - 1) && !dividido[i].isBlank()) {
					route = route.concat("/").concat(dividido[i]);
				}
			}
		}
		return route;
	}
	
	
	private boolean permitIdSuscribe(String id, String route) {

		String[] dividido = route.split("/");
		for (int i = 0; i < dividido.length; i++) {
			System.out.println(dividido[i]);
		}
		
		System.out.println(dividido[dividido.length - 1]);
		try {
			Integer idPassed = Integer.parseInt(dividido[dividido.length - 1]);			
			return id.equals(idPassed.toString());
		} catch (Exception e) {
			return true;
		}

	}

}
