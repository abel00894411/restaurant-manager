package com.restaurante.proyecto.security.filters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.restaurante.proyecto.models.entity.User;
import com.restaurante.proyecto.security.filters.strategiesRequest.IStrategy;
import com.restaurante.proyecto.services.JwtService;


@Component
public class AuthSocketInterceptor implements ChannelInterceptor{

	private static final String BEARER_PREFIX = "Bearer ";
	public static final List<User> empleadosActivos = new ArrayList<User>();
	public static final List<User> meserosActivos = new ArrayList<User>(); 
	public static final List<User> cocinerosActivos = new ArrayList<User>();
	public static final List<User> administradoresActivos = new ArrayList<User>();
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private List<IStrategy> strategies;
	
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		System.out.println("entro en el interceptor");
		
		String session = message.getHeaders().get("simpSessionId").toString();//validar si no existe
		System.out.println(session);
		//obtener headers y verificar que tipo de mensaje es (connect,disconnect, send, suscribe, etc)
		String solicitud = message.getHeaders().get("stompCommand").toString();
		System.out.println("solicitud de tipo: "+solicitud);
		System.out.println(message.getHeaders());
		

		if( !manejar(message) ) {
			System.out.println("no se pudo manejar la solicitud");
			throw new RuntimeException("No se pudo manejar el request -- AuthSocketInterceptor... if !manejar");
		}


		int i = 0;
		for(User emp : empleadosActivos) {
			System.out.println("id empleado["+i+"}: "+emp);
			i++;
		}
		System.out.println(message);
		return ChannelInterceptor.super.preSend(message, channel);
	}
	
	private boolean manejar(Message<?> message) {
		
		boolean band =false;
		int i = 1;
		System.out.println("\n\n");
		for(IStrategy strategy: this.strategies) {
			
			if(!band) {
				band = strategy.doSomething(empleadosActivos, message);
				System.out.println("i:"+(i-1)+" --"+band+"\n\n");
			}else {
				System.out.println("i:"+(i-1)+" --"+band+"\n\n");
				return band;
			}
			i++;
		}
		return band;
		
	}
	
	 
	
}
