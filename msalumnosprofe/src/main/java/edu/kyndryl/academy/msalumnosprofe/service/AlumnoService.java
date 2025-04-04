package edu.kyndryl.academy.msalumnosprofe.service;


import java.util.Optional;

import org.springframework.data.domain.Pageable;

import edu.kyndryl.academy.mscomunprofe.entity.Alumno;
import edu.kyndryl.academy.mscomunprofe.entity.FraseChiquito;

/**
 * Aquí quedan definidos de manera abstracta (sólo la cabecera de los métodos)
 * la funcionalidad o la lógica de negocio
 */
public interface AlumnoService {
	
	//ALTA
	Alumno alta (Alumno alumno);
	//BAJA POR ID
	void borrarPorId (Long id);
	//MODIFICACIÓN
	Optional<Alumno> modificarAlumnoPorId (Alumno alumno, Long id);
	//CONSULTA POR ID
	Optional<Alumno> consultarPorId (Long id);
	//CONSULTA DE TODOS
	Iterable<Alumno> consultarTodos ();
	//CONSULTA POR RANGO DE EDAD
	Iterable<Alumno> buscarAlumnosPorIntervaloDeEdad (int edadmin, int edadmax);
	//OBTENER FRASE CHIQUITO DE OTRO SERVER
	Optional<FraseChiquito> obtenerFraseAleatoria ();
	//obtenerAlumnosPorPagina
	Iterable<Alumno> consultarAlumnosPorPagina (Pageable pageable);
	//obtenerAlumnosPorPagina con filtro de edad
	Iterable<Alumno> findByEdadBetween (int edadmin, int edadmax, Pageable pageable);
	
	

}
