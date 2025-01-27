package edu.kyndryl.academy.mscursosprofe.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kyndryl.academy.mscursosprofe.model.Curso;
import edu.kyndryl.academy.mscursosprofe.repository.CursoRepository;


@Service
public class CursoServiceImp implements CursoService {
	
	@Autowired
	CursoRepository cursoRepository;//acceso a la bd

	@Override
	@Transactional(readOnly = true)
	public Iterable<Curso> findAll() {
		return this.cursoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Curso> findById(Long id) {
		return this.cursoRepository.findById(id);
	}

	@Override
	@Transactional
	public Curso save(Curso curso) {
		return this.cursoRepository.save(curso);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		this.cursoRepository.deleteById(id);

	}


	@Override
	public Optional<Curso> update(Curso curso, Long id) {
		// TODO IMPLEMENTAR 
		return Optional.empty();
	}

}
