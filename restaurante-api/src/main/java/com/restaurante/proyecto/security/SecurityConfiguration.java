package com.restaurante.proyecto.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = false)
public class SecurityConfiguration {
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http/*, AuthenticationManager authenticationManager*/)throws Exception{
		
		return http
				.csrf( config -> config.disable() )
				.sessionManagement(   sessionManagementConfig-> sessionManagementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)   )
//				.authenticationProvider(provider)
//				.addFilterAt(new springsocketprueba.security.AuthenticatonFilter(authenticationManager, authenticationService), UsernamePasswordAuthenticationFilter.class)
//				.addFilterAfter(authorizationFilter, AuthenticatonFilter.class)
				.authorizeHttpRequests( request->{
					request
						.requestMatchers("/login").permitAll()
//						.anyRequest().authenticated()
						.anyRequest().permitAll()
					
					;
				})
//				.cors(  cor -> cor.disable() )
				.build();
	}

}
