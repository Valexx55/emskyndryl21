package edu.kyndryl.academy.msalumnosprofe.model;

/*
/**
 *  comentario de código
 * @author Val
 * @since 1.0
 */

/**
 * esta clase, representa a un alumno con sus datos
 * DTO- Data Transfer/Type Object - Bean
 * 
 */
public class Alumno {
	
	//comentario de línea
	/*
	 * comentario de bloque
	 */
	
	private String nombre;
	private int edad;
	private String apellido;
	private String email;
	
	
	
	//constructor por defecto
	public Alumno() {
		// TODO Auto-generated constructor stub
	}

	//constructor canónico
	public Alumno(String nombre, int edad, String apellido, String email) {
		super();
		this.nombre = nombre;
		this.edad = edad;
		this.apellido = apellido;
		this.email = email;
	}

	//métodos de acceso
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getEdad() {
		return edad;
	}
	
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	

}
