package edu.kyndryl.academy.mscursosprofe.controller;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.kyndryl.academy.mscomunprofe.entity.Alumno;
import edu.kyndryl.academy.mscomunprofe.entity.Curso;
import edu.kyndryl.academy.mscursosprofe.service.CursoService;

@RestController
@RequestMapping("/curso")//LE DECIMOS a Spring/Tomcat lo que venga con /curso es para esta clase
public class CursosController {
	
	@Autowired
	CursoService cursoService;
	
	
	@GetMapping // GET http://localhost:8081/curso
	public ResponseEntity<?> listarCursos() {
		ResponseEntity<?> responseEntity = null;
		Iterable<Curso> listado_cursos = null;

			listado_cursos = this.cursoService.findAll();
			responseEntity = ResponseEntity.ok(listado_cursos);

		return responseEntity;

	}

	@GetMapping("/{id}") // GET http://localhost:8081/curso/5
	public ResponseEntity<?> listarCursoPorId(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> o_curso = null;

			o_curso = this.cursoService.findById(id);
			if (o_curso.isPresent()) {
				Curso curso_leido = o_curso.get();
				responseEntity = ResponseEntity.ok(curso_leido);
			} else {
				// no había un alumno con ese ID
				responseEntity = ResponseEntity.noContent().build();
			}

		return responseEntity;

	}
	
	
	@PostMapping // POST http://localhost:8081/curso
	public ResponseEntity<?> insertarCurso(@RequestBody Curso alumno) {
		ResponseEntity<?> responseEntity = null;
		Curso curso_creado = null;

				curso_creado = this.cursoService.save(alumno);
				responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(curso_creado);
			
		return responseEntity;

	}
	
	@PutMapping("/{id}") // PUT http://localhost:8081/curso/5
	public ResponseEntity<?> modificarCurso(@RequestBody Curso curso, @PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> o_curso = null;
		
				//curso.setNombre("Piton 3");//transient "desconectado de la bd"
			
				o_curso = this.cursoService.update(curso, id);
	
				if (o_curso.isPresent()) {
					Curso curso_modificado = o_curso.get();
					responseEntity = ResponseEntity.ok(curso_modificado);
				} else {
					// no había un curso con ese ID
					responseEntity = ResponseEntity.notFound().build();// 404
				}
	
		return responseEntity;

	}
	
	@DeleteMapping("/{id}") // DELETE http://localhost:8081/curso/8
	public ResponseEntity<?> eliminarCurso(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;

			this.cursoService.deleteById(id);
			responseEntity = ResponseEntity.ok().build();

		return responseEntity;

	}
	
	
	@PutMapping("/asignar-alumnos/{idcurso}") // PUT http://localhost:8081/curso/asignar-alumnos/5
	public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long idcurso) {
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> o_curso = null;
		
				o_curso =  this.cursoService.asignarAlumos(alumnos, idcurso);
			
				if (o_curso.isPresent()) {
					Curso curso_modificado = o_curso.get();
					responseEntity = ResponseEntity.ok(curso_modificado);
				} else {
					// no había un curso con ese ID
					responseEntity = ResponseEntity.notFound().build();// 404
				}
	
		return responseEntity;

	}
	
	
	
	@PutMapping("/eliminar-alumno/{idcurso}") // PUT http://localhost:8081/curso/eliminar-alumno/5
	public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long idcurso) {
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> o_curso = null;
		
				o_curso =  this.cursoService.eliminarAlumno(alumno, idcurso);
				
			
				if (o_curso.isPresent()) {
					Curso curso_modificado = o_curso.get();
					responseEntity = ResponseEntity.ok(curso_modificado);
				} else {
					// no había un curso con ese ID
					responseEntity = ResponseEntity.notFound().build();// 404
				}
	
		return responseEntity;

	}
	
	private void mostrarCabeceras (Map<String, Serializable> params)
	{
		Iterator<String> it_claves= params.keySet().iterator();
		
		params.entrySet().forEach(entrada-> {
			System.out.print ("CLAVE " + entrada.getKey()+ " = ");
			System.out.println("VALOR "+ entrada.getValue());
		});
		/*while (it_claves.hasNext())
		{
			Serializable s = params.get(it_claves.next());
			System.out.println("cabecera = " + s);
		}*/
	}
	
	
	
	//obtenerCursoAlumno: recibo un id de alumno y devuelvo el curso en que está matriculado
	@GetMapping("/obtener-curso-alumno/{idalumno}") 
	public ResponseEntity<?> obtenerCursoAlumno(@PathVariable Long idalumno, @RequestHeader Map<String, Serializable> params) 
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> o_curso = null;
		
				o_curso =  this.cursoService.obtenerCursoAlumno(idalumno);
				mostrarCabeceras(params);
			
				if (o_curso.isPresent()) {
					Curso curso_modificado = o_curso.get();
					responseEntity = ResponseEntity.ok(curso_modificado);
				} else {
					// no había un curso con ese ID
					responseEntity = ResponseEntity.noContent().build();// 204
				}
	
		return responseEntity;

	}
	
	
	
	
	
	
	
	
	
	

}
