package com.restaurante.proyecto.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.restaurante.proyecto.errors.UnauthorizedException;

@ControllerAdvice
public class ControllerAdvices {

	@ExceptionHandler(value = {org.springframework.security.authentication.InternalAuthenticationServiceException.class,InternalAuthenticationServiceException.class,jakarta.persistence.EntityNotFoundException.class, org.springframework.security.authentication.InternalAuthenticationServiceException.class})
	public ResponseEntity<?> usuarioNoEncontradoHnadler(Exception e) {
		System.out.println("entro al controller advice");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(obtenerRespuesta(e));
    }
	
	@ExceptionHandler(value=BadCredentialsException.class)
	public ResponseEntity<?> credencialesIncorrectasHandler(Exception e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(this.obtenerRespuesta(e));
	}
	
	
	
	@ExceptionHandler(value = { UnauthorizedException.class })
	public ResponseEntity<?> accessDeniedHandler(Exception e, WebRequest request){
		System.out.println("simon entro al hanlder");
		return null;
	}
	
	
	
	
	private Map<String,String> obtenerRespuesta(Exception e) {
		Map<String, String> respuesta = new HashMap<String, String>();
		respuesta.put("mensaje", e.getMessage() );
		return respuesta;
	}
	
	
}
