package edu.kyndryl.academy.msalumnosprofe.service;

import java.util.Optional;

import edu.kyndryl.academy.msalumnosprofe.model.Alumno;
import edu.kyndryl.academy.msalumnosprofe.model.FraseChiquito;

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
	

}
