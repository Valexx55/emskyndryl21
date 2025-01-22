package edu.kyndryl.academy.msalumnosprofe.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.kyndryl.academy.msalumnosprofe.model.Alumno;


/**
 * esta capa recibe y contesta las peticiones del cliente
 * 
 * en esta clase exhibimos las operaciones públicas
 * accesibles a través de una URL / API WEB
 * 
 * RESTfull Alumnos
 * 
 * ALTA - POST 
 * BAJA - DELETE
 * MODIFICACIÓN - PUT
 * CONSULTA - GET
 * 
 * 
 */
//@Controller //sería si la capa de controller devuelve vistas (HTML, JSP)
@RestController//le estoy diciendo que trabajo con JSON - de modo que si yo devuelvo un alumno , Spring automáticamente lo convierte a JSON
@RequestMapping("/alumno") //especifo que las peticiones a esta clase serán del tipo http://dominio:puerto/alumno
public class AlumnoController {
	
	
	
	Logger logger = LoggerFactory.getLogger(AlumnoController.class);//dame un log para esta clase
	
	//yo a Spring le digo oye: si recibes un GET localhost:8081/alumno/obtener-alumno-test
	//invocas a este método
	@GetMapping("/obtener-alumno-test")
	public Alumno obtenerAlumnoTest () {
		Alumno alumno = null;
		
			//alumno.getApellido();
			logger.debug("Entrando en obtenerAlumnoTest");
			alumno = new Alumno("Laura", 60, "Gómez", "laura@kyndryl.com");
			logger.debug("Alumno " + alumno.toString());
			logger.debug("Final obtenerAlumnoTest");
			//TODO COMENTAR TOSTRING EN ENTITADES Y BEANS LOG
		
		return alumno;//SERIALIZA Objeto de Java - A String JSON
	}
	
	
	// * RESTfull 
	  
	 //* BAJA - DELETE
	// * MODIFICACIÓN - PUT
	// * CONSULTA un alumno - GET
	
	// CONSULTA DE TODOS LOS ALUMNOS
	//ResponseEntity<Tipo que va en el cuerpo> es la clase que envuelve/representa el mensaje HTTP de Respuesta
	
	@GetMapping //GET localhost:8081/alumno
	public ResponseEntity<Iterable<Alumno>> listarAlumnos()
	{
		ResponseEntity<Iterable<Alumno>> responseEntity = null;
		
		return responseEntity;
	}
	
	// * CONSULTA un alumno - GET
	
	@GetMapping("/{id}") //GET localhost:8081/alumno/5 //@PathVariable carga el valor
	public ResponseEntity<Alumno> listarAlumnoPorId(@PathVariable  Long id)
	{
		ResponseEntity<Alumno> responseEntity = null;
		
		return responseEntity;
	}
	
	 //* BAJA - DELETE
	@DeleteMapping("/{id}") //DELETE localhost:8081/alumno
	public ResponseEntity<Void> borrarAlumnoPorId(@PathVariable  Long id)
	{
		ResponseEntity<Void> responseEntity = null;
		
		return responseEntity;
	}
	
	//POST - INSERTAR
	@PostMapping //POST localhost:8081/alumno
	public ResponseEntity<Alumno> insertarAlumno(@RequestBody Alumno alumno)
	{
		ResponseEntity<Alumno> responseEntity = null;
				
		
		return responseEntity;
	}
	
	
	@PutMapping("/{id}") //PUT localhost:8081/alumno/3
	public ResponseEntity<Alumno> modificarAlumno(@RequestBody Alumno alumno, @PathVariable Long id)
	{
		ResponseEntity<Alumno> responseEntity = null;
				
		
		return responseEntity;
	}
	
	

}
