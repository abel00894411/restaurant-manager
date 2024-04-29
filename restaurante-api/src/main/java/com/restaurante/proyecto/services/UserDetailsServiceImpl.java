package com.restaurante.proyecto.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.restaurante.proyecto.models.entity.Administrador;
import com.restaurante.proyecto.models.entity.Cocinero;
import com.restaurante.proyecto.models.entity.Empleado;
import com.restaurante.proyecto.models.entity.Mesero;
import com.restaurante.proyecto.repositories.IAdministradorRepository;
import com.restaurante.proyecto.repositories.ICocineroRepository;
import com.restaurante.proyecto.repositories.IEmpleadoRepository;
import com.restaurante.proyecto.repositories.IMeseroRepository;

import jakarta.persistence.EntityNotFoundException;

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
		
		Empleado empleado = empleadoRepository.findByrfc(username).orElseThrow(  ()-> new EntityNotFoundException()  );
		
		System.out.println("Entro al loadByUsername");
		
		Optional<Administrador> administrador = null;
		Optional<Cocinero> cocinero= null;
		Optional<Mesero> mesero= null;
		
		
		administrador = administradorRepository.findById(empleado.getIdEmpleado());
		if(administrador.isPresent()) {
			return new User(username, empleado.getPassword(), true, true, true, true, createGrantedAuthorities("ADMINISTRADOR"));
		}
		mesero = meseroRepository.findById(empleado.getIdEmpleado());
		if(mesero.isPresent()) {
			return new User(username, empleado.getPassword(), true, true, true, true, createGrantedAuthorities("MESERO"));
		}
		cocinero = cocineroRepository.findById(empleado.getIdEmpleado());
		if(cocinero.isPresent()) {
			return new User(username, empleado.getPassword(), true, true, true, true, createGrantedAuthorities("COCINERO"));
		}
		
		return null;
	}
	
	
	
	public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
		
		Empleado empleado = empleadoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException());
		
		System.out.println("Entro al loadById");
		
		Optional<Administrador> administrador = null;
		Optional<Cocinero> cocinero= null;
		Optional<Mesero> mesero= null;
		
		
		administrador = administradorRepository.findById(empleado.getIdEmpleado());
		if(administrador.isPresent()) {
			return new User(empleado.getRfc(), empleado.getPassword(), true, true, true, true, createGrantedAuthorities("ADMINISTRADOR"));
		}
		mesero = meseroRepository.findById(empleado.getIdEmpleado());
		if(mesero.isPresent()) {
			return new User(empleado.getRfc(), empleado.getPassword(), true, true, true, true, createGrantedAuthorities("MESERO"));
		}
		cocinero = cocineroRepository.findById(empleado.getIdEmpleado());
		if(cocinero.isPresent()) {
			return new User(empleado.getRfc(), empleado.getPassword(), true, true, true, true, createGrantedAuthorities("COCINERO"));
		}
		
		return null;
	}
	
	private Collection<? extends GrantedAuthority> createGrantedAuthorities(String authority) {
		List<GrantedAuthority> lista = new ArrayList<GrantedAuthority>();
		lista.add(new SimpleGrantedAuthority(authority));
		lista.add(new SimpleGrantedAuthority("ROLE_"+authority));
		return lista;
	}

}
