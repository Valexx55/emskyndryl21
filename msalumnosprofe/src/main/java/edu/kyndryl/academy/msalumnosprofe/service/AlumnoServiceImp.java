package edu.kyndryl.academy.msalumnosprofe.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kyndryl.academy.msalumnosprofe.model.Alumno;
import edu.kyndryl.academy.msalumnosprofe.repository.AlumnoRepository;

/**
 * En esta clase, contiene la lógica de negocio
 * la implementación de las operaciones
 */
@Service
public class AlumnoServiceImp implements AlumnoService {
	
	/**
	 * TRANSACCIÓN: CONJUNTO DE OPERACIONES LÓGICAMENTE AGRUPADAS QUE O BIEN SALEN TODAS O BIEN NIGUNA
	 * 
	 *  BILLETE DE AVE
	 *  
	 *  service
	 *  comprarBillete (Cliente, Destino, Fecha)
	 *  
	 *  {
	 *  	INICIO CONEXIÓN	
	 *  
	 *  	PAGAR - COMUNICAR INTERFAZ BANCARIA
	 *  	MARCAR ESE ASIENTO OCUPADO
	 *  	ASIENTO CONTABLE
	 *  
	 *  	SI TODO HA IDO BIEN -- COMMIT
	 *  	SI FALLA ALGO -- ROLLBACK
	 *  	CIERRO LA CONEXIÓN
	 *  }
	 *  
	 */
	
	
	@Autowired
	AlumnoRepository alumnoRepository;

	@Override
	@Transactional
	public Alumno alta(Alumno alumno) {
		return this.alumnoRepository.save(alumno);
	}

	@Override
	@Transactional
	public void borrarPorId(Long id) {
		this.alumnoRepository.deleteById(id);
		
	}

	@Override
	@Transactional
	public Optional<Alumno> modificarAlumnoPorId(Alumno alumno, Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Alumno> consultarPorId(Long id) {
		
		return this.alumnoRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> consultarTodos() {
		return this.alumnoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> buscarAlumnosPorIntervaloDeEdad(int edadmin, int edadmax) {
		
		return this.alumnoRepository.findByEdadBetween(edadmin, edadmax);
	}

}
