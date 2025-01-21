package edu.kyndryl.academy.msalumnosprofe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//import org.springframework.web.bind.annotation.GetMapping;

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
public class AlumnoController {
	
	
	
	Logger logger = LoggerFactory.getLogger(AlumnoController.class);//dame un log para esta clase
	
	//yo a Spring le digo oye: si recibes un GET localhost:8081/obtener-alumno-test
	//invocas a este método
	@GetMapping("/obtener-alumno-test")
	public Alumno obtenerAlumnoTest () {
		Alumno alumno = null;
		
			logger.debug("Entrando en obtenerAlumnoTest");
			alumno = new Alumno("Laura", 60, "Gómez", "laura@kyndryl.com");
			logger.debug("Alumno " + alumno);
			logger.debug("Final obtenerAlumnoTest");
			//TODO COMENTAR TOSTRING EN ENTITADES Y BEANS LOG
		
		return alumno;//SERIALIZA Objeto de Java - A String JSON
	}

}
