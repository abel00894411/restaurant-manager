package com.restaurante.proyecto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante.proyecto.repositories.IEmpleadoRepository;
import com.restaurante.proyecto.security.filters.AuthLoginFilter;
import com.restaurante.proyecto.security.filters.AuthorizationFilter;
import com.restaurante.proyecto.services.JwtService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = false)
public class SecurityConfiguration {
	
	
	@Autowired
	private AuthenticationProvider provider; //devuelve la instancia del DAO authentication provider
	@Autowired
	private IEmpleadoRepository empleadoRepository;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private AuthorizationFilter authFilter;
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager)throws Exception{
		
		return http
				.csrf( config -> config.disable() )
				.sessionManagement(   sessionManagementConfig-> sessionManagementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)   )
				.authenticationProvider(provider)
				.addFilterAt(new AuthLoginFilter( mapper,authenticationManager,empleadoRepository, jwtService) , UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(authFilter, AuthLoginFilter.class)
				.authorizeHttpRequests( request->{
					request
						.requestMatchers("/login").permitAll()
						.requestMatchers("/prueba").permitAll()
						.requestMatchers("/obtenerTodosPrueba").permitAll()
						.requestMatchers("/app/**").permitAll()
						.requestMatchers("/topic/**").permitAll()
						.requestMatchers("/connect/**").permitAll()
						.anyRequest().authenticated()
//						.anyRequest().permitAll()
					
					;
				})
				.cors(  cor -> cor.disable() )
				.build();
	}

}
