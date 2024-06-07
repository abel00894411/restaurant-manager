package com.restaurante.proyecto;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


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
	private AuthenticationProvider provider; // devuelve la instancia del DAO authentication provider
	@Autowired
	private IEmpleadoRepository empleadoRepository;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private AuthorizationFilter authFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager)
			throws Exception {

		return http.csrf(config -> config.disable())
				.sessionManagement(sessionManagementConfig -> sessionManagementConfig
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(provider)
				.addFilterAt(new AuthLoginFilter(mapper, authenticationManager, empleadoRepository, jwtService),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(authFilter, AuthLoginFilter.class).authorizeHttpRequests(request -> {
					request.requestMatchers("/login").permitAll()
							.requestMatchers("/prueba").permitAll()
							.requestMatchers("/usuarios/activos").hasAnyRole("ADMINISTRADOR", "MESERO", "COCINERO")
							.requestMatchers("/app/**").permitAll()
							.requestMatchers("/topic/**").permitAll()
							.requestMatchers("/connect/**").permitAll()
							.requestMatchers("/prueba-empleados-activos").permitAll()
							.requestMatchers("/usuarios/**").hasAuthority("ADMINISTRADOR")
							.requestMatchers(HttpMethod.GET, "/usuario/{id:\\d+}").hasAnyRole("ADMINISTRADOR", "MESERO", "COCINERO")
							.requestMatchers(HttpMethod.PUT, "/usuario/{id:\\d+}").hasRole("ADMINISTRADOR")
							.requestMatchers(HttpMethod.PATCH, "/usuario/{id:\\d+}").hasAnyRole("ADMINISTRADOR", "MESERO", "COCINERO")
							.requestMatchers(HttpMethod.DELETE, "/usuario/{id:\\d+}").hasRole("ADMINISTRADOR")
							.requestMatchers(HttpMethod.GET, "/menu").permitAll()
							.requestMatchers("/menu/**").hasRole("ADMINISTRADOR")
							.requestMatchers(HttpMethod.GET,"/categorias").permitAll()
							.requestMatchers(HttpMethod.POST,"/categorias").hasRole("ADMINISTRADOR")
							.requestMatchers("/categoria/**").hasRole("ADMINISTRADOR")
							.requestMatchers("/ordenes/**").hasRole("ADMINISTRADOR")
							.requestMatchers("/orden/**").hasRole("ADMINISTRADOR")
							.requestMatchers("/facturas").permitAll()
							.requestMatchers(HttpMethod.DELETE, "/factura/**").hasRole("ADMINISTRADOR")
							.anyRequest().authenticated()
//						.anyRequest().permitAll()

					;
				})
//				.cors(cor -> cor.disable())
				.csrf(c -> c.disable())
				.build();
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*"); // Allow all origins
		config.addAllowedHeader("*"); // Allow all headers
		config.addAllowedMethod("*"); // Allow all methods
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}
