package com.restaurante.proyecto.utils;

import java.util.Arrays;
import java.util.List;

public enum RoutesApp {

	
	appOrdenesNueva("/app/ordenes/nueva",Arrays.asList("MESERO")),
	appOrdenesEnlistar("/app/ordenes/enlistar",Arrays.asList("MESERO")), //pendiente de disicutir
	appOrdenesLlenar("/app/ordenes/llenar",Arrays.asList("MESERO")), // duda
	appOrdenesTerminar("/app/ordenes/terminar",Arrays.asList("MESERO")),
	appItemsEnlistar("/app/items/enlistar",Arrays.asList("MESERO")),     // pendiente de discutir
	appItemsActualizar("/app/items/actualizar",Arrays.asList("MESERO","COCINERO")),
	
	;
	
	private final String path;
    private final List<String> roles;
    
	RoutesApp(String path, List<String> roles) {
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
