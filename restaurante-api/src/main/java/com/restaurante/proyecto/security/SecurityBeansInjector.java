package com.restaurante.proyecto.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante.proyecto.security.filters.strategiesRequest.ConnectStrategy;
import com.restaurante.proyecto.security.filters.strategiesRequest.DisconnectStrategy;
import com.restaurante.proyecto.security.filters.strategiesRequest.IStrategy;
import com.restaurante.proyecto.security.filters.strategiesRequest.SendStrategy;
import com.restaurante.proyecto.security.filters.strategiesRequest.SubscribeStrategy;
import com.restaurante.proyecto.services.UserDetailsServiceImpl;

@Configuration
public class SecurityBeansInjector {

	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		System.out.println("se creo bean password encoder");
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	

}
