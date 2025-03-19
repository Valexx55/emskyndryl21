package edu.kyndryl.academy.msalumnosprofe.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.kyndryl.academy.mscomunprofe.entity.Alumno;

@Repository
public interface AlumnoRepositoryPage extends PagingAndSortingRepository<Alumno, Long> {

}
