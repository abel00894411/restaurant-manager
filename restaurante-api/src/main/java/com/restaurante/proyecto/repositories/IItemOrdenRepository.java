package com.restaurante.proyecto.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.restaurante.proyecto.dtos.ItemOrdenListado;
import com.restaurante.proyecto.models.entity.ItemOrden;

@Repository
public interface IItemOrdenRepository extends JpaRepository<ItemOrden, Long> {

	@Query("""

			SELECT i FROM ItemOrden i WHERE i.orden.idOrden = :id

			""")
	public List<ItemOrden> buscarPorIdOrden(Long id);

	@Query(value="""


			SELECT i.idItemOrden, i.idItemMenu, i.cantidad, i.estado 
			FROM itemsorden i
			INNER JOIN ordenes o ON o.idOrden = i.idOrden
			WHERE ((i.estado = "PENDIENTE") and 
			(   timestampdiff( HOUR, date_format(now(),'%Y-%m-%d %H:%i:%s'), o.fecha )  < 24 ) and
			
			(  i.idCocinero = ?1  ));


						""",nativeQuery = true)
	public List<Object[]> obtenerItemsPendientesActuales(Long id);

}
