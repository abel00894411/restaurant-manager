package com.restaurante.proyecto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.restaurante.proyecto.repositories.IAdministradorRepository;
import com.restaurante.proyecto.repositories.ICocineroRepository;
import com.restaurante.proyecto.repositories.IEmpleadoRepository;
import com.restaurante.proyecto.repositories.IMeseroRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private IEmpleadoRepository empleadoRepository;
	@Autowired
	private IAdministradorRepository administradorRepository;
	@Autowired
	private ICocineroRepository cocineroRepository;
	@Autowired
	private IMeseroRepository meseroRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
