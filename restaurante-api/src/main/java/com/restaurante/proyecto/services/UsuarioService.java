package com.restaurante.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.proyecto.dtos.EmpleadoResponse;
import com.restaurante.proyecto.models.entity.Empleado;
import com.restaurante.proyecto.repositories.IAdministradorRepository;
import com.restaurante.proyecto.repositories.ICocineroRepository;
import com.restaurante.proyecto.repositories.IEmpleadoRepository;
import com.restaurante.proyecto.repositories.IMeseroRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

	@Autowired
	private IEmpleadoRepository empleadoRepository;
	@Autowired
	private IAdministradorRepository administradorRepository;
	@Autowired
	private IMeseroRepository meseroRepository;
	@Autowired
	private ICocineroRepository cocineroRepository;
	
	
	@Transactional // tengo entendido que esto es como en base de datos, se ejecuta como una sola transaccion sin importar que tanto hagamos jsjs
						// pero ya se lo puse arruba del nombre de la clase, lo que aplicará a todos los metodos, nomas para que veas 
	public List<Empleado> obtenerUsuarios(){
		
		return empleadoRepository.findAll();
		
	}
	
	
	public List<Object[]> obtenerPorId(Long id) {
		
		List<Object[]> respuesta = null;

		try {
			respuesta = empleadoRepository.encontrarPorId(id);

		}catch (Exception e) {
			System.out.println("Error - usuarioService-obtenerPorId: "+e.getMessage());
		}
		return respuesta;
	}
	
	
}
