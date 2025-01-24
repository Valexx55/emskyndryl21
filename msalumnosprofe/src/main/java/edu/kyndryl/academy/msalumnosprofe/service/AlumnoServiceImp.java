package edu.kyndryl.academy.msalumnosprofe.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import edu.kyndryl.academy.msalumnosprofe.model.Alumno;
import edu.kyndryl.academy.msalumnosprofe.model.FraseChiquito;
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

	//TODO: revisar el fallo al deserializar
	@Override
	public Optional<FraseChiquito> obtenerFraseAleatoria() {
		Optional<FraseChiquito> oFrase = Optional.empty();
		RestTemplate restTemplate = null;
		ResponseEntity<FraseChiquito> fraseChiquito = null;
		
		
		/*List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
		//Add the Jackson Message converter
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

		// Note: here we are making this converter to process any kind of response, 
		// not only application/*json, which is the default behaviour
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));        
		messageConverters.add(converter);  
		restTemplate.setMessageConverters(messageConverters); */
		
			HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    HttpEntity<String> entity = new HttpEntity<String>(headers);
		
			restTemplate = new RestTemplate();
			
			fraseChiquito = restTemplate.exchange("http://chiquitadas.es/api/quotes/avoleorrr", HttpMethod.GET,entity, FraseChiquito.class);
			//fraseChiquito = restTemplate.getForObject("http://chiquitadas.es/api/quotes/avoleorrr", FraseChiquito.class);
			oFrase = Optional.of(fraseChiquito.getBody());
		
		return oFrase;
	}

}
