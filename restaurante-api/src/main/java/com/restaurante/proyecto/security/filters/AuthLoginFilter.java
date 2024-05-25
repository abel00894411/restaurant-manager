package com.restaurante.proyecto.security.filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante.proyecto.repositories.IEmpleadoRepository;
import com.restaurante.proyecto.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class AuthLoginFilter extends UsernamePasswordAuthenticationFilter{

	private JwtService jwtService;
	private IEmpleadoRepository empleadoRepository;
	private AuthenticationManager authenticationManager;
	private ObjectMapper objectMapper;
	 
	public AuthLoginFilter(ObjectMapper mapper,AuthenticationManager authenticationManager, IEmpleadoRepository empleadoRepository, JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.objectMapper = mapper;//new ObjectMapper();
		this.empleadoRepository = empleadoRepository;
		this.jwtService = jwtService;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		System.out.println("entró al attempAuthentication");
		JsonNode cuerpo = obtenerCuerpo(request);
		String username = obtenerUsuario(cuerpo);
		String password= obtenerPassword(cuerpo);
		System.out.println(username+"_"+password);
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		

		return authenticationManager.authenticate(authToken);
	}
	
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authResult);
		Map<String, Object> body = new HashMap<String, Object>();
		Map<String, Object> extraClaims = new HashMap<String, Object>();// cuerpo del token
		List<String> authority = authResult.getAuthorities().stream().// el 'tipo'
				map( x->x.getAuthority())
				.filter(a -> a.startsWith("ROLE_"))
				.map(a->a.replace("ROLE_", ""))
				.collect(Collectors.toList());
		

		Long idUsuario = empleadoRepository.findByrfc( authResult.getName() ).get().getIdEmpleado();

		
		extraClaims.put("idUsuario", idUsuario);
		extraClaims.put("tipo", authority.get(0));
		
		body.put("mensaje" , "Sesion iniciada con exito");
		body.put("idUsuario" , idUsuario);
		body.put("token", jwtService.generateToken(idUsuario, extraClaims));
		

		response.getWriter().write(objectMapper.writeValueAsString(body));
		response.setStatus(HttpStatus.OK.value());//se puede mandar solo el int 200, que es el codigo
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().flush(); //asegurarnos de que todo se escriba correctamente
		
		chain.doFilter(request, response);
	}
	
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		System.out.println("unsuccesfull ---");
		response.setStatus(401);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		super.unsuccessfulAuthentication(request, response, failed);
	}
	
	
	private String obtenerUsuario(JsonNode cuerpo) {
			String username = cuerpo.get("rfc").asText();
			if(username == null || username == "") {
				throw new BadCredentialsException("Credenciales de acceso incorrectas");
			}
			return username;
	}
	
	private String obtenerPassword(JsonNode cuerpo) {
		String password = cuerpo.get("password").asText();
		if(password == null || password == "") {
			throw new BadCredentialsException("Credenciales de acceso incorrectas");
		}
		return password;
	}
	
private JsonNode obtenerCuerpo(HttpServletRequest request) {
		
		try {
			JsonNode jsonNode = objectMapper.readTree(request.getInputStream());
			return jsonNode;
		}catch (Exception e) {
			System.out.println("error obteniendo Cuerpo");
			throw new RuntimeException("Error obteniendo el cuerpo");
		}
	}
	
	
}
