package edu.kyndryl.academy.mscursosprofe.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.kyndryl.academy.mscomunprofe.entity.Curso;


@Repository //no es obligatorio por heredar de CrudRepository
public interface CursoRepository extends CrudRepository<Curso, Long> {
	//TODO: PAGINACIÓN

}
