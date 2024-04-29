package com.restaurante.proyecto.services;

import java.security.Key;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final Long EXPIRATION = 24L;
	private final String key = "firma secreta restaurante analisis";
	
	

	public String generateToken(Long idUsuario, Map<String, Object> extraClaims) {

		Date issuedAt = new Date(System.currentTimeMillis());
		Date expiration = new Date(issuedAt.getTime() + ( EXPIRATION*60 * 60 * 1000)); // hora actual m24 horas
		
		
		//si no agregamos los claims primero se corre el riesgo de sobreescribir el resto de claims que se vayan a setear mas adelante. quien sabe por que
		return Jwts.builder()
							.setClaims(extraClaims)
							.setSubject(idUsuario.toString())            //clames por defecto u obligatorio
							.setIssuedAt(issuedAt)
							.setExpiration(expiration)
							.setHeaderParam(Header.TYPE , Header.JWT_TYPE)
							.signWith( generateKey()  , SignatureAlgorithm.HS256 )
							.compact()
							;
							
		
	}
	
	
//Key es una clase de java que devuelve la clave no como string sino como obejto y esta encriptado 
	private Key generateKey() {
		return Keys.hmacShaKeyFor(key.getBytes());
	}
	
	public String extractIdUsuario(String jwt) {
		return Jwts.parserBuilder()
				.setSigningKey(generateKey())
				.build()
				.parseClaimsJws(jwt)
				.getBody()
				.getSubject();//obtiene el campo sub del jwt
				
	}
	
	public String extractRole(String jwt) {
		return Jwts.parserBuilder()
				.setSigningKey(generateKey())
				.build()
				.parseClaimsJws(jwt)
				.getBody()
				.get("tipo")
				.toString();
	}
	
	
	public boolean isTokenValid(String jwt) {
		try {
			
			Jws<Claims> claimJws = Jwts.parserBuilder()
										.setSigningKey(generateKey())
										.build()
										.parseClaimsJws(jwt);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	
	
	public String obtainTokenFromNativeHeader(Message<?> message) {
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

}
