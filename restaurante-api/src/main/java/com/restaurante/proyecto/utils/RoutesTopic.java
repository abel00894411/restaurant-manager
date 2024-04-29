package com.restaurante.proyecto.utils;

import java.util.Arrays;
import java.util.List;

public enum RoutesTopic {

	topicOrdenesCreadas("/topic/ordenes/creadas",Arrays.asList("MESERO")),
	topicOrdenesAsignados("/topic/ordenes/asignados",Arrays.asList("MESERO")),
	topixOrdenesListados("/topic/ordenes/listados",Arrays.asList("MESERO")),
	topicOrdenesTerminadas("/topic/ordenes/terminadas",Arrays.asList("MESERO")),
	topicItemsListados("/topic/items/listados",Arrays.asList("COCINERO")),
	topicItemsAgregados("/topic/items/agregados",Arrays.asList("MESERO")),
	topicItemsAsignados("/topic/items/asignados",Arrays.asList("COCINERO")),
	topicItemsActualizaciones("/topic/items/actualizaciones",Arrays.asList("MESERO","COCINERO")) // igual duda de su razon de existir
	
	;
	
	private final String path;
    private final List<String> roles;
    
	RoutesTopic(String path, List<String> roles) {
		this.path = path;
        this.roles = roles;
	}
	
	public List<String> getRoles() {
        return roles;
    }
	
	public String getPath() {
        return path;
    }
}
