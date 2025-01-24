package edu.kyndryl.academy.msalumnosprofe.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import edu.kyndryl.academy.msalumnosprofe.model.Alumno;
import edu.kyndryl.academy.msalumnosprofe.service.AlumnoService;

@WebMvcTest(AlumnoController.class)//levantar el contexto parcialmente de las clases indicadas
public class AlumnoControllerTest3 {
	
	@Autowired
	private MockMvc mockMvc; // el objeto con el que lanzamos las peticiones HTTP
	
	@MockBean
	AlumnoService alumnoService; //es un servicio de mentirijilla--> todo: programar su comportamiento


	
	@Test
	public void getServicioMockeado() throws Exception {
		
		Alumno alumno = new Alumno();
		alumno.setId(1l);
		alumno.setNombre("Juan");
		alumno.setApellido("Moreno");
		alumno.setEdad(22);
		alumno.setEmail("juanmo@mail.es");

		// serializar este alumno
		//String json_alumno = UtilTest.toJSON(alumno);
		
		//programamos el funcionamiento del mock
		when(alumnoService.consultarPorId(2l)).thenReturn(Optional.of(alumno));

		this.mockMvc.perform(get("/alumno/2")).
		andDo(print()).
		andExpect(status().isOk()).
		andExpect(content().contentType("application/json")).
		andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Juan")).
		andExpect(MockMvcResultMatchers.jsonPath("$.edad").value("22"));
	}
	
}
