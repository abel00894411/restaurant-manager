package com.restaurante.proyecto.security.filters.strategiesRequest;

import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.restaurante.proyecto.services.JwtService;
import com.restaurante.proyecto.utils.RoutesApp;
import com.restaurante.proyecto.utils.RoutesTopic;

@Component
public interface IStrategy {
	/*
	 
	 agregar - lista
	 
	 	-verificar token
	 	
	 eliminar - lista
	 	
	 manejar mensaje - lista
	 	
	 	-obtiene sesion y verifica
	 */
	
	boolean doSomething(Map<String, String> empleados,Message<?> message);
	
	
	default boolean resquetHandler(String request, String handler) {
		return request.equals(handler);
	};
	
	
	default String obtainRequest(Message<?> message) {
		return message.getHeaders().get("stompCommand").toString();
	}
	
	
	default String obtainToken(Message<?> message) {
		Map<String, List<String>> nativeHeaders = (Map<String, List<String>>) message.getHeaders().get("nativeHeaders");
		List<String> authorization = nativeHeaders.get("Authorization");
		if( authorization == null) {
			throw new RuntimeException("No contiene header authorization"); //o seria mejor lanzar excepcion ?
		}
		
		if(authorization.size() < 0 ) {
			throw new RuntimeException("Authorization vacio");
		}
		return authorization.get(0).replace("Bearer ","");
	}
	
	default List<String> obtainNativeHeader(Message<?> message, String header) {
		Map<String, List<String>> nativeHeaders = (Map<String, List<String>>) message.getHeaders().get("nativeHeaders");
		List<String> nativeHeader = nativeHeaders.get(header);
		
		if(nativeHeader.size()<0) {
			System.out.println("Native header no encontrado Istrategy");
			throw new RuntimeException("Native header no encontrado");
		}
		
		return nativeHeader;

	}
	
	default boolean isRouteMessageAllowed(String route, String role) {
		try {
			 for(RoutesApp r : RoutesApp.values()) {
				 if(r.getPath().equals(route)) {
					 List<String> permitidos = r.getRoles();
					 return permitidos.contains(role);
				 }
			 }
			 return false;
		}catch (Exception e) {
			System.out.println("ruta desconocida en isRouteMessageAllowed: "+e.getMessage());
			throw new RuntimeException("La ruta destino no existe: "+e.getMessage());
		}
	}
	
	default boolean isChannelSubscriptionAllowed(String route, String role) {
		try {
			
			if( route.contains("/asignados/") ) {
				route = route.substring(0, route.indexOf("/asignados/") + 10 );
			}
			 for(RoutesTopic r : RoutesTopic.values()) {
				 System.out.println(route+"  ===  "+r.getPath());
				 if(r.getPath().equals(route)) {
					 List<String> permitidos = r.getRoles();
					 System.out.println(route+"  ===  "+permitidos);
					 return permitidos.contains(role);
				 }
			 }
			 return false;
		}catch (Exception e) {
			System.out.println("ruta desconocida en isChannelSubscriptionAllowed: "+e.getMessage());
			throw new RuntimeException("La ruta destino no existe: "+e.getMessage());
		}
	}
	
}
