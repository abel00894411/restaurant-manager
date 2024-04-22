package com.restaurante.proyecto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.restaurante.proyecto.security.filters.AuthSocketInterceptor;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer{

	
	@Autowired
	private AuthSocketInterceptor authSocketInterceptor;
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
	
		registry.enableSimpleBroker("/topic");
		registry.setApplicationDestinationPrefixes("/app");
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/connect")
										.setAllowedOriginPatterns("null")
										//.addInterceptors(null)
										.withSockJS()
		;
	}
	
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		

		
//		registration.interceptors(new AuthSocketInterceptor());
		registration.interceptors(authSocketInterceptor);
		//		WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);
	}
	
	
}
