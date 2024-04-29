package com.restaurante.proyecto.security.filters.strategiesRequest;

import java.util.List;
import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.restaurante.proyecto.models.entity.User;
import com.restaurante.proyecto.security.filters.AuthSocketInterceptor;

@Component
public class DisconnectStrategy implements IStrategy{

	@Override
	public boolean doSomething(List<User> empleados, Message<?> message) {
		String request = obtainRequest(message);
		System.out.println("\t\t\tDisconnect strategy");
		if(!this.resquetHandler(request, "DISCONNECT")) {
			return false;
		}
		String session = message.getHeaders().get("simpSessionId").toString();
//		User user  = null;
		System.out.println("session removida: "+session);
//		empleados.removeIf( x ->{
//			System.out.println(x.session()+"=="+session);
//			return x.session().equals(session);
//		});
		
		for(User empleado : empleados) {
			if(empleado.getSession().equals(session)) {
				System.out.println(empleado.session()+"=="+session);
				AuthSocketInterceptor.empleadosActivos.remove(empleado);
				if(AuthSocketInterceptor.administradoresActivos.contains(empleado)) {
					System.out.println("se quitó ADMINISTRADOR");
					AuthSocketInterceptor.administradoresActivos.remove(empleado);
					
				} else if(AuthSocketInterceptor.meserosActivos.contains(empleado)) {
					System.out.println("se quitó MESERO");
					AuthSocketInterceptor.meserosActivos.remove(empleado);
				}else if(AuthSocketInterceptor.cocinerosActivos.contains(empleado)) {
					System.out.println("se quitó COCINERO");
					AuthSocketInterceptor.cocinerosActivos.remove(empleado);
				}
				break;
			}
		}
		
		return true;
	}

	

}
