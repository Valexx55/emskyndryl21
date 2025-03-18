package edu.kyndryl.academy.msalumnosprofe.client;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.kyndryl.academy.mscomunprofe.entity.Curso;

@FeignClient(name = "mscursosprofe")
public interface ClienteFeignCurso {
	
	@GetMapping("/curso/obtener-curso-alumno/{idalumno}")
	public Optional<Curso> obtenerCursoAlumno(@PathVariable Long idalumno);
	

}
