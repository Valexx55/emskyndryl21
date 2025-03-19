package edu.kyndryl.academy.msalumnosprofe.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import edu.kyndryl.academy.msalumnosprofe.controller.AlumnoController;
import edu.kyndryl.academy.msalumnosprofe.model.FraseChiquito;
import edu.kyndryl.academy.msalumnosprofe.repository.AlumnoRepository;
import edu.kyndryl.academy.msalumnosprofe.repository.AlumnoRepositoryPage;
import edu.kyndryl.academy.mscomunprofe.entity.Alumno;

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
	
	Logger logger = LoggerFactory.getLogger(AlumnoServiceImp.class);//dame un log para esta clase
	
	@Autowired
	AlumnoRepository alumnoRepository;
	
	@Autowired
	AlumnoRepositoryPage alumnoRepositoryPage;

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
		Optional<Alumno> oa =  Optional.empty();
		
		//1 leer
			oa =  this.alumnoRepository.findById(id);
			if (oa.isPresent())
			{
				//2 modificamos
				Alumno alumnoleido = oa.get();//está en estado Persistente si modifico un atributo dle objeto, estoy modificando también la columna / el registro de la bd JPA
				// alumnoleido.setNombre(alumno.getNombre());
				BeanUtils.copyProperties(alumno, alumnoleido, "id", "creadoEn");//propiedades que se ignoran no se copian
				//3 guardamos es implícto
				//this.alumnoRepository.save(alumnoleido);
				oa = Optional.of(alumnoleido); 
			}
		
		
		return oa;
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

	//TODO: revisar el fallo al deserializar
	@Override
	public Optional<FraseChiquito> obtenerFraseAleatoria() {
		Optional<FraseChiquito> oFrase = Optional.empty();
		RestTemplate restTemplate = null;
		FraseChiquito fraseChiquito = null;
		
			restTemplate = new RestTemplate();
			//frase = restTemplate.getForObject("http://chiquitadas.es/api/quotes/avoleorrr", String.class);//OJO, esto falla porque nos da un 300 + 10 PTS PARA rICK
			fraseChiquito = restTemplate.getForObject("https://chiquitadas.es/api/quotes/avoleorrr", FraseChiquito.class);
			logger.debug("FRASE RX = " + fraseChiquito);
			//logger.debug("FRASE RX = " + frase);
			oFrase = Optional.of(fraseChiquito);
			
		
		return oFrase;
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> consultarAlumnosPorPagina(Pageable pageable) {
		
		return this.alumnoRepositoryPage.findAll(pageable);
		
	}

}
