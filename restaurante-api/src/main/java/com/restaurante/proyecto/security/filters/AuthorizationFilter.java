package com.restaurante.proyecto.security.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante.proyecto.repositories.IEmpleadoRepository;
import com.restaurante.proyecto.services.JwtService;
import com.restaurante.proyecto.services.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class AuthorizationFilter extends OncePerRequestFilter{

	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private IEmpleadoRepository empleadoRepository;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestUri = request.getRequestURI();
		
		System.out.println(requestUri);

		if( ! (   requestUri.startsWith("/connect") ||  requestUri.startsWith("/topic")   ) ) {
			
		
			String authHeader = request.getHeader("Authorization")==null? "":request.getHeader("Authorization");
			System.out.println("Ingresa al filtro");
			if(authHeader == "" || !authHeader.startsWith("Bearer ")) {
				
				filterChain.doFilter(request, response);
				return;
			}
			
			
			
			String jwt = authHeader.split(" ")[1];
			Long id = Long.parseLong(jwtService.extractIdUsuario(jwt));
			UserDetails user = userDetailsService.loadUserById(id);
			UsernamePasswordAuthenticationToken authToken =  new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authToken);
			
		}
		filterChain.doFilter(request, response);

		
	}
	
	
}
