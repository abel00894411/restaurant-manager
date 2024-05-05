package com.restaurante.proyecto.security.filters.strategiesRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.restaurante.proyecto.models.entity.Categoria;
import com.restaurante.proyecto.models.entity.User;
import com.restaurante.proyecto.repositories.ICocineroRepository;
import com.restaurante.proyecto.security.filters.AuthSocketInterceptor;
import com.restaurante.proyecto.services.JwtService;

@Component
public class ConnectStrategy implements IStrategy{

	@Autowired
	private JwtService jwtService;
	@Autowired 
	private ICocineroRepository cocineroRepository;
	
	@Override
	public boolean doSomething(List<User> empleados, Message<?> message) {
		String request = obtainRequest(message);
		
		System.out.println("\t\t\tConnect strategy");		
		if(!this.resquetHandler(request, "CONNECT")) {
			return false;
		}
		
		String token = obtainToken(message);
		if(!jwtService.isTokenValid(token)) {
			System.out.println("entro al token invalido connect strategy");
			throw new RuntimeException("Token no valido -- connectStrategy");
		}
		String id = jwtService.extractIdUsuario(token);
		String role = jwtService.extractRole( token );

		for(User u : empleados) {
			if(u.getId().equals(id)) {
				System.out.println("entró a lo de dos usuarios iguales");
				AuthSocketInterceptor.administradoresActivos.remove(u);
				AuthSocketInterceptor.meserosActivos.remove(u);
				AuthSocketInterceptor.cocinerosActivos.remove(u);
				empleados.remove(u);
			}
		}
		
		String session = message.getHeaders().get("simpSessionId").toString();
		User empleado = new User(token, session, id, 0);
		empleados.add(empleado);
		if(role.equals("ADMINISTRADOR")) {
			AuthSocketInterceptor.administradoresActivos.add(empleado);
		}else if(role.equals("MESERO")) {
			AuthSocketInterceptor.meserosActivos.add(empleado);
		}else if(role.equals("COCINERO")) {
			
			List<Categoria> categorias = cocineroRepository.findById(Long.parseLong(id)).get().getCategorias();
			System.out.println("agregadno categoria al cocinero");
			for (Categoria categoria : categorias) {
				System.out.println("---\n"+categoria.getIdCategoria());
				System.out.println(categoria.getCategoria()+"\n---");
			}
			empleado.setEspecialidad( categorias );
			AuthSocketInterceptor.cocinerosActivos.add(empleado);
		}
		
		System.out.println("sesion agregada: "+session);
		return true;
	}


}
