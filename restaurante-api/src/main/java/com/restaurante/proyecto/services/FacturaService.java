package com.restaurante.proyecto.services;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.proyecto.dtos.FacturaDTO;
import com.restaurante.proyecto.dtos.FacturaResponse;
import com.restaurante.proyecto.models.entity.Factura;
import com.restaurante.proyecto.models.entity.ItemOrden;
import com.restaurante.proyecto.models.entity.Orden;
import com.restaurante.proyecto.repositories.IFacturaRepository;
import com.restaurante.proyecto.repositories.IItemOrdenRepository;
import com.restaurante.proyecto.repositories.IOrdenRepository;

import jakarta.validation.constraints.Email;

@Service
public class FacturaService {

	@Autowired
	IFacturaRepository facturaRepository;
	@Autowired
	IOrdenRepository ordenRepository;
	@Autowired
	IItemOrdenRepository itemOrdenRepository;
	
	
	public List<FacturaDTO> getFacturas(String correo) {
		 List<Factura> facturas= facturaRepository.findByCorreo(correo);
		 return facturas.stream().map(this::convertToDTO).collect(Collectors.toList());
	}
	
	
	public List<FacturaDTO> getAll(){
		
		return facturaRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}
	
    public FacturaResponse createFactura(FacturaDTO facturaDTO) {
        Factura factura = new Factura();
        factura.setCorreo(facturaDTO.getCorreo());
        factura.setDireccion(facturaDTO.getDireccion());
        
        Orden orden = ordenRepository.findByEstadoAndIdOrden("DESPACHADA", facturaDTO.getIdOrden());
        factura.setOrden(orden);
        factura.setRfc(facturaDTO.rfc);
        factura.setTotal(orden.getTotal());
        
        Factura savedFactura = facturaRepository.save(factura);
        List<ItemOrden> itemsOrden = itemOrdenRepository.buscarPorIdOrden(orden.getIdOrden()); 
        FacturaResponse facturaResponse = FacturaResponse.crearFacturaResponse(savedFactura, itemsOrden);
        
        return facturaResponse;
        
    }

    public void deleteFactura(Long id) {
        facturaRepository.deleteById(id);
    }
    
    private FacturaDTO convertToDTO(Factura factura) {
        FacturaDTO dto = new FacturaDTO();
        dto.setId(factura.getIdFactura()); 
        dto.setIdOrden(factura.getOrden().getIdOrden());
        dto.setFechaEmision(factura.getFechaEmision().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        dto.setCorreo(factura.getCorreo());
        dto.setDireccion(factura.getDireccion());
        dto.setTotal(factura.getTotal());
        dto.setRfc(factura.getRfc());
        return dto;
    }
	
	

}
