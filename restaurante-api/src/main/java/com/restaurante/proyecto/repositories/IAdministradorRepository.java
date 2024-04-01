package com.restaurante.proyecto.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.restaurante.proyecto.models.entity.Administrador;
import com.restaurante.proyecto.models.entity.Empleado;

@Repository
public interface IAdministradorRepository extends JpaRepository<Administrador, Long> {

	
	@Override// si te fijas y das ctrl + clic muchos metodos ya estan creados en JpaRepository que se está extendiendo(heredando)
	// pero muchas consutlas que usaremos aun no estan implementadas entocnes toca chambear a manopla
	Optional<Administrador> findById(Long id) ;
	
	//ejemplo NO SON CONSULTAS SQL son JPQL, en lugar del nombre de las tablas se usan los nombres y atributos de las clases
	@Query("""
			SELECT a	
			FROM Administrador a
			INNER JOIN Empleado e ON e.idEmpleado = a.idAdministrador.idEmpleado
			WHERE e.nombre = :nombre AND :numero=:numero
			""") // el numero = numero solo es para que veas como se hacen las consultas con parametros , es el numero de aqui abajito
	List<Administrador> buscarPorEjemplo(String nombre,Integer numero);

	@Query("""
			SELECT a	
			FROM Administrador a
			INNER JOIN Empleado e ON e.idEmpleado = a.idAdministrador.idEmpleado
			WHERE e.rfc = :rfc
			""")
	Administrador ejemploBuscarPorRFC(String rfc);
	
	
}
