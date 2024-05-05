package com.restaurante.proyecto.controllers; 

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.proyecto.dtos.ItemOrdenListado;
import com.restaurante.proyecto.models.entity.Administrador;
import com.restaurante.proyecto.models.entity.Categoria;
import com.restaurante.proyecto.models.entity.Cocinero;
import com.restaurante.proyecto.models.entity.Empleado;
import com.restaurante.proyecto.models.entity.ItemOrden;
import com.restaurante.proyecto.models.entity.Orden;
import com.restaurante.proyecto.repositories.IAdministradorRepository;
import com.restaurante.proyecto.repositories.IEmpleadoRepository;
import com.restaurante.proyecto.repositories.IItemOrdenRepository;
import com.restaurante.proyecto.repositories.IMeseroRepository;
import com.restaurante.proyecto.repositories.IOrdenRepository;
import com.restaurante.proyecto.security.filters.AuthSocketInterceptor;

@RestController
public class PruebaController {

	@Autowired
	private IEmpleadoRepository empleadoRepository;
	@Autowired
	private IAdministradorRepository administradorRepository;
	@Autowired
	private IMeseroRepository meseroRepository;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private IOrdenRepository ordenRepository;
	@Autowired
	private IItemOrdenRepository itemOrdenRepository;
	
	
	
	@GetMapping("/prueba")
	public ResponseEntity<?> empleadosActivos() {
		
		Empleado def = empleadoRepository.findByrfc("DEFAULT").get();
		
//		List<Object[]> resultados = ordenRepository.pruebaHola();
//		Object[] r = resultados.get(0);
		System.out.println(ItemOrdenListado.crearListado(itemOrdenRepository.obtenerItemsPendientesActuales(Long.valueOf(6))));
		
		return ResponseEntity.ok(AuthSocketInterceptor.empleadosActivos);
	}
	
	@GetMapping("/prueba-empleados-activos")
	public ResponseEntity<?> empleadosActivosTipos() {
		Map<Object, Object> response = new HashMap<Object, Object>();
		response.put("administradores", AuthSocketInterceptor.administradoresActivos);
		response.put("meseros", AuthSocketInterceptor.meserosActivos);
		response.put("cocineros", AuthSocketInterceptor.cocinerosActivos);
		return ResponseEntity.ok(response);
	}
	
	
	
	
	@GetMapping("/obtenerPorId/{id}")
	public Object getMethodName(@PathVariable Long id) {
		return administradorRepository.getById(id).getIdAdministrador();
	}
	
	@GetMapping("/obtenerTodosPrueba")
	public Object obtenerTodosPrueba() {
		
		return empleadoRepository.encontrarTodos();
	}
	
	
	
}
