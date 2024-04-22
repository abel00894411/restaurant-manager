package com.restaurante.proyecto.services;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.proyecto.dtos.EmpleadoResponse;
import com.restaurante.proyecto.models.entity.Empleado;
import com.restaurante.proyecto.repositories.IAdministradorRepository;
import com.restaurante.proyecto.repositories.ICocineroRepository;
import com.restaurante.proyecto.repositories.IEmpleadoRepository;
import com.restaurante.proyecto.repositories.IMeseroRepository;

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
	
	
	@Transactional 
	public List<EmpleadoResponse> obtenerUsuarios(){
		
		List<EmpleadoResponse> empleados = empleadoRepository.encontrarTodos();
		
		return empleados;
		
	}
	
	
	public Map<String, Object>  obtenerPorId(Long id) {
		Map<String, Object> respuesta =  new HashMap<String, Object>();
		
		List<Object[]> resultado = null;

		try {
			resultado = empleadoRepository.encontrarPorId(id);
			
			for(Object[] obj : resultado) {
				respuesta.put("id", obj[0]);
				respuesta.put("nombre", obj[1]);
				respuesta.put("apellido", obj[2]);
				respuesta.put("puesto", obj[3]);
				respuesta.put("rfc", obj[4]);
				respuesta.put("sueldo", obj[5]);
				respuesta.put("fechaContratacion", 
						((Date)  obj[6]).toInstant()
						.atZone(ZoneId.systemDefault()   )
						.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))   .toString()  );
			}

		}catch (Exception e) {
			System.out.println("Error - usuarioService-obtenerPorId: "+e.getMessage());
		}
		return respuesta;
	}
	
	
}
