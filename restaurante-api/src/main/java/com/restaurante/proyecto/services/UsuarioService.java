package com.restaurante.proyecto.services;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurante.proyecto.dtos.EmpleadoResponse;
import com.restaurante.proyecto.dtos.UsuarioActualizar;
import com.restaurante.proyecto.dtos.UsuarioCrear;
import com.restaurante.proyecto.models.entity.Administrador;
import com.restaurante.proyecto.models.entity.Categoria;
import com.restaurante.proyecto.models.entity.Cocinero;
import com.restaurante.proyecto.models.entity.Empleado;
import com.restaurante.proyecto.models.entity.Mesero;
import com.restaurante.proyecto.repositories.IAdministradorRepository;
import com.restaurante.proyecto.repositories.ICategoriaRepository;
import com.restaurante.proyecto.repositories.ICocineroRepository;
import com.restaurante.proyecto.repositories.IEmpleadoRepository;
import com.restaurante.proyecto.repositories.IMeseroRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
	
	private final int longitudPassword = 5;
	private final String letras = "AbBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789"; 
	private Random random= new Random();
	
	@Autowired
	private IEmpleadoRepository empleadoRepository;
	@Autowired
	private IAdministradorRepository administradorRepository;
	@Autowired
	private IMeseroRepository meseroRepository;
	@Autowired
	private ICocineroRepository cocineroRepository;
	@Autowired
	private ICategoriaRepository categoriaRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
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
			
				Optional<Cocinero> cocinero = cocineroRepository.findById(Long.valueOf((String)obj[0]));
				
				if(cocinero.isPresent()) {
					respuesta.put("categorias", cocinero.get().getCategorias().stream().map(c->{
						return c.getIdCategoria();
					}).collect(Collectors.toList())  );
				}
			}

		}catch (Exception e) {
			System.out.println("Error - usuarioService-obtenerPorId: "+e.getMessage());
		}
		return respuesta;
	}
	
	
	public Map<String, Object> crearEmpleado( UsuarioCrear usuarioCrear) {

		if(!(usuarioCrear.rol().equals("ADMINISTRADOR") || usuarioCrear.rol().equals("MESERO") ||usuarioCrear.rol().equals("COCINERO"))) {
			throw new RuntimeException("No es un rol valido");
		}

		String password=generarCadenaAleatoria(longitudPassword);
		
		Empleado empleado = new Empleado();
		empleado.setNombre(usuarioCrear.nombre());
		empleado.setApellido(usuarioCrear.apellido());
		empleado.setRfc(usuarioCrear.rfc());
		empleado.setSueldo(usuarioCrear.sueldo());
		empleado.setPassword( passwordEncoder.encode(password) );
		
		
		empleado = empleadoRepository.save(empleado);
		if(usuarioCrear.rol().equals("COCINERO")) {
			
			Cocinero cocinero = new Cocinero();
			cocinero.setCategorias( crearCategorias(usuarioCrear.categorias() ) );
			cocinero.setIdCocinero(empleado);			

			cocineroRepository.save(cocinero);
			
		}else if( usuarioCrear.rol().equals("MESERO")  ){
			
			Mesero mesero = new Mesero();
			mesero.setIdMesero(empleado);
			meseroRepository.save(mesero);
			
		}else if( usuarioCrear.rol().equals("ADMINISTRADOR")  ){
			Administrador administrador = new Administrador();
			administrador.setIdAdministrador(empleado);
			administradorRepository.save(administrador);
		}
		
		Map<String, Object> resultado = new HashMap<String, Object>();
		resultado.put("password", password);
		resultado.put("idUsuario", empleado.getIdEmpleado());
		return resultado;
		
	}
	
	
	public void actualizarEmpleado(UsuarioActualizar actualizar,Long id) {
		Empleado empleado = empleadoRepository.findById(id).orElseThrow(()->new RuntimeException("Empleado no encontrado"));
		
		empleado.setNombre(actualizar.nombre());
		empleado.setApellido(actualizar.apellido());
		empleado.setRfc(actualizar.rfc());
		empleado.setSueldo(actualizar.sueldo());
		
		empleadoRepository.save(empleado);
		
		Cocinero cocinero = null;
		Optional<Cocinero> cocineroOptional = cocineroRepository.findById(empleado.getIdEmpleado());
		if(cocineroOptional.isPresent()) {
			List<Categoria> categorias = crearCategorias(actualizar.categorias());
			cocinero = cocineroOptional.get();
			cocinero.setCategorias(categorias);
			cocineroRepository.save(cocinero);
			
			cocineroRepository.save(cocinero);

		}
		
		
	}
	
	
	
	public void eliminarEmpleado(Long id) {
		empleadoRepository.deleteById(id);
	}
	
	
	
	
	
	
	public void cambiarPassword(String password, Long id) {
		Empleado empleado =empleadoRepository.findById(id).orElseThrow(()->new RuntimeException("No existe ese usuario"));
		empleado.setPassword( passwordEncoder.encode(password) );
	}
	
	
	
	private List<Categoria> crearCategorias(List<String> categorias){
		
		if(categorias.isEmpty()){
			throw new RuntimeException("El cocinero debe de tener especialidad");
		}
		
		return categorias.stream().map(cat->{
			return categoriaRepository.findBycategoria(cat).orElseThrow( ()-> new RuntimeException(" No existe esa categoria ") );
		}).collect(Collectors.toList());
	}
	
	
	public String generarCadenaAleatoria(int longitud) {
		String a="";
		
		for(int i=0; i<=longitud; i++) {
			
			int index = random.nextInt(this.letras.length());
			a = a+( this.letras.charAt(index) );
		}
		return a;
	}
	
}
