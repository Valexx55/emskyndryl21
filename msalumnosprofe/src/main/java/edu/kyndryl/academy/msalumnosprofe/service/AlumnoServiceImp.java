package edu.kyndryl.academy.msalumnosprofe.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.kyndryl.academy.msalumnosprofe.model.Alumno;

/**
 * En esta clase, contiene la lógica de negocio
 * la implementación de las operaciones
 */
@Service
public class AlumnoServiceImp implements AlumnoService {

	@Override
	public Alumno alta(Alumno alumno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void borrarPorId(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Alumno> modificarAlumnoPorId(Alumno alumno, Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Alumno> consultarPorId(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Iterable<Alumno> consultarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
