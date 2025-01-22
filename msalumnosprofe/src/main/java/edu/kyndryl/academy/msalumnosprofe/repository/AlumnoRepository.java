package edu.kyndryl.academy.msalumnosprofe.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.kyndryl.academy.msalumnosprofe.model.Alumno;

/**
 * en esta clase, operamos con la base de datos
 */
@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Long> {

}