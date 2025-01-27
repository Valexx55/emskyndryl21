package edu.kyndryl.academy.mscursosprofe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cursos")
public class Curso {
	
	@Id//pk
	@GeneratedValue(strategy = GenerationType.IDENTITY) //autoinc
	private Long id;
	
	private String nombre;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Curso(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	public Curso() {
		// TODO Auto-generated constructor stub
	}
	


}
